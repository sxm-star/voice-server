package com.mifa.cloud.voice.server.controller.system;

import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.dto.SystemKeyValueDTO;
import com.mifa.cloud.voice.server.commons.dto.SystemkeyValueTypeVO;
import com.mifa.cloud.voice.server.pojo.SystemKeyValue;
import com.mifa.cloud.voice.server.service.SystemKeyValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/4/12.
 */
@RestController
@RequestMapping(AppConst.BASE_PATH + "v1")
@Api(value = "系统字典", description = "系统字典", produces = MediaType.APPLICATION_JSON)
public class SystemKeyValueController {

    @Autowired
    private SystemKeyValueService systemKeyValueService;

    @PostMapping(value = "/keyValue")
    @ApiOperation(value = "系统字典新增")
    @Loggable(descp = "系统字典新增")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION,
            required = true, value = "service token", dataType = "string")
    })
    public CommonResponse<Void> insertKeyValue(@RequestBody @Valid SystemKeyValueDTO param) {

        // 判断相同类型的key是否已经存在
        List<SystemKeyValue> keyValueList = systemKeyValueService.getKeyValueListByType(param.getBizType(), param.getParamKey(), param.getContractNo());
        if(!keyValueList.isEmpty()) {
            return CommonResponse.failCommonResponse("字典已存在");
        }

        SystemKeyValue systemKeyValue = SystemKeyValue.builder()
                .bizType(param.getBizType())
                .paramKey(param.getParamKey())
                .paramValue(param.getParamValue())
                .remark(param.getRemark())
                .createdBy(param.getContractNo())
                .build();
        int count = systemKeyValueService.insert(systemKeyValue);
        if (count > 0) {
            return CommonResponse.successCommonResponse("系统字典新增成功", null);
        }
        return CommonResponse.failCommonResponse("系统字典新增失败");
    }

    @GetMapping(value = "/keyValue-list")
    @ApiOperation(value = "根据类型获得系统字典")
    @Loggable(descp = "根据类型获得系统字典")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, dataType = "string", value = "service token"),
            @ApiImplicitParam(paramType = "query", name = "keyValueType", required = true, dataType = "string", value = "系统功能类型,必填"),
            @ApiImplicitParam(paramType = "query", name = "contractNo", required = false, dataType = "string",value = "客户号")
    })
    public CommonResponse<List<SystemkeyValueTypeVO>> getKeyValueListByType(String keyValueType, String contractNo) {

        List<SystemKeyValue> keyValueList = systemKeyValueService.getKeyValueListByType(keyValueType, null, contractNo);
        List<SystemkeyValueTypeVO> voList = keyValueList.stream()
                .map(
                        kv -> SystemkeyValueTypeVO.builder()
                                .id(kv.getId())
                                .bizType(kv.getBizType())
                                .paramKey(kv.getParamKey())
                                .paramValue(kv.getParamValue())
                                .build()
                )
                .collect(Collectors.toList());
        return CommonResponse.successCommonResponse(voList);

    }

    @DeleteMapping(value = "/keyValue")
    @ApiOperation(value = "根据ID删除系统字典")
    @Loggable(descp = "根据ID删除系统字典")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, dataType = "string", value = "service token"),
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "long", value = "主键ID")
    })
    public CommonResponse<Boolean> deleteKeyValueById(Long id) {
        return CommonResponse.successCommonResponse(systemKeyValueService.deleteByPrimaryKey(id));
    }



}
