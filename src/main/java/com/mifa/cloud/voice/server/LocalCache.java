package com.mifa.cloud.voice.server;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 缓存工具
 * 
 * @author 宋烜明
 *
 */
@Slf4j
public class LocalCache {

	/**
	 * 发送验证码缓存,过期时间1分钟
	 */
	private static LoadingCache<String, Long> RegisterCodeCache = CacheBuilder.newBuilder()
		.expireAfterAccess(1, TimeUnit.MINUTES)	//当缓存项在指定的时间段内没有被读或写就会被回收
		.maximumSize(1000)		// 设置缓存个数
		.build(new CacheLoader<String, Long>() {
		    @Override
		    /** 当本地缓存命没有中时，调用load方法获取结果并将结果缓存 **/
		    public Long load(String key) {
		    	return System.currentTimeMillis();
		    }
		
		}
	);

	/**
	 * 注册验证码缓存<br>
	 * <手机号, 6小时内发送次数>
	 * 6小时内仅允许发5个
	 */
	private static Cache<String, Integer> RegisterCodeCache6Hours = CacheBuilder.newBuilder()
			.expireAfterWrite(6, TimeUnit.HOURS)	//缓存时间：6小时,复读缓存时间不会延时
			.maximumSize(5000)						//设置缓存个数1w
			.build();
	
	
	/**
	 * 在1分钟内是否请求过验证码
	 * @param
	 */
	public static boolean hasRegisterRequest(String phone) {
		Long time = RegisterCodeCache.getIfPresent(phone);
		Long now = System.currentTimeMillis();
    	if(time != null) {
    		if((now - time) <= 60*1000){
				//1分钟内有请求过注册短信验证码
        		log.error(String.format("phone=%s, 1分钟内重复[注册短信]验证码", phone));
				return true;
    		}
    	}
		//无注册请求
		RegisterCodeCache.put(phone, System.currentTimeMillis());
    	
    	//6小时内发送不超过5次
    	Integer numIn6hours = RegisterCodeCache6Hours.getIfPresent(phone);
    	if(numIn6hours == null) {
    		RegisterCodeCache6Hours.put(phone, 1);
    	} else if(numIn6hours < 5) {
    		RegisterCodeCache6Hours.put(phone, numIn6hours+1);
    	} else {
    		//大于等于5次
    		log.error(String.format("phone=%s, 6小时内[注册短信]验证码发送超过5次", phone));
			return true;
    	}
    	return false;
	}

	/**
	 * 发送验证码缓存,过期时间1分钟
	 */
	private static LoadingCache<String, Long> UpdatePasswordCodeCache = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.MINUTES)	//当缓存项在指定的时间段内没有被读或写就会被回收
			.maximumSize(1000)		// 设置缓存个数
			.build(new CacheLoader<String, Long>() {
					   @Override
					   /** 当本地缓存命没有中时，调用load方法获取结果并将结果缓存 **/
					   public Long load(String key) {
						   return System.currentTimeMillis();
					   }
				   }
			);

	/**
	 * 修改密码验证码缓存<br>
	 * <手机号, 6小时内发送次数>
	 * 6小时内仅允许发3个
	 */
	private static Cache<String, Integer> UpdatePasswordCodeCache6Hours = CacheBuilder.newBuilder()
			.expireAfterWrite(6, TimeUnit.HOURS)	//缓存时间：6小时,复读缓存时间不会延时
			.maximumSize(1000)						//设置缓存个数1k
			.build();
	

	/**
	 * 在1分钟内是否修改密码请求过验证码
	 * @param phone
	 */
	public static boolean hasUpdatePasswordRequest(String phone) {
		Long time = UpdatePasswordCodeCache.getIfPresent(phone);
		Long now = System.currentTimeMillis();
		if(time != null) {
			if((now - time) <= 60*1000){
				//1分钟内有过请求过修改密码短信验证码,error
	    		log.error(String.format("phone=%s, 1分钟内重复[修改密码短信]验证码", phone));
				return true;
			}
		}
		//无修改密码请求
		UpdatePasswordCodeCache.put(phone, System.currentTimeMillis());

    	
    	//6小时内发送不超过3次
    	Integer numIn6hours = UpdatePasswordCodeCache6Hours.getIfPresent(phone);
    	if(numIn6hours == null) {
    		UpdatePasswordCodeCache6Hours.put(phone, 1);
    	} else if(numIn6hours < 3) {
    		UpdatePasswordCodeCache6Hours.put(phone, numIn6hours+1);
    	} else {
    		//大于等于3次
    		log.error(String.format("phone=%s, 6小时内[修改密码短信]验证码发送超过3次", phone));
			return true;
    	}
    	
		return false;
	}

}
