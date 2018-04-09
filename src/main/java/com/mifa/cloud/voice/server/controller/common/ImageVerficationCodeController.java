package com.mifa.cloud.voice.server.controller.common;

import com.mifa.cloud.voice.server.component.redis.KeyValueDao;
import com.mifa.cloud.voice.server.utils.ImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@RestController
@Api(value = "图形验证码", description = "图形验证码", produces = MediaType.APPLICATION_JSON)
@Slf4j
public class ImageVerficationCodeController {


    @Autowired
    private KeyValueDao keyValueDao;
    @Autowired
    private ImageUtil imageUtil;

    @GetMapping(value = "/getImageVerficationCode")
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
            keyValueDao.set("IMGIDENTIFYCODE_" + mobilePhone,verifyCode,60*3);
            imageUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (IOException e) {
            log.error("输出图形验证码异常："+e.getMessage());
            e.printStackTrace();
        }
    }


}
