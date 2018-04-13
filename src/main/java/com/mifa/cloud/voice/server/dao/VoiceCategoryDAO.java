package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.VoiceCategoryDO;
import com.mifa.cloud.voice.server.pojo.VoiceCategoryDOExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoiceCategoryDAO {
    long countByExample(VoiceCategoryDOExample example);

    int deleteByExample(VoiceCategoryDOExample example);

    int deleteByPrimaryKey(Integer categoryId);

    int insert(VoiceCategoryDO record);

    int insertSelective(VoiceCategoryDO record);

    List<VoiceCategoryDO> selectByExample(VoiceCategoryDOExample example);

    VoiceCategoryDO selectByPrimaryKey(Integer categoryId);

    int updateByExampleSelective(@Param("record") VoiceCategoryDO record, @Param("example") VoiceCategoryDOExample example);

    int updateByExample(@Param("record") VoiceCategoryDO record, @Param("example") VoiceCategoryDOExample example);

    int updateByPrimaryKeySelective(VoiceCategoryDO record);

    int updateByPrimaryKey(VoiceCategoryDO record);
}