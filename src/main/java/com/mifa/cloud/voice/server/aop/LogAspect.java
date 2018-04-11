package com.mifa.cloud.voice.server.aop;

import com.alibaba.fastjson.JSON;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.enums.ErrorKeyEnums;
import com.mifa.cloud.voice.server.commons.enums.SCOPE;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.ClassClassPath;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LogAspect {
	ThreadLocal<Long> startTime = new ThreadLocal<>();

	// @Resource
	// private LogService logService;

	/**
	 * AOP日志操作实体
	 */
	@Data
	private class LogEntity {
		private String logId;
		private String methodName;
		private Loggable methodLogAnnon;
		private ProceedingJoinPoint point;

		public LogEntity(String logId, String methodName, Loggable methodLogAnnon, ProceedingJoinPoint point) {
			this.logId = logId;
			this.methodName = methodName;
			this.methodLogAnnon = methodLogAnnon;
			this.point = point;
		}

		public String getLogId() {
			return logId;
		}

		public String getMethodName() {
			return methodName;
		}

		public Loggable getMethodLogAnnon() {
			return methodLogAnnon;
		}

		public ProceedingJoinPoint getPoint() {
			return point;
		}

		public void before() {
			try {
				beforeExecLog(this);
			} catch (Exception e) {
				log.error("系统异常:{}", e);
				e.printStackTrace();
			}
		}

		public void after(Object resp) {
			try {
				afterExecLog(this, resp);
			} catch (Exception e) {
				log.error("处理之后的异常:{}", e);
				e.printStackTrace();
			}
		}
	}

	@Before("pointcut()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());

		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 记录下请求内容
		log.info("请求URL : {}", request.getRequestURL().toString());
		log.info("请求IP : " + request.getRemoteAddr());
		// log.info("CLASS_METHOD : " +
		// joinPoint.getSignature().getDeclaringTypeName() + "." +
		// joinPoint.getSignature().getName());
		// log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
	}

	// 切入点
	@Pointcut("execution(* *(..)) && @annotation(com.mifa.cloud.voice.server.annotation.Loggable)")
	private void pointcut() {
	}

	@Around("pointcut()")
	public Object methodExec(ProceedingJoinPoint point) throws Throwable {
		String logId = UUID.randomUUID().toString();

		Class evtClass = point.getTarget().getClass();
		String methodName = point.getSignature().getName(); // 目标方法名
		Method method = ((MethodSignature) point.getSignature()).getMethod(); // 目标方法实体

		Loggable methodLogAnnon = null;
		LogEntity logEntity = null;
		if (null != method) {
			boolean hasMethodLogAnno = method.isAnnotationPresent(Loggable.class);
			if (hasMethodLogAnno) {
				methodLogAnnon = method.getAnnotation(Loggable.class); // 方法注解实体
				logEntity = new LogEntity(logId, methodName, methodLogAnnon, point);
			}
		}

		Object resp = null;
		boolean exFlag = false;
		try {
			resp = point.proceed(); // 执行目标方法内容，获取返回值
		} catch (Exception e) {
			log.error("业务处理过程出现异常:{}", e);
			exFlag = true;
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletResponse rsp = attributes.getResponse();
			PrintWriter printWriter = null;
			try {
				rsp.setHeader("Content-type", "text/html;charset=UTF-8");
				rsp.setCharacterEncoding("UTF-8");
				rsp.setStatus(500);
				printWriter = rsp.getWriter();
				resp = new CommonResponse(CommonResponse.FALSE, ErrorKeyEnums.SERVICE_ERROR ,null );
				printWriter.write(JSON.toJSONString(resp));
			} catch (IOException ignored) {
			} finally {
				if (printWriter != null) {
                    printWriter.close();
                }
			}
		} finally {
			logEntity.before();
		}

		logEntity.after(resp);
		return exFlag == true ? null : resp;
	}

	@AfterReturning(returning = "ret", pointcut = "pointcut()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		 log.info("RESPONSE : " + ret);
		 log.info("###### use time : {} ms ######", (System.currentTimeMillis() - startTime.get()));
	}

	/**
	 * 入参处理
	 * 
	 * 日志ID
	 * @throws Exception
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private void beforeExecLog(LogEntity entity) throws Exception {
		if (null != entity && null != entity.getMethodLogAnnon()) {
			Loggable methodLogAnnon = entity.getMethodLogAnnon();
			if (methodLogAnnon.loggable() && methodLogAnnon.scope().contains(SCOPE.BEFORE)) {

				Class evtClass = entity.getPoint().getTarget().getClass();
				String descp = methodLogAnnon.descp();
				String include = methodLogAnnon.include();

				Map<String, Object> methodParamNames = getMethodParamNames(evtClass, entity.getMethodName(), include);
				Map<String, Object> reqLogContent = getArgsMap(entity.getPoint(), methodParamNames);
				reqLogContent.put("descrition", descp);

				log(entity, SCOPE.BEFORE, reqLogContent);
			}
		}
	}

	/**
	 * 返回处理
	 * 
	 * 日志ID
	 * 
	 * @param resp
	 *            返回值
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private void afterExecLog(LogEntity entity, Object resp)
			throws NoSuchMethodException, SecurityException, Exception {
		if (null != entity && null != entity.getMethodLogAnnon()) {
			Loggable methodLogAnnon = entity.getMethodLogAnnon();
			if (methodLogAnnon.loggable() && methodLogAnnon.scope().contains(SCOPE.AFTER)) {
				log(entity, SCOPE.AFTER, resp);
			}
		}
	}

	/**
	 * 获取方法入参变量名
	 * 
	 * @param cls
	 *            触发的类
	 * @param methodName
	 *            触发的方法名
	 * @param include
	 *            需要打印的变量名
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getMethodParamNames(Class cls, String methodName, String include) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		pool.insertClassPath(new ClassClassPath(cls));
		CtMethod cm = pool.get(cls.getName()).getDeclaredMethod(methodName);
		LocalVariableAttribute attr = (LocalVariableAttribute) cm.getMethodInfo().getCodeAttribute()
				.getAttribute(LocalVariableAttribute.tag);

		if (attr == null) {
			throw new Exception("attr is null");
		} else {
			Map<String, Object> paramNames = new HashMap<String, Object>();
			int paramNamesLen = cm.getParameterTypes().length;
			int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
			if (StringUtils.isEmpty(include)) {
				for (int i = 0; i < paramNamesLen; i++) {
					paramNames.put(attr.variableName(i + pos), i);
				}
			} else { // 若include不为空
				for (int i = 0; i < paramNamesLen; i++) {
					String paramName = attr.variableName(i + pos);
					if (include.indexOf(paramName) > -1) {
						paramNames.put(paramName, i);
					}
				}
			}
			return paramNames;
		}
	}

	/**
	 * 组装入参Map
	 * 
	 * @param point
	 * @param methodParamNames
	 * @return
	 */
	private Map getArgsMap(ProceedingJoinPoint point, Map<String, Object> methodParamNames) {
		Object[] args = point.getArgs();
		if (null == methodParamNames) {
			return Collections.EMPTY_MAP;
		}
		for (Entry<String, Object> entry : methodParamNames.entrySet()) {
			int index = Integer.valueOf(String.valueOf(entry.getValue()));
			Object arg = (null == args[index] ? "" : args[index]);
			methodParamNames.put(entry.getKey(), arg);
		}
		return methodParamNames;
	}

	/**
	 * 输出日志
	 * 
	 * @param entity
	 * @param scope
	 * @param logContent
	 */
	private void log(LogEntity entity, SCOPE scope, Object logContent) {

		Loggable logAnno = entity.getMethodLogAnnon();
		String scopeStr = scope.toString();
		String logText = "";

		// if(logContent!=null){ logText = JSON.toJSONString(logContent);}

		// log.info("scope----->{}，entity----->{}，logContent--->{}",scope,entity.toString(),logContent);
		if (scope.toString().equalsIgnoreCase("REQUEST")) {
			log.info("请求参数:{}", logContent);
		} else if (scope.toString().equalsIgnoreCase("RESPONSE")) {
			log.info("响应结果:{}", logContent);
		} else {
			log.info("不知何种请求");
		}
		// if (null != logContent) {
		// switch (ClassUtil.getClassType(logContent)) {
		// case PRIMITIVE:
		// logText = logContent.toString();
		// break;
		//
		// case REFERENCE:
		// logText = JSON.toJSONString(logContent);
		// break;
		// }
		// }

		// LogInfoBean logInfo = new LogInfoBean(entity.getLogId(),
		// logAnno.type(),
		// Level.toLevel(logAnno.level()), logText);
		// logInfo.setMethodName(entity.getMethodName());
		// logInfo.setRemark(scopeStr);

		if (logAnno.db()) {
			// logService.savelogToDB(entity.getLogId(), logAnno.type(),
			// entity.getMethodName(), logText, scopeStr);
		}
		if (logAnno.elk()) {
			// logService.savelogToELK(logInfo);
		}
		if (logAnno.console()) {
			// logService.savelogToConsole(logInfo);
		}
	}
}