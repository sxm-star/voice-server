package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.RoleDto;
import com.mifa.cloud.voice.server.commons.dto.RoleResourceDto;
import com.mifa.cloud.voice.server.dao.SystemResourceDAO;
import com.mifa.cloud.voice.server.dao.SystemRoleResourceDAO;
import com.mifa.cloud.voice.server.pojo.SystemRoleResourceDO;
import com.mifa.cloud.voice.server.pojo.SystemRoleResourceDOExample;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: sxm
 * @date: 2018/4/10 15:08
 * @version: v1.0.0
 */
@Service
public class SystemRoleResourceService {
    @Autowired
    SystemResourceDAO systemResourceDAO;
    @Autowired
    SystemRoleResourceDAO systemRoleResourceDAO;

    /**
     * 根据角色ID获取当前角色对应的角色资源列表
     * @param roleId
     * @return
     */
   private List<RoleResourceDto> findSystemRoleResource(Long roleId) {
        SystemRoleResourceDOExample criteriaRoleResource = new SystemRoleResourceDOExample();
        criteriaRoleResource.createCriteria().andRoleIdEqualTo(roleId);
        List<SystemRoleResourceDO> list = systemRoleResourceDAO.selectByExample(criteriaRoleResource);
        if (list != null || !list.isEmpty()) {
            List<RoleResourceDto> roleResourceDtos  = new ArrayList<>();
            list.forEach(systemRoleResourceDO -> roleResourceDtos.add(BaseBeanUtils.convert(systemRoleResourceDO,RoleResourceDto.class)));
            return roleResourceDtos;
        }
        return Collections.emptyList();
    }

    public Map<Long,RoleResourceDto> findRoleResourceList(RoleDto ...roles){
        Map<Long, RoleResourceDto> resourceMap = new HashMap<>();;
        for (RoleDto role : roles) {
            if (role.getRoleStatus()!=null&&role.getRoleStatus()==0) {
                List<RoleResourceDto> roleResources = findSystemRoleResource(role.getId());
                if (roleResources!=null || !roleResources.isEmpty()){
                    roleResources.forEach(roleResourceDto -> { resourceMap.put(roleResourceDto.getResourceId(), roleResourceDto);});
                    return  resourceMap;
                }
            }
        }
        return Collections.EMPTY_MAP;
    }


}
