package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.SystemRoleResourceDO;
import com.mifa.cloud.voice.server.pojo.SystemRoleResourceDOExample;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.NotEmpty;

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