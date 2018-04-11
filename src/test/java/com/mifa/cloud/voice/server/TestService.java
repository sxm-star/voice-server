package com.mifa.cloud.voice.server;

import com.alibaba.fastjson.JSON;
import com.mifa.cloud.voice.server.commons.dto.ResourceDto;
import com.mifa.cloud.voice.server.commons.dto.RoleDto;
import com.mifa.cloud.voice.server.commons.dto.RoleResourceDto;
import com.mifa.cloud.voice.server.commons.enums.ResouceStatusEnum;
import com.mifa.cloud.voice.server.service.SystemResourceService;
import com.mifa.cloud.voice.server.service.SystemRoleResourceService;
import com.mifa.cloud.voice.server.service.SystemRoleService;
import com.mifa.cloud.voice.server.utils.ResourceUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author: sxm
 * @date: 2018/4/10 20:00
 * @version: v1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestService {

    @Autowired
    public SystemRoleResourceService roleResourceService;
    @Autowired
    public SystemResourceService resourceService;
    @Autowired
    public SystemRoleService roleService;

    @Test
    public void testRole(){
        RoleDto role = roleService.getRoleById(1l);
        // 取出用户对应的权限，放入session中
        Map<Long, RoleResourceDto> map = roleResourceService
                .findRoleResourceList(role);
        List<ResourceDto> parentResourceList = resourceService
                .findResourceList(0l, null,ResouceStatusEnum.NORMAL);
        resourceService.findAllResourceList(parentResourceList, ResouceStatusEnum.NORMAL);
        List<ResourceDto> resourceList = ResourceUtil.getResource(
                parentResourceList, map);
        System.out.println( map);
        System.out.println(JSON.toJSONString(map));
        System.out.println(JSON.toJSONString(resourceList));
    }
}
