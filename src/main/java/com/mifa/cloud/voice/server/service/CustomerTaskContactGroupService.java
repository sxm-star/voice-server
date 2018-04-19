package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.ContactGroupRspDto;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.pojo.CustomerTaskContactGroupDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Boolean addContactGroup(String groupName, String source, String contractNo) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = new CustomerTaskContactGroupDO();
        customerTaskContactGroupDO.setTaskId(BaseStringUtils.uuid());
        customerTaskContactGroupDO.setSource(source);
        customerTaskContactGroupDO.setGroupName(groupName);
        customerTaskContactGroupDO.setCreatedBy(contractNo);
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

    public PageDto<ContactGroupRspDto> queryContactGroupList(String contractNo, String groupName, Integer pageNum, Integer pageSize) {
        CustomerTaskContactGroupDO customerTaskContactGroupDO = new CustomerTaskContactGroupDO();
        customerTaskContactGroupDO.setCreatedBy(contractNo);
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
}
