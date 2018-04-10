package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.commons.dto.ResourceDto;
import com.mifa.cloud.voice.server.commons.enums.ResouceStatusEnum;
import com.mifa.cloud.voice.server.commons.enums.ResouceTypeEnum;
import com.mifa.cloud.voice.server.dao.SystemResourceDAO;
import com.mifa.cloud.voice.server.pojo.SystemResourceDO;
import com.mifa.cloud.voice.server.pojo.SystemResourceDOExample;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: sxm
 * @date: 2018/4/10 15:07
 * @version: v1.0.0
 */
@Service
public class ResourceService {

    @Autowired
    SystemResourceDAO systemResourceDAO;

    /**
     * 根据资源ID 类型 状态查询当前资源列表
     * @param pid 父类目ID
     * @param resouceTypeEnum
     * @param resouceStatusEnum
     * @return
     */
    List<ResourceDto> findResourceList(Long pid, ResouceTypeEnum resouceTypeEnum , ResouceStatusEnum resouceStatusEnum){
        SystemResourceDOExample resourceDOExample = new SystemResourceDOExample();
        SystemResourceDOExample.Criteria criteria = resourceDOExample.createCriteria();
        if (resouceTypeEnum!=null){
            criteria.andResourceTypeEqualTo(resouceTypeEnum.getCode());
        }
        if(resouceStatusEnum!=null){
            criteria.andResourceStatusEqualTo(resouceStatusEnum.getCode());
        }
        resourceDOExample.setOrderByClause("order by resource_order");

        List<SystemResourceDO> resourceDOList = systemResourceDAO.selectByExample(resourceDOExample);
        if (resourceDOList!=null || !resourceDOList.isEmpty()){
            List<ResourceDto> resourceDtos = new ArrayList<>();
            resourceDOList.forEach(systemResourceDO -> resourceDtos.add(BaseBeanUtils.convert(systemResourceDO,ResourceDto.class)) );
            return resourceDtos;
        }
        return Collections.emptyList();
    }

    /**
     * 加载子资源文件列表
     * @param resourceDtos
     * @param resouceStatusEnum
     * @return
     */
    List<ResourceDto> findAllResourceList(List<ResourceDto> resourceDtos,ResouceStatusEnum resouceStatusEnum){
        if(resourceDtos==null || resourceDtos.isEmpty()){
            return Collections.emptyList();
        }
        resourceDtos.forEach(resourceDto -> {
           resourceDto.setChildResource(findResourceList(resourceDto.getId(),null,ResouceStatusEnum.NORMAL));
        });
        return resourceDtos;
    }
}
