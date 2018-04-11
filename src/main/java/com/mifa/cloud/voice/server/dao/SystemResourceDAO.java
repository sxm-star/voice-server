package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.SystemResourceDO;
import com.mifa.cloud.voice.server.pojo.SystemResourceDOExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SystemResourceDAO {
    long countByExample(SystemResourceDOExample example);

    int deleteByExample(SystemResourceDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SystemResourceDO record);

    int insertSelective(SystemResourceDO record);

    List<SystemResourceDO> selectByExample(SystemResourceDOExample example);

    SystemResourceDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SystemResourceDO record, @Param("example") SystemResourceDOExample example);

    int updateByExample(@Param("record") SystemResourceDO record, @Param("example") SystemResourceDOExample example);

    int updateByPrimaryKeySelective(SystemResourceDO record);

    int updateByPrimaryKey(SystemResourceDO record);
}