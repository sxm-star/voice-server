package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.ContactDto;
import com.mifa.cloud.voice.server.commons.dto.ContactQueryDto;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.dao.CustomerTaskUserContactsDAO;
import com.mifa.cloud.voice.server.dao.UploadFileLogMapper;
import com.mifa.cloud.voice.server.pojo.CustomerTaskUserContactsDO;
import com.mifa.cloud.voice.server.pojo.CustomerTaskUserContactsDOExample;
import com.mifa.cloud.voice.server.pojo.UploadFileLog;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import com.mifa.cloud.voice.server.utils.EncodesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: songxm
 * @date: 2018/4/12 10:13
 * @version: v1.0.0
 */
@Service
@Slf4j
public class ContactsService {
    @Autowired
    CustomerTaskUserContactsDAO contactsDAO;
    @Autowired
    UploadFileLogMapper uploadFileLogMapper;
    /**
     * 查询号码
     *
     * @return
     */
    public PageDto<ContactDto> queryContactList(ContactQueryDto contactQueryDto, Integer pageNum, Integer pageSize) {
        CustomerTaskUserContactsDO contactDo = BaseBeanUtils.convert(contactQueryDto,CustomerTaskUserContactsDO.class);

        try {
            CustomerTaskUserContactsDOExample example = new CustomerTaskUserContactsDOExample();
            CustomerTaskUserContactsDOExample.Criteria criteria = example.createCriteria();
            criteria.andContractNoEqualTo(contactQueryDto.getContractNo());
            if(StringUtils.isNotEmpty(contactQueryDto.getUserName())){
                criteria.andUserNameEqualTo(contactQueryDto.getUserName());
            }
            if(StringUtils.isNotEmpty(contactQueryDto.getUserPhone())){
                criteria.andUserPhoneEqualTo(contactQueryDto.getUserPhone());
            }
            if (StringUtils.isNotEmpty(contactQueryDto.getOrgName())){
                criteria.andOrgNameEqualTo(contactQueryDto.getOrgName());
            }
            example.setOrderByClause("created_at desc");
            PageHelper.startPage(pageNum,pageSize);
            List<CustomerTaskUserContactsDO> contactListDOs = contactsDAO.selectByExample(example);
            PageInfo<CustomerTaskUserContactsDO> pageInfo = new PageInfo<CustomerTaskUserContactsDO>(contactListDOs);
            if (pageInfo != null && CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<ContactDto> contactDtos = new ArrayList<>();
                pageInfo.getList().stream().forEach(contact -> contactDtos.add(BaseBeanUtils.convert(contact, ContactDto.class)));
                PageDto<ContactDto> pageResult = BaseBeanUtils.convert(pageInfo, PageDto.class);
                pageResult.setList(contactDtos);
                return pageResult;
            }
        } catch (Exception e) {
            log.error("查询异常:{}", e);
        }
        return null;
    }

    /**
     * 新增号码
     * @return
     */
    public Boolean insertContact(ContactDto contactDto) {
        CustomerTaskUserContactsDO contactDo = BaseBeanUtils.convert(contactDto, CustomerTaskUserContactsDO.class);
        contactDo.setTaskId(BaseStringUtils.uuid());
        contactDo.setCreatedBy(contactDto.getContractNo());
        int cnt = contactsDAO.insert(contactDo);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 号码修改
     * @return
     */
    public Boolean alterContact(Long id , ContactQueryDto contactQueryDto) {
        CustomerTaskUserContactsDO pre_ContactsDo = contactsDAO.selectByPrimaryKey(id);
        BaseBeanUtils.copyNoneNullProperties(contactQueryDto,pre_ContactsDo);
        pre_ContactsDo.setUpdatedBy(contactQueryDto.getContractNo());
        int cnt = contactsDAO.updateByPrimaryKeySelective(pre_ContactsDo);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 解析号码入库
     * @param list
     * @param contractNo
     * @param taskId
     * @param salt
     */
    @Transactional(rollbackFor = Exception.class)
    public void addContancts(List<Map<String, Object>> list, String contractNo, String taskId, String salt,Long fileId) {
        log.info("list size:{},contractNo:{},taskId:{},salt:{}",list.size(),contractNo,taskId,salt);
        if (CollectionUtils.isNotEmpty(list)) {
            List<CustomerTaskUserContactsDO> contactsDOs = new ArrayList<>();
            list.forEach(map -> {
                CustomerTaskUserContactsDO taskUserContactsDO = BaseBeanUtils.convert(map, CustomerTaskUserContactsDO.class);
                taskUserContactsDO.setTaskId(taskId);
                taskUserContactsDO.setContractNo(contractNo);
                taskUserContactsDO.setSalt(salt);
                String phone = taskUserContactsDO.getUserPhone();
                try {
                    taskUserContactsDO.setUserPhone(EncodesUtils.encodeBase64(EncodesUtils.aesEncrypt(phone.getBytes("UTF-8"), EncodesUtils.decodeBase64(salt))));
                } catch (Exception e) {
                    log.info("手机号:{}加密失败:{}", phone, e);
                }
                taskUserContactsDO.setCreatedAt(new Date());
                taskUserContactsDO.setCreatedBy(contractNo);
                contactsDOs.add(taskUserContactsDO);
                log.info("批量保存 size: {}",contactsDOs.size());
            });
           int cnt =  contactsDAO.insertBatch(contactsDOs);
            if (cnt>0){
                UploadFileLog uploadFileLog = uploadFileLogMapper.selectByPrimaryKey(fileId);
                uploadFileLog.setFileStatus(StatusEnum.BLOCK.getCode().toString());
                uploadFileLog.setUpdateBy("system");
                uploadFileLog.setUpdateAt(new Date());
                uploadFileLogMapper.updateByPrimaryKeySelective(uploadFileLog);
            }

        }
    }

}


