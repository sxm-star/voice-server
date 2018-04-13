package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.commons.dto.VoiceCategoryDto;
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
public class TemplateCategoryService extends BaseService<VoiceCategoryDO>{

    @Autowired
    private VoiceCategoryDAO voiceCategoryDAO;

    /**
     * 查询模板类目,v1版本只有
     * @param parentId
     * @return
     */
    public CommonResponse<PageDto<VoiceCategoryDO>> queryVoiceCategoryList(Integer parentId, String contractNo ,Integer pageNum,Integer pageSize) {
        VoiceCategoryDO voiceCategoryDO = new VoiceCategoryDO();
        voiceCategoryDO.setParentId(parentId);
        if (StringUtils.isNotEmpty(contractNo)) {
            voiceCategoryDO.setCreatedBy(String.valueOf(contractNo));
        }
        try {
            PageInfo<VoiceCategoryDO> pageInfo = this.queryListByPageAndOrder(voiceCategoryDO,pageNum,pageSize,"sort_order asc");
            if (pageInfo!=null&& CollectionUtils.isNotEmpty(pageInfo.getList())){
                List<VoiceCategoryDto> voiceCategoryDtos = new ArrayList<>();
                pageInfo.getList().stream().forEach(catrgoryItem->voiceCategoryDtos.add(BaseBeanUtils.convert(catrgoryItem,VoiceCategoryDto.class)));
                BaseBeanUtils.convert(pageInfo,PageDto.class).setList(voiceCategoryDtos);
                return CommonResponse.successCommonResponse(voiceCategoryDtos);
            }
        }catch (Exception e){
            log.error("查询异常:{}",e);
        }
        return CommonResponse.successCommonResponse();
    }


}
