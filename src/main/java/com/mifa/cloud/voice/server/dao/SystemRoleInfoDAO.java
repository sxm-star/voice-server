package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.SystemRoleInfoDO;
import com.mifa.cloud.voice.server.pojo.SystemRoleInfoDOExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemRoleInfoDAO {
    long countByExample(SystemRoleInfoDOExample example);

    int deleteByExample(SystemRoleInfoDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SystemRoleInfoDO record);

    int insertSelective(SystemRoleInfoDO record);

    List<SystemRoleInfoDO> selectByExample(SystemRoleInfoDOExample example);

    SystemRoleInfoDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SystemRoleInfoDO record, @Param("example") SystemRoleInfoDOExample example);

    int updateByExample(@Param("record") SystemRoleInfoDO record, @Param("example") SystemRoleInfoDOExample example);

    int updateByPrimaryKeySelective(SystemRoleInfoDO record);

    int updateByPrimaryKey(SystemRoleInfoDO record);
}