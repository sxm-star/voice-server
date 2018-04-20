package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.*;
import com.mifa.cloud.voice.server.commons.enums.SexEnum;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.component.properties.AppProperties;
import com.mifa.cloud.voice.server.dao.CustomerTaskContactGroupDAO;
import com.mifa.cloud.voice.server.dao.CustomerTaskUserContactsDAO;
import com.mifa.cloud.voice.server.dao.UploadFileLogMapper;
import com.mifa.cloud.voice.server.pojo.CustomerTaskContactGroupDO;
import com.mifa.cloud.voice.server.pojo.CustomerTaskUserContactsDO;
import com.mifa.cloud.voice.server.pojo.CustomerTaskUserContactsDOExample;
import com.mifa.cloud.voice.server.pojo.UploadFileLog;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
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
    @Autowired
    AppProperties appProperties;
    @Autowired
    CustomerTaskContactGroupDAO taskContactGroupDAO;
    /**
     * 查询号码
     *
     * @return
     */
    public PageDto<ContactRspDto> queryContactList(ContactQueryDto contactQueryDto, Integer pageNum, Integer pageSize) {
        CustomerTaskUserContactsDO contactDo = BaseBeanUtils.convert(contactQueryDto, CustomerTaskUserContactsDO.class);
            CustomerTaskUserContactsDOExample example = new CustomerTaskUserContactsDOExample();
            CustomerTaskUserContactsDOExample.Criteria criteria = example.createCriteria();
            criteria.andContractNoEqualTo(contactQueryDto.getContractNo());
            criteria.andTaskIdEqualTo(contactQueryDto.getTaskId());
            if (StringUtils.isNotEmpty(contactQueryDto.getUserName())) {
                criteria.andUserNameEqualTo(contactQueryDto.getUserName());
            }
            if (StringUtils.isNotEmpty(contactQueryDto.getUserPhone())) {
                criteria.andUserPhoneEqualTo(EncodesUtils.selfEncrypt(contactQueryDto.getUserPhone(),appProperties.getSalt()));
            }
            if (StringUtils.isNotEmpty(contactQueryDto.getOrgName())) {
                criteria.andOrgNameEqualTo(contactQueryDto.getOrgName());
            }
            example.setOrderByClause("created_at desc");
            PageHelper.startPage(pageNum, pageSize);
            List<CustomerTaskUserContactsDO> contactListDOs = contactsDAO.selectByExample(example);
            PageInfo<CustomerTaskUserContactsDO> pageInfo = new PageInfo<>(contactListDOs);
            if (pageInfo != null && CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<ContactRspDto> contactDtos = new ArrayList<>();

                pageInfo.getList().stream().forEach(contact -> {
                    ContactRspDto contactDto = BaseBeanUtils.convert(contact, ContactRspDto.class);
                    contactDto.setUserSex(SexEnum.valueOf(contactDto.getUserSex()).getDesc());
                    try {
                        contactDto.setUserPhone(EncodesUtils.selfDecrypt(contactDto.getUserPhone(),appProperties.getSalt()));
                    } catch (Exception e) {
                        log.info("查询敏感数据解密失败:{}", e);
                    }
                    contactDtos.add(contactDto);
                });
                PageDto<ContactRspDto> pageResult = BaseBeanUtils.convert(pageInfo, PageDto.class);
                pageResult.setList(contactDtos);

                return pageResult;
            }

        return null;
    }

    /**
     * 新增号码
     *
     * @return
     */
    public Boolean insertContact(ContactDto contactDto) {
        CustomerTaskContactGroupDO taskContactGroupDO =  taskContactGroupDAO.selectByPrimaryKey(contactDto.getGroupId());
        CustomerTaskUserContactsDO contactDo = BaseBeanUtils.convert(contactDto, CustomerTaskUserContactsDO.class);
        contactDo.setTaskId(taskContactGroupDO.getTaskId());
        contactDo.setCreatedBy(contactDto.getContractNo());
        contactDo.setUserPhone(EncodesUtils.selfEncrypt(contactDto.getUserPhone(),appProperties.getSalt()));
        contactDo.setSalt(appProperties.getSalt());
        int cnt = contactsDAO.insert(contactDo);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 号码修改
     *
     * @return
     */
    public Boolean alterContact(ContactAlterReqDto contactQueryDto) {
        CustomerTaskUserContactsDO pre_ContactsDo = contactsDAO.selectByPrimaryKey(contactQueryDto.getId());
        BaseBeanUtils.copyNoneNullProperties(contactQueryDto, pre_ContactsDo);
        pre_ContactsDo.setUpdatedBy(contactQueryDto.getContractNo());
        try {
            pre_ContactsDo.setUserPhone(EncodesUtils.selfEncrypt(contactQueryDto.getUserPhone(),appProperties.getSalt()));
        }catch (Exception e){
            log.error("数据修改 加密失败:{}",e);
        }
        int cnt = contactsDAO.updateByPrimaryKeySelective(pre_ContactsDo);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean deleteByContactNoAndId(String contactNo,Long id){
        CustomerTaskUserContactsDO contactsDO =  contactsDAO.selectByPrimaryKey(id);
        if (contactsDO.getCreatedBy() != null && contactsDO.getCreatedBy().equals(contactNo)) {
            contactsDO.setStatus(StatusEnum.BLOCK.getCode().toString());
            int cnt = contactsDAO.updateByPrimaryKeySelective(contactsDO);
            return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    /**
     * 解析号码入库
     *
     * @param list
     * @param contractNo
     * @param taskId
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addContancts(List<Map<String, Object>> list, String contractNo, String taskId, Long fileId) {
        log.info("list size:{},contractNo:{},taskId:{},salt:{}", list.size(), contractNo, taskId,appProperties.getSalt());
        if (CollectionUtils.isNotEmpty(list)) {
            List<CustomerTaskUserContactsDO> contactsDOs = new ArrayList<>();
            list.forEach(map -> {
                CustomerTaskUserContactsDO taskUserContactsDO = BaseBeanUtils.convert(map, CustomerTaskUserContactsDO.class);
                taskUserContactsDO.setTaskId(taskId);
                taskUserContactsDO.setContractNo(contractNo);
                taskUserContactsDO.setSalt(appProperties.getSalt());
                String phone = taskUserContactsDO.getUserPhone();
                try {
                    taskUserContactsDO.setUserPhone(EncodesUtils.selfEncrypt(phone,appProperties.getSalt()));
                } catch (Exception e) {
                    log.info("手机号:{}加密失败:{}", phone, e);
                }
                taskUserContactsDO.setCreatedAt(new Date());
                taskUserContactsDO.setCreatedBy(contractNo);
                contactsDOs.add(taskUserContactsDO);
                log.info("批量保存 size: {}", contactsDOs.size());
            });
            int cnt = contactsDAO.insertBatch(contactsDOs);
            if (cnt > 0) {
                UploadFileLog uploadFileLog = uploadFileLogMapper.selectByPrimaryKey(fileId);
                uploadFileLog.setFileStatus(StatusEnum.BLOCK.getCode().toString());
                uploadFileLog.setUpdateBy("system");
                uploadFileLog.setUpdateAt(new Date());
                uploadFileLogMapper.updateByPrimaryKeySelective(uploadFileLog);
                return Boolean.TRUE;
            }

        }
        return Boolean.FALSE;
    }

}


