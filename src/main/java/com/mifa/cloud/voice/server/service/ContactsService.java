package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.ContactDto;
import com.mifa.cloud.voice.server.commons.dto.ContactQueryDto;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.dao.CustomerTaskUserContactsDAO;
import com.mifa.cloud.voice.server.pojo.CustomerTaskUserContactsDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: songxm
 * @date: 2018/4/12 10:13
 * @version: v1.0.0
 */
@Service
@Slf4j
public class ContactsService extends BaseService<CustomerTaskUserContactsDO>{
    @Autowired
    CustomerTaskUserContactsDAO contactsDAO;
    /**
     * 查询号码
     *
     * @return
     */
    public PageDto<ContactDto> queryContactList(ContactQueryDto contactQueryDto, Integer pageNum, Integer pageSize) {
        CustomerTaskUserContactsDO contactDo = BaseBeanUtils.convert(contactQueryDto,CustomerTaskUserContactsDO.class);

        try {
            PageInfo<CustomerTaskUserContactsDO> pageInfo = this.queryListByPageAndOrder(contactDo, pageNum, pageSize, "created_at desc");
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
    public Boolean alterContact(String taskId , ContactQueryDto contactQueryDto) {
        CustomerTaskUserContactsDO pre_ContactsDo = this.queryById(taskId);
        BaseBeanUtils.copyNoneNullProperties(contactQueryDto,pre_ContactsDo);
        pre_ContactsDo.setUpdatedBy(contactQueryDto.getContractNo());
        int cnt = contactsDAO.updateByPrimaryKeySelective(pre_ContactsDo);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }



}


