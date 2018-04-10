package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.SystemRoleResourceDO;
import com.mifa.cloud.voice.server.pojo.SystemRoleResourceDOExample;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SystemRoleResourceDAO {
    long countByExample(SystemRoleResourceDOExample example);

    int deleteByExample(SystemRoleResourceDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SystemRoleResourceDO record);

    int insertSelective(SystemRoleResourceDO record);

    List<SystemRoleResourceDO> selectByExample(SystemRoleResourceDOExample example);

    SystemRoleResourceDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SystemRoleResourceDO record, @Param("example") SystemRoleResourceDOExample example);

    int updateByExample(@Param("record") SystemRoleResourceDO record, @Param("example") SystemRoleResourceDOExample example);

    int updateByPrimaryKeySelective(SystemRoleResourceDO record);

    int updateByPrimaryKey(SystemRoleResourceDO record);
}