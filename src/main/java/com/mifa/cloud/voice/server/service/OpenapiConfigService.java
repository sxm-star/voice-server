package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.OpenApiConfigDTO;
import com.mifa.cloud.voice.server.commons.dto.OpenApiConfigRspDTO;
import com.mifa.cloud.voice.server.pojo.OpenapiConfigDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import com.mifa.cloud.voice.server.utils.BaseDateUtils;
import com.mifa.cloud.voice.server.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: songxm
 * @date: 2018/5/9 15:21
 * @version: v1.0.0
 */
@Service
@Slf4j
public class OpenapiConfigService extends BaseService<OpenapiConfigDO> {


    public  Boolean addOpenapiConfig(OpenApiConfigDTO openapiConfigDTO){
        OpenapiConfigDO openapiConfigDO =   this.queryOne(OpenapiConfigDO.builder().contractNo(openapiConfigDTO.getContractNo()).build());
        if (null!=openapiConfigDO){
            log.warn("已经存在租户token了");
            return Boolean.FALSE;
        }
        openapiConfigDO =  BaseBeanUtils.convert(openapiConfigDTO,OpenapiConfigDO.class);
        Date currentDate = new Date();
        openapiConfigDO.setCreatedAt(currentDate);
        openapiConfigDO.setAuthToken(JwtTokenUtil.createToken(openapiConfigDO.getContractNo(),currentDate, openapiConfigDTO.getTimeUnit().getCode(), openapiConfigDTO.getExpireValue()));
        openapiConfigDO.setAuthExpire(BaseDateUtils.getPeriodDateByCalendar(currentDate, openapiConfigDTO.getTimeUnit().getCode(), openapiConfigDTO.getExpireValue()));
        int cnt = this.save(openapiConfigDO);
        return cnt>0?Boolean.TRUE:Boolean.FALSE;
    }

    public OpenApiConfigRspDTO getOpenApiConfig(String contractNo){
        OpenapiConfigDO openapiConfigDO = new OpenapiConfigDO();
        openapiConfigDO.setContractNo(contractNo);
        OpenApiConfigRspDTO rspDto = null;
        try{
            rspDto  = BaseBeanUtils.convert(this.queryOne(openapiConfigDO),OpenApiConfigRspDTO.class);
        }catch (Exception e){
            log.error("查询租户token信息异常:{}",e);
        }
        return rspDto;
    }
}
