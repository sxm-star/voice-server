package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.ContactGroupPostDto;
import com.mifa.cloud.voice.server.commons.dto.ContactGroupRspDto;
import com.mifa.cloud.voice.server.commons.dto.ContactGroupSelectDto;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.dao.CustomerTaskContactGroupDAO;
import com.mifa.cloud.voice.server.pojo.CustomerTaskContactGroupDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author: songxm
 * @date: 2018/4/19 11:32
 * @version: v1.0.0
 */
@Service
@Slf4j
public class CustomerTaskContactGroupService extends BaseService<CustomerTaskContactGroupDO> {
    @Autowired
    CustomerTaskContactGroupDAO groupDAO;

    public Boolean addContactGroup(ContactGroupPostDto contactGroupPostDto) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = new CustomerTaskContactGroupDO();
        customerTaskContactGroupDO.setTaskId(BaseStringUtils.uuid());
        customerTaskContactGroupDO.setSource(contactGroupPostDto.getSource());
        customerTaskContactGroupDO.setGroupName(contactGroupPostDto.getGroupName());
        customerTaskContactGroupDO.setGroupCnt(0);//默认0
        customerTaskContactGroupDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        customerTaskContactGroupDO.setCreatedBy(contactGroupPostDto.getContractNo());
        customerTaskContactGroupDO.setCreatedAt(new Date());
        this.save(customerTaskContactGroupDO);
        return Boolean.TRUE;
    }

    public Boolean updateContactGroup(String groupName, String source, String contractNo, Long id) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = this.queryById(id);
        if (StringUtils.isNotEmpty(groupName)) {
            customerTaskContactGroupDO.setGroupName(groupName);
        }
        if (StringUtils.isNotEmpty(source)) {
            customerTaskContactGroupDO.setSource(source);
            customerTaskContactGroupDO.setUpdatedBy(contractNo);
            customerTaskContactGroupDO.setUpdatedAt(new Date());
        }
        this.updateByIdSelective(customerTaskContactGroupDO);
        return Boolean.TRUE;
    }

    public ContactGroupRspDto getContanctGroupById(Long id) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = this.queryById(id);
        if (customerTaskContactGroupDO != null) {
            return BaseBeanUtils.convert(customerTaskContactGroupDO, ContactGroupRspDto.class);
        }
        return null;
    }

    public Boolean updateContanctGroupByIdSelective(ContactGroupRspDto contactGroupRspDto) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = BaseBeanUtils.convert(contactGroupRspDto, CustomerTaskContactGroupDO.class);
        customerTaskContactGroupDO.setUpdatedAt(new Date());
        customerTaskContactGroupDO.setUpdatedBy("system");
        this.updateByIdSelective(customerTaskContactGroupDO);
        return Boolean.TRUE;
    }

    public PageDto<ContactGroupRspDto> queryContactGroupList(String contractNo, String groupName, Integer pageNum, Integer pageSize) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = new CustomerTaskContactGroupDO();
        customerTaskContactGroupDO.setCreatedBy(contractNo);
        customerTaskContactGroupDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        PageInfo<CustomerTaskContactGroupDO> pageInfo;
        try {
            if (StringUtils.isNotEmpty(groupName)) {
                customerTaskContactGroupDO.setGroupName(groupName);
            }
            pageInfo = this.queryListByPageAndOrder(customerTaskContactGroupDO, pageNum, pageSize, " created_at desc ");

            List<ContactGroupRspDto> list = new ArrayList<>();
            pageInfo.getList().forEach(
                    item -> list.add(BaseBeanUtils.convert(item, ContactGroupRspDto.class))
            );
            PageDto<ContactGroupRspDto> pageDto = BaseBeanUtils.convert(pageInfo, PageDto.class);
            pageDto.setList(list);
            return pageDto;
        } catch (Exception e) {
            log.error("查询异常:{}", e);
            return null;
        }
    }

    public List<ContactGroupSelectDto> querySelectedContactGroupList(String contactNo) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = new CustomerTaskContactGroupDO();
        customerTaskContactGroupDO.setCreatedBy(contactNo);
        customerTaskContactGroupDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        List<CustomerTaskContactGroupDO> list = this.queryListByWhere(customerTaskContactGroupDO);
        if (CollectionUtils.isNotEmpty(list)) {
            List<ContactGroupSelectDto> resList = new ArrayList<>();
            list.forEach(customerTaskContactGroupDO1 -> resList.add(BaseBeanUtils.convert(customerTaskContactGroupDO1, ContactGroupSelectDto.class)));
            return resList;
        }
        return Collections.EMPTY_LIST;
    }

    public Boolean deleteByContactNoAndId(String contactNo, Long id) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = new CustomerTaskContactGroupDO();
        customerTaskContactGroupDO.setCreatedBy(contactNo);
        customerTaskContactGroupDO.setId(Long.parseLong(String.valueOf(id)));
        if (this.queryOne(customerTaskContactGroupDO) != null) {
            customerTaskContactGroupDO.setStatus(StatusEnum.BLOCK.getCode().toString());
            int cnt = this.updateByIdSelective(customerTaskContactGroupDO);
            return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        return Boolean.FALSE;
    }
}
