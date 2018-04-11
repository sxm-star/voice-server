package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.RoleDto;
import com.mifa.cloud.voice.server.commons.enums.RoleEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.dao.SystemRoleInfoDAO;
import com.mifa.cloud.voice.server.pojo.SystemRoleInfoDO;
import com.mifa.cloud.voice.server.pojo.SystemRoleInfoDOExample;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<RoleDto> getRoleList(String roleName, RoleEnum roleEnum, StatusEnum statusEnum){
        SystemRoleInfoDOExample roleInfoDOExample = new SystemRoleInfoDOExample();
        SystemRoleInfoDOExample.Criteria criteria = roleInfoDOExample.createCriteria();
        if (StringUtils.isNotEmpty(roleName)){
            criteria.andRoleNameLike(roleName);
        }
        if(roleEnum!=null){
            criteria.andRoleTypeEqualTo(roleEnum.getCode());
        }
        if(statusEnum!=null){
            criteria.andRoleStatusEqualTo(statusEnum.getCode());
        }
        roleInfoDOExample.setOrderByClause(" id asc ");
        List<SystemRoleInfoDO> list = systemRoleInfoDAO.selectByExample(roleInfoDOExample);
        if (CollectionUtils.isNotEmpty(list)){
            List<RoleDto> result = new ArrayList<>();
            list.forEach(systemRoleInfoDO -> result.add(BaseBeanUtils.convert(systemRoleInfoDO,RoleDto.class)));
            return  result;
        }
        return Collections.EMPTY_LIST;
    }

    public boolean insertRole(RoleDto roleDto){
        int cnt = systemRoleInfoDAO.insertSelective(BaseBeanUtils.convert(roleDto,SystemRoleInfoDO.class));
        return cnt>0?Boolean.TRUE:Boolean.FALSE;
    }

    public boolean updateRole(RoleDto roleDto){
        int cnt = systemRoleInfoDAO.updateByPrimaryKeySelective(BaseBeanUtils.convert(roleDto,SystemRoleInfoDO.class));
        return cnt>0?Boolean.TRUE:Boolean.FALSE;
    }

    /**
     * 逻辑删除
     * @param roleDto
     * @return
     */
    public boolean delRole(RoleDto roleDto){
         int cnt = systemRoleInfoDAO.updateByPrimaryKey(BaseBeanUtils.convert(roleDto,SystemRoleInfoDO.class));
         return cnt>0?Boolean.TRUE:Boolean.FALSE;
    }
}
