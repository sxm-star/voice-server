package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.RoleDTO;
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
import org.springframework.transaction.annotation.Transactional;

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
     *
     * @param id
     * @return
     */
    public RoleDTO getRoleById(Long id) {
        return BaseBeanUtils.convert(systemRoleInfoDAO.selectByPrimaryKey(id), RoleDTO.class);
    }

    public List<RoleDTO> getRoleList(String roleName, RoleEnum roleEnum, StatusEnum statusEnum) {
        SystemRoleInfoDOExample roleInfoDOExample = new SystemRoleInfoDOExample();
        SystemRoleInfoDOExample.Criteria criteria = roleInfoDOExample.createCriteria();
        if (StringUtils.isNotEmpty(roleName)) {
            criteria.andRoleNameLike(roleName);
        }
        if (roleEnum != null) {
            criteria.andRoleTypeEqualTo(roleEnum.getCode());
        }
        if (statusEnum != null) {
            criteria.andRoleStatusEqualTo(statusEnum.getCode());
        }
        roleInfoDOExample.setOrderByClause(" id asc ");
        List<SystemRoleInfoDO> list = systemRoleInfoDAO.selectByExample(roleInfoDOExample);
        if (CollectionUtils.isNotEmpty(list)) {
            List<RoleDTO> result = new ArrayList<>();
            list.forEach(systemRoleInfoDO -> result.add(BaseBeanUtils.convert(systemRoleInfoDO, RoleDTO.class)));
            return result;
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insertRole(RoleDTO roleDTO) {
        int cnt = systemRoleInfoDAO.insertSelective(BaseBeanUtils.convert(roleDTO, SystemRoleInfoDO.class));
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(RoleDTO roleDTO) {
        int cnt = systemRoleInfoDAO.updateByPrimaryKeySelective(BaseBeanUtils.convert(roleDTO, SystemRoleInfoDO.class));
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delRoles(List<Long> roleIds) {
        SystemRoleInfoDOExample roleInfoDOExample = new SystemRoleInfoDOExample();
        SystemRoleInfoDOExample.Criteria criteria = roleInfoDOExample.createCriteria();
        criteria.andIdIn(roleIds);
        int cnt = systemRoleInfoDAO.deleteByExample(roleInfoDOExample);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
