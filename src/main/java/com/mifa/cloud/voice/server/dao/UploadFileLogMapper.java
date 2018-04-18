package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.UploadFileLog;
import com.mifa.cloud.voice.server.pojo.UploadFileLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadFileLogMapper {
    int countByExample(UploadFileLogExample example);

    int deleteByExample(UploadFileLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UploadFileLog record);

    int insertSelective(UploadFileLog record);

    List<UploadFileLog> selectByExample(UploadFileLogExample example);

    UploadFileLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UploadFileLog record, @Param("example") UploadFileLogExample example);

    int updateByExample(@Param("record") UploadFileLog record, @Param("example") UploadFileLogExample example);

    int updateByPrimaryKeySelective(UploadFileLog record);

    int updateByPrimaryKey(UploadFileLog record);
}