package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.RoleDto;
import com.mifa.cloud.voice.server.dao.SystemRoleInfoDAO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: sxm
 * @date: 2018/4/10 15:06
 * @version: v1.0.0
 */
@Service
public class SystemRoleService {
    @Autowired
    SystemRoleInfoDAO systemRoleInfoDAO;

    /**
     * 获取当前角色信息
     * @param id
     * @return
     */
    public RoleDto getRoleById(Long id){
        return BaseBeanUtils.convert(systemRoleInfoDAO.selectByPrimaryKey(id),RoleDto.class) ;
    }
}
