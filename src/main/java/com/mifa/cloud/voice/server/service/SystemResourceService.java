package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.ResourceDTO;
import com.mifa.cloud.voice.server.commons.enums.ResouceTypeEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.dao.SystemResourceDAO;
import com.mifa.cloud.voice.server.pojo.SystemResourceDO;
import com.mifa.cloud.voice.server.pojo.SystemResourceDOExample;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: sxm
 * @date: 2018/4/10 15:07
 * @version: v1.0.0
 */
@Service
public class SystemResourceService {

    @Autowired
    SystemResourceDAO systemResourceDAO;

    /**
     * 根据资源ID 类型 状态查询当前资源列表
     *
     * @param pid               父类目ID
     * @param resouceTypeEnum
     * @param resouceStatusEnum
     * @return
     */
    public List<ResourceDTO> findResourceList(Long pid, ResouceTypeEnum resouceTypeEnum, StatusEnum resouceStatusEnum) {
        SystemResourceDOExample resourceDOExample = new SystemResourceDOExample();
        SystemResourceDOExample.Criteria criteria = resourceDOExample.createCriteria();
        criteria.andPidEqualTo(pid);
        if (resouceTypeEnum != null) {
            criteria.andResourceTypeEqualTo(resouceTypeEnum.getCode());
        }
        if (resouceStatusEnum != null) {
            criteria.andResourceStatusEqualTo(resouceStatusEnum.getCode());
        }
        resourceDOExample.setOrderByClause(" resource_order asc ");

        List<SystemResourceDO> resourceDOList = systemResourceDAO.selectByExample(resourceDOExample);
        if (resourceDOList != null || !resourceDOList.isEmpty()) {
            List<ResourceDTO> resourceDTOs = new ArrayList<>();
            resourceDOList.forEach(systemResourceDO -> resourceDTOs.add(BaseBeanUtils.convert(systemResourceDO, ResourceDTO.class)));
            return resourceDTOs;
        }
        return Collections.emptyList();
    }

    /**
     * 加载子资源文件列表
     *
     * @param resourceDTOs
     * @param resouceStatusEnum
     * @return
     */
    public List<ResourceDTO> findAllResourceList(List<ResourceDTO> resourceDTOs, StatusEnum resouceStatusEnum) {
        if (resourceDTOs == null || resourceDTOs.isEmpty()) {
            return Collections.emptyList();
        }
        resourceDTOs.forEach(resourceDto -> {
            resourceDto.setChildResource(findResourceList(resourceDto.getId(), null, StatusEnum.NORMAL));
        });
        return resourceDTOs;
    }

    /**
     * 菜单设置
     *
     * @param resourceDTOs
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addMenu(List<ResourceDTO> resourceDTOs) {
        if (CollectionUtils.isNotEmpty(resourceDTOs)) {
            List<SystemResourceDO> resourceDOs = new ArrayList<>();
            resourceDTOs.forEach(resourceDto -> {
                resourceDOs.add(BaseBeanUtils.convert(resourceDto, SystemResourceDO.class));
            });
            int cnt = systemResourceDAO.insertBatch(resourceDOs);
            return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    /**
     * 菜单更新状态 地址栏操作
     * @param resourceDTOs
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean alterMenu(List<ResourceDTO> resourceDTOs) {
        if (CollectionUtils.isNotEmpty(resourceDTOs)) {
            int cnt = 0;
            for (ResourceDTO resourceDTO : resourceDTOs) {
                SystemResourceDO resourceDO =  BaseBeanUtils.convert(resourceDTO, SystemResourceDO.class);
                int tmpCnt = systemResourceDAO.updateByPrimaryKeySelective(resourceDO);
                cnt += tmpCnt;
            }
            return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        return Boolean.FALSE;
    }
}
