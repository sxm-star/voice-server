package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.RoleDTO;
import com.mifa.cloud.voice.server.commons.dto.RoleResourceDTO;
import com.mifa.cloud.voice.server.dao.SystemResourceDAO;
import com.mifa.cloud.voice.server.dao.SystemRoleResourceDAO;
import com.mifa.cloud.voice.server.pojo.SystemRoleResourceDO;
import com.mifa.cloud.voice.server.pojo.SystemRoleResourceDOExample;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
   private List<RoleResourceDTO> findSystemRoleResource(Long roleId) {
        SystemRoleResourceDOExample criteriaRoleResource = new SystemRoleResourceDOExample();
        criteriaRoleResource.createCriteria().andRoleIdEqualTo(roleId);
        List<SystemRoleResourceDO> list = systemRoleResourceDAO.selectByExample(criteriaRoleResource);
        if (list != null || !list.isEmpty()) {
            List<RoleResourceDTO> roleResourceDTOs = new ArrayList<>();
            list.forEach(systemRoleResourceDO -> roleResourceDTOs.add(BaseBeanUtils.convert(systemRoleResourceDO,RoleResourceDTO.class)));
            return roleResourceDTOs;
        }
        return Collections.emptyList();
    }

    public Map<Long,RoleResourceDTO> findRoleResourceList(RoleDTO...roles){
        Map<Long, RoleResourceDTO> resourceMap = new HashMap<>();;
        for (RoleDTO role : roles) {
            if (role.getRoleStatus()!=null&&role.getRoleStatus()==0) {
                List<RoleResourceDTO> roleResources = findSystemRoleResource(role.getId());
                if (roleResources!=null || !roleResources.isEmpty()){
                    roleResources.forEach(roleResourceDTO -> { resourceMap.put(roleResourceDTO.getResourceId(), roleResourceDTO);});
                    return  resourceMap;
                }
            }
        }
        return Collections.EMPTY_MAP;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateRoleResource(Long roleId, Map<Long, RoleResourceDTO> map, Long[] ids) {
        for (Long resourceId : ids) {
            RoleResourceDTO newRoleResource= map.remove(resourceId);
            if (newRoleResource==null) {
                SystemRoleResourceDO roleResourceDO = new SystemRoleResourceDO();
                roleResourceDO.setRoleId(roleId);
                roleResourceDO.setResourceId(resourceId);
                systemRoleResourceDAO.insert(roleResourceDO);
            }
        }
        for (RoleResourceDTO removeResource : map.values()) {
            systemRoleResourceDAO.deleteByPrimaryKey(removeResource.getId());
        }
    }


}
