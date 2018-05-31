package com.mifa.cloud.voice.server.controller;

import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.service.SystemJobTimeService;
import com.mifa.cloud.voice.server.service.SystemResourceService;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author: sxm
 * @date: 2018/4/12 09:59
 * @version: v1.0.0
 */
@RestController
@CrossOrigin
@Api(value = "系统管理相关API.", tags = "系统管理API", description = "涉及菜单,模块,换肤,系统属性设置功能")
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class SystemManagerController {

    @Autowired
    public SystemResourceService resourceService;
    @Autowired
    public SystemJobTimeService systemJobTimeService;

    @ApiOperation("添加系统菜单")
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "添加系统菜单")
    public CommonResponse<Boolean> addMenu(@RequestBody @Valid List<ResourceFunctionModelDTO> resourceFunctionModelDTOs) {
        if (CollectionUtils.isEmpty(resourceFunctionModelDTOs)){
            return CommonResponse.failCommonResponse();
        }
        List<ResourceDTO> resourceDTOs = new ArrayList<>();
        resourceFunctionModelDTOs.forEach(resourceFunctionModelDTO -> {
            ResourceDTO resourceDTO = BaseBeanUtils.convert(resourceFunctionModelDTO, ResourceDTO.class);
            resourceDTO.setResourceStatus(resourceFunctionModelDTO.getStatusEnum().getCode());
            resourceDTO.setResourceGroup(resourceFunctionModelDTO.getResourceGroup().getCode());
            resourceDTO.setResourceType(resourceFunctionModelDTO.getResouceTypeEnum().getCode());
            resourceDTOs.add(resourceDTO);
        });
        return CommonResponse.successCommonResponse(resourceService.addMenu(resourceDTOs));
    }

    @ApiOperation("批量修改菜单")
    @RequestMapping(value = "/menu", method = RequestMethod.PUT)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "修改系统菜单,支持单条修改和批量修改")
    public CommonResponse<Boolean> alterMenu(@RequestBody @Valid List<MenuDTO> menuDTOs) {
        if (CollectionUtils.isEmpty(menuDTOs)){
            return CommonResponse.failCommonResponse();
        }
        List<ResourceDTO> resourceDTOs = new ArrayList<>();
        menuDTOs.forEach(resourceFunctionModelDto -> {
            ResourceDTO resourceDTO = BaseBeanUtils.convert(resourceFunctionModelDto, ResourceDTO.class);
            resourceDTOs.add(resourceDTO);
        });
        return CommonResponse.successCommonResponse(resourceService.alterMenu(resourceDTOs));
    }

    @ApiOperation("获取同一层级菜单列表")
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "获取同一层级菜单列表")
    public CommonResponse<List<MenuDTO>> findMenuList(@RequestParam(required = true, defaultValue = "0") Long pid, @RequestParam(required = true) StatusEnum statusEnum) {
        List<ResourceDTO> list = resourceService.findResourceList(pid, null, statusEnum);
        if (CollectionUtils.isNotEmpty(list)) {
            List<MenuDTO> resultList = new ArrayList<>();
            list.forEach(resourceDto -> {
                resultList.add(BaseBeanUtils.convert(resourceDto, MenuDTO.class));
            });
            return CommonResponse.successCommonResponse(resultList);
        }
        return CommonResponse.successCommonResponse(Collections.EMPTY_LIST);
    }


    @ApiOperation("添加系统允许拨打时间设置")
    @RequestMapping(value = "/system/call-time", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "添加系统允许拨打时间设置")
    public CommonResponse<Boolean> addAllowCallTime(@RequestBody @Valid SystemJobTimeReqDto systemJobTimeReqDto) {

        return CommonResponse.successCommonResponse(systemJobTimeService.addAllowCallTime(systemJobTimeReqDto));
    }




    @ApiOperation("查询系统允许拨打时间设置")
    @RequestMapping(value = "/system/call-time", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "查询系统允许拨打时间设置")
    public CommonResponse<List<SystemJobTimeRspDTO>> getAllowCallTime(@RequestParam String contractNo){
       return CommonResponse.successCommonResponse( systemJobTimeService.getAllowCallTime(contractNo));
    }
}
