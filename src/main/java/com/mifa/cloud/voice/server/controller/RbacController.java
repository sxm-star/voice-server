package com.mifa.cloud.voice.server.controller;

import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.ResourceDto;
import com.mifa.cloud.voice.server.commons.dto.RoleDto;
import com.mifa.cloud.voice.server.commons.dto.RoleResourceDto;
import com.mifa.cloud.voice.server.commons.enums.ResouceStatusEnum;
import com.mifa.cloud.voice.server.service.SystemResourceService;
import com.mifa.cloud.voice.server.service.SystemRoleResourceService;
import com.mifa.cloud.voice.server.service.SystemRoleService;
import com.mifa.cloud.voice.server.utils.ResourceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: sxm
 * @dae: 2018/4/11 07:56
 * @version: v1.0.0
 */
@RestController
@CrossOrigin
@Api("用户权限管理相关API.")
@RequestMapping(AppConst.BASE_AUTH_PATH + "v1")
public class RbacController {
    @Autowired
    public SystemRoleResourceService roleResourceService;
    @Autowired
    public SystemResourceService resourceService;
    @Autowired
    public SystemRoleService roleService;

    @ApiOperation("获取用户的角色对应的资源列表")
    @RequestMapping(value = "/role-resource-list",method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
            , @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "roleId", required = false, dataType = "string",value = "角色ID,选填")
    })
    @Loggable(descp = "用户登录操作")
    public List<ResourceDto> findRoleResourceList(String contractNo, String roleId){
        RoleDto role = roleService.getRoleById(Long.parseLong(roleId));
        Map<Long, RoleResourceDto> map = roleResourceService
                .findRoleResourceList(role);
        List<ResourceDto> parentResourceList = resourceService
                .findResourceList(0L, null, ResouceStatusEnum.NORMAL);
        resourceService.findAllResourceList(parentResourceList, ResouceStatusEnum.NORMAL);
        List<ResourceDto> resourceList = ResourceUtil.getResource(
                parentResourceList, map);
        return  resourceList;
    }
}
