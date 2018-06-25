package com.mifa.cloud.voice.server.dao;

import com.mifa.cloud.voice.server.commons.dto.PointDTO;
import com.mifa.cloud.voice.server.pojo.AccountCapitalDetailDO;
import com.mifa.cloud.voice.server.pojo.AccountCapitalDetailDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountCapitalDetailDAO {
    long countByExample(AccountCapitalDetailDOExample example);

    int deleteByExample(AccountCapitalDetailDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AccountCapitalDetailDO record);

    int insertSelective(AccountCapitalDetailDO record);

    List<AccountCapitalDetailDO> selectByExample(AccountCapitalDetailDOExample example);

    AccountCapitalDetailDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AccountCapitalDetailDO record, @Param("example") AccountCapitalDetailDOExample example);

    int updateByExample(@Param("record") AccountCapitalDetailDO record, @Param("example") AccountCapitalDetailDOExample example);

    int updateByPrimaryKeySelective(AccountCapitalDetailDO record);

    int updateByPrimaryKey(AccountCapitalDetailDO record);

    /**
     * 统计管理员首页前 n天拨打数据
     * @param dayCnt
     * @return
     */
    List<PointDTO> queryTotalByDataType(@Param("dayCnt") Integer dayCnt);

    Long queryTotalRecharge(String transType);
}