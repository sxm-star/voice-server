package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.pojo.VoiceNotifyLogDO;
import com.mifa.cloud.voice.server.pojo.VoiceNotifyLogDOExample;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.NotEmpty;

public interface VoiceNotifyLogDAO {
    long countByExample(VoiceNotifyLogDOExample example);

    int deleteByExample(VoiceNotifyLogDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VoiceNotifyLogDO record);

    int insertSelective(VoiceNotifyLogDO record);

    List<VoiceNotifyLogDO> selectByExample(VoiceNotifyLogDOExample example);

    VoiceNotifyLogDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VoiceNotifyLogDO record, @Param("example") VoiceNotifyLogDOExample example);

    int updateByExample(@Param("record") VoiceNotifyLogDO record, @Param("example") VoiceNotifyLogDOExample example);

    int updateByPrimaryKeySelective(VoiceNotifyLogDO record);

    int updateByPrimaryKey(VoiceNotifyLogDO record);
}