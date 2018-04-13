package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.SystemKeyValue;
import com.mifa.cloud.voice.server.pojo.SystemKeyValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemKeyValueMapper {
    int countByExample(SystemKeyValueExample example);

    int deleteByExample(SystemKeyValueExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SystemKeyValue record);

    int insertSelective(SystemKeyValue record);

    List<SystemKeyValue> selectByExample(SystemKeyValueExample example);

    SystemKeyValue selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SystemKeyValue record, @Param("example") SystemKeyValueExample example);

    int updateByExample(@Param("record") SystemKeyValue record, @Param("example") SystemKeyValueExample example);

    int updateByPrimaryKeySelective(SystemKeyValue record);

    int updateByPrimaryKey(SystemKeyValue record);
}