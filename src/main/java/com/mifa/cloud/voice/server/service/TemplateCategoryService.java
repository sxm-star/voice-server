package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.commons.dto.VoiceCategoryDto;
import com.mifa.cloud.voice.server.commons.dto.VoiceCategoryQueryDto;
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
    public PageDto<VoiceCategoryDto> queryVoiceCategoryList(VoiceCategoryQueryDto voiceCategoryQueryDto, Integer pageNum, Integer pageSize) {
        VoiceCategoryDO voiceCategoryDO = new VoiceCategoryDO();
        voiceCategoryDO.setParentId(0); //系统目前只有一级
        voiceCategoryDO.setStatus(StatusEnum.NORMAL.getCode());
        if (StringUtils.isNotEmpty(voiceCategoryQueryDto.getContanctNo())) {
            voiceCategoryDO.setCreatedBy(voiceCategoryQueryDto.getContanctNo());
        }
        if (StringUtils.isNotEmpty(voiceCategoryQueryDto.getName())) {
            voiceCategoryDO.setName(voiceCategoryQueryDto.getName());
        }
        if (StringUtils.isNotEmpty(voiceCategoryQueryDto.getBizType())) {
            voiceCategoryDO.setBizType(voiceCategoryQueryDto.getBizType());
        }
        try {
            PageInfo<VoiceCategoryDO> pageInfo = this.queryListByPageAndOrder(voiceCategoryDO, pageNum, pageSize, "sort_order asc");
            if (pageInfo != null && CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<VoiceCategoryDto> voiceCategoryDtos = new ArrayList<>();
                pageInfo.getList().stream().forEach(catrgoryItem -> voiceCategoryDtos.add(BaseBeanUtils.convert(catrgoryItem, VoiceCategoryDto.class)));
                PageDto<VoiceCategoryDto> pageResult = BaseBeanUtils.convert(pageInfo, PageDto.class);
                pageResult.setList(voiceCategoryDtos);
                return pageResult;
            }
        } catch (Exception e) {
            log.error("查询异常:{}", e);
        }
        return null;
    }

    /**
     * 新增模板类目操作
     * @param voiceCategoryQueryDto
     * @return
     */
    public Boolean insertVoiceCategory(VoiceCategoryQueryDto voiceCategoryQueryDto) {
        VoiceCategoryDO voiceCategoryDO = BaseBeanUtils.convert(voiceCategoryQueryDto, VoiceCategoryDO.class);
        voiceCategoryDO.setCreatedBy(voiceCategoryQueryDto.getContanctNo());
        voiceCategoryDO.setStatus(StatusEnum.NORMAL.getCode());
        int cnt = voiceCategoryDAO.insert(voiceCategoryDO);
        return cnt > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 模板类目修改
     * @param categoryId
     * @param voiceCategoryQueryDto
     * @return
     */
    public Boolean alterVoiceCategory(Integer categoryId , VoiceCategoryQueryDto voiceCategoryQueryDto) {
        VoiceCategoryDO preVoiceCategoryDO = this.queryById(categoryId.longValue());
        VoiceCategoryDO newVoiceCategoryDO = BaseBeanUtils.convert(voiceCategoryQueryDto, VoiceCategoryDO.class);
        newVoiceCategoryDO.setUpdatedBy(voiceCategoryQueryDto.getContanctNo());
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
