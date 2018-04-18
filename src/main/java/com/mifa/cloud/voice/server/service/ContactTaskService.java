package com.mifa.cloud.voice.server.service;

import com.mifa.cloud.voice.server.dao.CustomerTaskUserContactsDAO;
import com.mifa.cloud.voice.server.dao.CustomerUserContactsDAO;
import com.mifa.cloud.voice.server.pojo.CustomerTaskUserContactsDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import com.mifa.cloud.voice.server.utils.EncodesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: songxm
 * @date: 2018/4/18 15:50
 * @version: v1.0.0
 */
@Service
@Slf4j
public class ContactTaskService {
    @Autowired
    private CustomerTaskUserContactsDAO customerTaskUserContactsDAO;
    @Autowired
    private CustomerUserContactsDAO customerUserContactsDAO;

    public void addContancts(List<Map<String, Object>> list, String contractNo, String taskId, String salt) {
        if (CollectionUtils.isNotEmpty(list)) {
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
                customerTaskUserContactsDAO.insert(taskUserContactsDO);
            });
        }
    }
}
