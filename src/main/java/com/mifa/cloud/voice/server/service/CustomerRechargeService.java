package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.PageDTO;
import com.mifa.cloud.voice.server.dao.CustomerRechargeMapper;
import com.mifa.cloud.voice.server.commons.dto.CustomerRechargeVO;
import com.mifa.cloud.voice.server.pojo.CustomerRecharge;
import com.mifa.cloud.voice.server.pojo.CustomerRechargeExample;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */
@Service
public class CustomerRechargeService {

    @Autowired
    private CustomerRechargeMapper customerRechargeMapper;

    /**
     * 充值记录列表
     */
    public PageDTO<CustomerRechargeVO> selectRechargeList(String rechargeName, String contractNo, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        CustomerRechargeExample example = new CustomerRechargeExample();
        CustomerRechargeExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(rechargeName)) {
            criteria.andRechargeNameLike("%" + rechargeName + "%");
        }
        if(StringUtils.isNotEmpty(contractNo)) {
            criteria.andContractNoEqualTo(contractNo);
        }
        example.setOrderByClause("recharge_time DESC");
        List<CustomerRecharge> list = customerRechargeMapper.selectByExample(example);
        List<CustomerRechargeVO> voList = new ArrayList<CustomerRechargeVO>();

        PageInfo<CustomerRecharge> pageInfo = new PageInfo<CustomerRecharge>(list);
        pageInfo.getList().stream()
                .forEach(
                        item -> voList.add(BaseBeanUtils.convert(item, CustomerRechargeVO.class))
                );
        PageDTO<CustomerRechargeVO> pageResult = BaseBeanUtils.convert(pageInfo, PageDTO.class);
        pageResult.setList(voList);
        return pageResult;
    }


}
