package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.ContactGroupPostDTO;
import com.mifa.cloud.voice.server.commons.dto.ContactGroupRspDTO;
import com.mifa.cloud.voice.server.commons.dto.ContactGroupSelectDTO;
import com.mifa.cloud.voice.server.commons.dto.PageDTO;
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
    @Autowired
    ContactsService contactsService;

    public Boolean addContactGroup(ContactGroupPostDTO contactGroupPostDTO) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = new CustomerTaskContactGroupDO();
        customerTaskContactGroupDO.setTaskId(BaseStringUtils.uuid());
        customerTaskContactGroupDO.setSource(contactGroupPostDTO.getSource());
        customerTaskContactGroupDO.setGroupName(contactGroupPostDTO.getGroupName());
        customerTaskContactGroupDO.setGroupCnt(0);//默认0
        customerTaskContactGroupDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        customerTaskContactGroupDO.setCreatedBy(contactGroupPostDTO.getContractNo());
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

    public ContactGroupRspDTO getContanctGroupById(Long id) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = this.queryById(id);
        if (customerTaskContactGroupDO != null) {
            return BaseBeanUtils.convert(customerTaskContactGroupDO, ContactGroupRspDTO.class);
        }
        return null;
    }

    public Boolean updateContanctGroupByIdSelective(ContactGroupRspDTO contactGroupRspDTO) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = BaseBeanUtils.convert(contactGroupRspDTO, CustomerTaskContactGroupDO.class);
        customerTaskContactGroupDO.setUpdatedAt(new Date());
        customerTaskContactGroupDO.setUpdatedBy("system");
        this.updateByIdSelective(customerTaskContactGroupDO);
        return Boolean.TRUE;
    }

    public PageDTO<ContactGroupRspDTO> queryContactGroupList(String contractNo, String groupName, Integer pageNum, Integer pageSize) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = new CustomerTaskContactGroupDO();
        customerTaskContactGroupDO.setCreatedBy(contractNo);
        customerTaskContactGroupDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        PageInfo<CustomerTaskContactGroupDO> pageInfo;
        try {
            if (StringUtils.isNotEmpty(groupName)) {
                customerTaskContactGroupDO.setGroupName(groupName);
            }
            pageInfo = this.queryListByPageAndOrder(customerTaskContactGroupDO, pageNum, pageSize, " created_at desc ");

            List<ContactGroupRspDTO> list = new ArrayList<>();
            pageInfo.getList().forEach(
                    item -> list.add(BaseBeanUtils.convert(item, ContactGroupRspDTO.class))
            );
            PageDTO<ContactGroupRspDTO> pageDTO = BaseBeanUtils.convert(pageInfo, PageDTO.class);
            pageDTO.setList(list);
            return pageDTO;
        } catch (Exception e) {
            log.error("查询异常:{}", e);
            return null;
        }
    }

    public List<ContactGroupSelectDTO> querySelectedContactGroupList(String contactNo) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = new CustomerTaskContactGroupDO();
        customerTaskContactGroupDO.setCreatedBy(contactNo);
        customerTaskContactGroupDO.setStatus(StatusEnum.NORMAL.getCode().toString());
        List<CustomerTaskContactGroupDO> list = this.queryListByWhere(customerTaskContactGroupDO);
        if (CollectionUtils.isNotEmpty(list)) {
            List<ContactGroupSelectDTO> resList = new ArrayList<>();
            list.forEach(customerTaskContactGroupDO1 -> resList.add(BaseBeanUtils.convert(customerTaskContactGroupDO1, ContactGroupSelectDTO.class)));
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

            boolean flag = contactsService.batchDeleteByContactNoAndGroupId(contactNo,id);
            return cnt > 0 && flag ? Boolean.TRUE : Boolean.FALSE;
        }
        return Boolean.FALSE;
    }
}
