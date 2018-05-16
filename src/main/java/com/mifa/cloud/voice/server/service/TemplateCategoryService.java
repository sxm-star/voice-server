package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.PageDTO;
import com.mifa.cloud.voice.server.commons.dto.VoiceCategoryDTO;
import com.mifa.cloud.voice.server.commons.dto.VoiceCategoryQueryDTO;
import com.mifa.cloud.voice.server.commons.enums.StatusEnum;
import com.mifa.cloud.voice.server.dao.VoiceCategoryDAO;
import com.mifa.cloud.voice.server.pojo.VoiceCategoryDO;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: songxm
 * @date: 2018/4/13 10:27
 * @version: v1.0.0
 */
@Service
@Slf4j
public class TemplateCategoryService extends BaseService<VoiceCategoryDO> {

    @Autowired
    private VoiceCategoryDAO voiceCategoryDAO;

    /**
     * 查询模板类目
     *
     * @return
     */
    public PageDTO<VoiceCategoryDTO> queryVoiceCategoryList(VoiceCategoryQueryDTO voiceCategoryQueryDTO, Integer pageNum, Integer pageSize) {
        VoiceCategoryDO voiceCategoryDO = new VoiceCategoryDO();
        voiceCategoryDO.setParentId(0); //系统目前只有一级
        voiceCategoryDO.setStatus(StatusEnum.NORMAL.getCode());
        if (StringUtils.isNotEmpty(voiceCategoryQueryDTO.getContanctNo())) {
            voiceCategoryDO.setCreatedBy(voiceCategoryQueryDTO.getContanctNo());
        }
        if (StringUtils.isNotEmpty(voiceCategoryQueryDTO.getName())) {
            voiceCategoryDO.setName(voiceCategoryQueryDTO.getName());
        }
        if (StringUtils.isNotEmpty(voiceCategoryQueryDTO.getBizType())) {
            voiceCategoryDO.setBizType(voiceCategoryQueryDTO.getBizType());
        }
        try {
            PageInfo<VoiceCategoryDO> pageInfo = this.queryListByPageAndOrder(voiceCategoryDO, pageNum, pageSize, "sort_order asc");
            if (pageInfo != null && CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<VoiceCategoryDTO> voiceCategoryDTOs = new ArrayList<>();
                pageInfo.getList().stream().forEach(catrgoryItem -> voiceCategoryDTOs.add(BaseBeanUtils.convert(catrgoryItem, VoiceCategoryDTO.class)));
                PageDTO<VoiceCategoryDTO> pageResult = BaseBeanUtils.convert(pageInfo, PageDTO.class);
                pageResult.setList(voiceCategoryDTOs);
                return pageResult;
            }
        } catch (Exception e) {
            log.error("查询异常:{}", e);
        }
        return null;
    }

    /**
     * 新增模板类目操作
     * @param voiceCategoryQueryDTO
     * @return
     */
    public Boolean insertVoiceCategory(VoiceCategoryQueryDTO voiceCategoryQueryDTO) {
        VoiceCategoryDO voiceCategoryDO = BaseBeanUtils.convert(voiceCategoryQueryDTO, VoiceCategoryDO.class);
        voiceCategoryDO.setCreatedBy(voiceCategoryQueryDTO.getContanctNo());
        voiceCategoryDO.setStatus(StatusEnum.NORMAL.getCode());
        int cnt = voiceCategoryDAO.insert(voiceCategoryDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 模板类目修改
     * @param categoryId
     * @param voiceCategoryQueryDTO
     * @return
     */
    public Boolean alterVoiceCategory(Integer categoryId , VoiceCategoryQueryDTO voiceCategoryQueryDTO) {
        VoiceCategoryDO preVoiceCategoryDO = this.queryById(categoryId.longValue());
        VoiceCategoryDO newVoiceCategoryDO = BaseBeanUtils.convert(voiceCategoryQueryDTO, VoiceCategoryDO.class);
        newVoiceCategoryDO.setUpdatedBy(voiceCategoryQueryDTO.getContanctNo());
        newVoiceCategoryDO.setCategoryId(preVoiceCategoryDO.getCategoryId());
        int cnt = voiceCategoryDAO.updateByPrimaryKeySelective(newVoiceCategoryDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 停用
     * @param categoryId
     * @param contractNo
     * @return
     */
    public Boolean deleteVoiceCategory(Integer categoryId ,String contractNo) {
        VoiceCategoryDO preVoiceCategoryDO = this.queryById(categoryId.longValue());
        preVoiceCategoryDO.setStatus(StatusEnum.BLOCK.getCode());
        preVoiceCategoryDO.setUpdatedBy(contractNo);
        int cnt = voiceCategoryDAO.updateByPrimaryKeySelective(preVoiceCategoryDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

}
