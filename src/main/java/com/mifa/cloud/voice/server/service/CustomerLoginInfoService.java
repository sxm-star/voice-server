package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.CustomerInfoReqDTO;
import com.mifa.cloud.voice.server.commons.dto.CustomerInfoRspDTO;
import com.mifa.cloud.voice.server.commons.dto.CustomerInfoUpdateReqDTO;
import com.mifa.cloud.voice.server.commons.dto.PageDTO;
import com.mifa.cloud.voice.server.commons.enums.UserStatusEnum;
import com.mifa.cloud.voice.server.dao.CustomerLoginInfoMapper;
import com.mifa.cloud.voice.server.pojo.AccountCapitalDO;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfo;
import com.mifa.cloud.voice.server.pojo.CustomerLoginInfoExample;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import com.mifa.cloud.voice.server.utils.PasswordUtil;
import com.mifa.cloud.voice.server.utils.SeqProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/10.
 */
@Service
@Slf4j
public class CustomerLoginInfoService {

    @Autowired
    private CustomerLoginInfoMapper customerLoginInfoMapper;
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private AccountCapitalService capitalService;

    /**
     * 根据loginName查询
     */
    public CustomerLoginInfo findByLoginName(String loginName) {
        CustomerLoginInfoExample example = new CustomerLoginInfoExample();
        CustomerLoginInfoExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<CustomerLoginInfo> customerLoginInfos = customerLoginInfoMapper.selectByExample(example);
        if (customerLoginInfos.isEmpty()) {
            return null;
        }
        return customerLoginInfos.get(0);
    }

    /**
     * 根据手机号查询
     */
    public CustomerLoginInfo findByLoginMobile(String mobile) {
        if(StringUtils.isEmpty(mobile)) {
            return null;
        }
        CustomerLoginInfoExample example = new CustomerLoginInfoExample();
        CustomerLoginInfoExample.Criteria criteria = example.createCriteria();
        criteria.andMobileEqualTo(mobile);
        List<CustomerLoginInfo> customerLoginInfos = customerLoginInfoMapper.selectByExample(example);
        if (customerLoginInfos.isEmpty()) {
            return null;
        }
        return customerLoginInfos.get(0);
    }

    /**
     * 根据用户名或手机号查询
     */
    public CustomerLoginInfo findByMobileOrLoginName(String loginName, String mobile) {
        if(StringUtils.isEmpty(loginName) && StringUtils.isEmpty(mobile)) {
            return null;
        }
        CustomerLoginInfoExample example = new CustomerLoginInfoExample();
        CustomerLoginInfoExample.Criteria criteria = example.createCriteria();
        criteria.andMobileEqualTo(mobile);
        example.or(new CustomerLoginInfoExample().createCriteria().andLoginNameEqualTo(loginName));
        List<CustomerLoginInfo> customerLoginInfos = customerLoginInfoMapper.selectByExample(example);
        if (customerLoginInfos.isEmpty()) {
            return null;
        }
        return customerLoginInfos.get(0);
    }

    /**
     * 插入记录（只插入参数非空字段）
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(CustomerLoginInfo record) {
        if (StringUtils.isEmpty(record.getContractNo())) {
            record.setContractNo(SeqProducerUtil.getContractNo());
        }
        return customerLoginInfoMapper.insertSelective(record);
    }


    /**
     * 根据id修改记录（只修改参数非空字段）
     */
    public int updateByPrimaryKeySelective(CustomerLoginInfo record) {
        return customerLoginInfoMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据id查询记录
     * */
    @Transactional(rollbackFor = Exception.class)
    public CustomerLoginInfo selectByPrimaryKey(String contractNo) {
        return customerLoginInfoMapper.selectByPrimaryKey(contractNo);
    }


   public PageDTO<CustomerInfoRspDTO> queryCustomerList(CustomerInfoReqDTO customerInfoReqDTO,Integer pageSize, Integer pageNum){
       CustomerLoginInfoExample example = new CustomerLoginInfoExample();
       CustomerLoginInfoExample.Criteria criteria = example.createCriteria();
       if (customerInfoReqDTO!=null&&customerInfoReqDTO.getContractNo()!=null){
           criteria.andContractNoEqualTo(customerInfoReqDTO.getContractNo());
       }
       if (customerInfoReqDTO!=null && customerInfoReqDTO.getLoginName()!=null){
           criteria.andLoginNameEqualTo(customerInfoReqDTO.getLoginName());
       }
       if (customerInfoReqDTO!=null && customerInfoReqDTO.getMobile()!=null){
           criteria.andMobileEqualTo(customerInfoReqDTO.getMobile());
       }
       // 加入分页
       PageHelper.startPage(pageSize, pageNum);
       example.setOrderByClause(" created_at desc");
       PageDTO<CustomerInfoRspDTO> pageDTO = new PageDTO<>();
       List<CustomerLoginInfo> customerLoginInfos =customerLoginInfoMapper.selectByExample(example);
        if (customerLoginInfos!=null&& customerLoginInfos.size()>0){
            PageInfo<CustomerLoginInfo> pageInfo = new PageInfo<CustomerLoginInfo>(customerLoginInfos);
            List<CustomerInfoRspDTO> rspList = new ArrayList<>();
            customerLoginInfos.forEach(customerLoginInfo -> {
                CustomerInfoRspDTO customerInfoRspDTO =  BaseBeanUtils.convert(customerLoginInfo,CustomerInfoRspDTO.class);
                AccountCapitalDO accountCapitalDO =  capitalService.queryOne(AccountCapitalDO.builder().contractNo(customerInfoRspDTO.getContractNo()).build());
                if (null!=accountCapitalDO){
                    customerInfoRspDTO.setAvailableAmount(accountCapitalDO.getAvailableAmount());
                }else {
                    customerInfoRspDTO.setAvailableAmount(0L);
                }
                customerInfoRspDTO.setLoginStatus(UserStatusEnum.getDesc(customerInfoRspDTO.getLoginStatus()));
                rspList.add(customerInfoRspDTO);
            });
             pageDTO =BaseBeanUtils.convert(pageInfo,PageDTO.class);
            pageDTO.setList(rspList);
        }
       return pageDTO;
   }

   public Boolean updateCustomer(CustomerInfoUpdateReqDTO customerInfoReqDTO){
       CustomerLoginInfo customerLoginInfo =  BaseBeanUtils.convert(customerInfoReqDTO,CustomerLoginInfo.class);

       try {
           if (customerInfoReqDTO.getLoginPasswd()!=null&&customerInfoReqDTO.getLoginPasswd().length()>=6 ){
               // 密码加密处理
               Map<String, String> generateMap = passwordUtil.generate(customerInfoReqDTO.getLoginPasswd());
               customerLoginInfo.setSalt(generateMap.get("salt"));
               customerLoginInfo.setLoginPasswd(generateMap.get("encryptPassword"));
           }else {
               customerLoginInfo.setLoginPasswd(null);

           }
           if (customerInfoReqDTO.getLoginStatus()!=null){
               customerLoginInfo.setLoginStatus(UserStatusEnum.getCode(customerInfoReqDTO.getLoginStatus()));
           }

           customerLoginInfo.setUpdatedBy(customerInfoReqDTO.getCreatedBy());
           customerLoginInfo.setUpdatedAt(new Date());
           int cnt =  customerLoginInfoMapper.updateByPrimaryKeySelective(customerLoginInfo);
           return cnt>0?Boolean.TRUE:Boolean.FALSE;
       }catch (Exception e){
           log.error("错误信息:{}",e);
           return Boolean.FALSE;
       }

   }

}
