package com.mifa.cloud.voice.server.controller;

import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.dto.MenuDto;
import com.mifa.cloud.voice.server.commons.dto.ResourceDto;
import com.mifa.cloud.voice.server.commons.dto.ResourceFunctionModelDto;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
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

    @ApiOperation("添加系统菜单")
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "添加系统菜单")
    public CommonResponse<Boolean> addMenu(@RequestBody @Valid List<ResourceFunctionModelDto> resourceFunctionModelDtos) {
        if (CollectionUtils.isEmpty(resourceFunctionModelDtos)){
            return CommonResponse.failCommonResponse();
        }
        List<ResourceDto> resourceDtos = new ArrayList<>();
        resourceFunctionModelDtos.forEach(resourceFunctionModelDto -> {
            ResourceDto resourceDto = BaseBeanUtils.convert(resourceFunctionModelDto, ResourceDto.class);
            resourceDto.setResourceStatus(resourceFunctionModelDto.getStatusEnum().getCode());
            resourceDto.setResourceGroup(resourceFunctionModelDto.getResourceGroup().getCode());
            resourceDto.setResourceType(resourceFunctionModelDto.getResouceTypeEnum().getCode());
            resourceDtos.add(resourceDto);
        });
        return CommonResponse.successCommonResponse(resourceService.addMenu(resourceDtos));
    }


    @ApiOperation("获取同一层级菜单列表")
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @Loggable(descp = "获取同一层级菜单列表")
    public CommonResponse<List<MenuDto>> findMenuList(@RequestParam(required = true, defaultValue = "0") Long pid, @RequestParam(required = true) StatusEnum statusEnum) {
        List<ResourceDto> list = resourceService.findResourceList(pid, null, statusEnum);
        if (CollectionUtils.isNotEmpty(list)) {
            List<MenuDto> resultList = new ArrayList<>();
            list.forEach(resourceDto -> {
                resultList.add(BaseBeanUtils.convert(resourceDto, MenuDto.class));
            });
            return CommonResponse.successCommonResponse(resultList);
        }
        return CommonResponse.successCommonResponse(Collections.EMPTY_LIST);
    }
}
