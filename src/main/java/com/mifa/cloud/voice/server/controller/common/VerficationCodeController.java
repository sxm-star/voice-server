package com.mifa.cloud.voice.server.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.component.RandomSort;
import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
import com.mifa.cloud.voice.server.config.StaticConst;
import com.mifa.cloud.voice.server.dto.MobileVerficationCodeDTO;
import com.mifa.cloud.voice.server.utils.ImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "验证码", description = "验证码", produces = MediaType.APPLICATION_JSON)
@Slf4j
@RequestMapping(AppConst.BASE_PATH + "v1")
public class VerficationCodeController {


    @Autowired
    private KeyValueDao keyValueDao;
    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/get_image_verfication_code")
    @ApiOperation(value = "图形验证码", notes = "图形验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "mobile", value = "手机号码", required = true, paramType = "query")})
    public void imageVerficationCode(HttpServletRequest request, HttpServletResponse response){
        String mobilePhone = request.getParameter("mobile");
        if(StringUtils.isEmpty(mobilePhone)){
            log.error("获取图形验证码时mobilePhone参数为空");
            return;
        }
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        try {
            int w = 92, h = 40;
            String verifyCode = imageUtil.generateVerifyCode(4);
            //保存验证码
            keyValueDao.set(StaticConst.IMG_IDENTIFY_CODE + mobilePhone,verifyCode,60*3);
            imageUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (IOException e) {
            log.error("输出图形验证码异常："+e.getMessage());
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/get_mobile_verfication_code")
    @ApiOperation(value = "短信验证码", notes = "短信验证码")
    public CommonResponse mobileVerficationCode(@RequestBody @Valid MobileVerficationCodeDTO param) {
        String code = String.valueOf(RandomSort.generateRandomNum(6));
        keyValueDao.set(StaticConst.MOBILE_SMS_KEY + param.getMobile(), code, 60 * 3);
        Map<String, String> map = new HashMap<String, String>();
        map.put("bizType", "1");
        map.put("aliasName", "mifayuyinyun");
        map.put("tel", param.getMobile());
        map.put("identifyingCode", code);
        String json = JSONObject.toJSONString(map);
        log.info("send msg：" + json);
        rabbitTemplate.convertAndSend("q_sms", json);
        return CommonResponse.successCommonResponse();
    }





}
