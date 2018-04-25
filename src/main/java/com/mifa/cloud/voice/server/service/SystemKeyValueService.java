package com.mifa.cloud.voice.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.commons.dto.PageDto;
import com.mifa.cloud.voice.server.commons.dto.SystemKeyValueQueryAndEdit;
import com.mifa.cloud.voice.server.commons.dto.SystemKeyValueVO;
import com.mifa.cloud.voice.server.dao.SystemKeyValueMapper;
import com.mifa.cloud.voice.server.pojo.SystemKeyValue;
import com.mifa.cloud.voice.server.pojo.SystemKeyValueExample;
import com.mifa.cloud.voice.server.utils.BaseBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/12.
 */
@Service
public class SystemKeyValueService {

    @Autowired
    private SystemKeyValueMapper mapper;

    /**
     * 插入记录（只插入参数非空字段）
     */
    @Transactional(rollbackFor = Exception.class)
    public int insert(SystemKeyValue record) {
        return mapper.insert(record);
    }

    /**
     * 根据字典类型和key获得字典集合
     * */
    public List<SystemKeyValue> getKeyValueListByType(String keyValueType, String key) {
        SystemKeyValueExample example = new SystemKeyValueExample();
        SystemKeyValueExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(key)) {
            criteria.andParamKeyEqualTo(key);
        }
        criteria.andBizTypeEqualTo(keyValueType);
        return mapper.selectByExample(example);
    }

    /**
     * 根据主键id删除数据
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByPrimaryKey(Long id) {
        return mapper.deleteByPrimaryKey(id) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 分页查询字典列表
     */
    public PageDto<SystemKeyValueVO> getSystemKeyValuePageList(SystemKeyValueQueryAndEdit query, Integer page, Integer rows) {
        SystemKeyValueExample example = new SystemKeyValueExample();
        SystemKeyValueExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(query.getContractNo())) {
            criteria.andCreatedByEqualTo(query.getContractNo());
        }
        if(StringUtils.isNotEmpty(query.getParamValue())) {
            criteria.andParamKeyEqualTo(query.getParamValue());
        }
        if(StringUtils.isNotEmpty(query.getRemark())) {
            criteria.andRemarkEqualTo(query.getRemark());
        }
        // 加入分页
        PageHelper.startPage(page, rows);
        example.setOrderByClause("created_at DESC");
        PageInfo<SystemKeyValue> pageInfo = new PageInfo<>(mapper.selectByExample(example));
        List<SystemKeyValueVO> voList = new ArrayList<>();
        pageInfo.getList().forEach(item -> voList.add(BaseBeanUtils.convert(item, SystemKeyValueVO.class)));
        PageDto<SystemKeyValueVO> pageDto = BaseBeanUtils.convert(pageInfo,PageDto.class);
        pageDto.setList(voList);
        return pageDto;
    }

    /**
     * 编辑
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByPrimaryKeySelective(SystemKeyValueQueryAndEdit record) {
        SystemKeyValue systemKeyValue = BaseBeanUtils.convert(record, SystemKeyValue.class);
        return mapper.updateByPrimaryKeySelective(systemKeyValue) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }















}
