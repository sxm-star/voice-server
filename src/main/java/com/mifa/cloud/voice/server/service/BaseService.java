package com.mifa.cloud.voice.server.service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mifa.cloud.voice.server.dao.base.BaseMapper;
import com.mifa.cloud.voice.server.pojo.BaseDataDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * 通用service的封装 CRUD,分页查询，排序，根据ids批量删除
 * @author 宋烜明
 * @param <T>   spring4.0泛型注入
 *
 */
public abstract class BaseService<T extends BaseDataDO> {
	   /**
	    * spring4.0 泛型注入新特性，扩展通用mapper
	    */
	    @Autowired
	    private BaseMapper<T> mapper;
	    
	    public BaseMapper<T> getMapper() {
	        return this.mapper;
	    }

	    private Class<T> clazz;

	    @SuppressWarnings("unchecked")
	    public BaseService() {
	        super();
	        Type type = this.getClass().getGenericSuperclass();
	        ParameterizedType ptype = (ParameterizedType) type;
	        this.clazz = (Class<T>) ptype.getActualTypeArguments()[0];
	    }
	/**
	 * 根据主键ID查询数据
	 * @param id
	 * @return
	 */
	public T queryById(Object id){
		return this.getMapper().selectByPrimaryKey(id);
	}
	
	/**
     * 查询所有数据
     * 
     * @return
     */
    public List<T> queryAll() {
        return this.getMapper().select(null);
    }

    /**
     * 根据条件查询
     * 
     * @param t
     * @return
     */
    public List<T> queryListByWhere(T t) {
        return this.getMapper().select(t);
    }

    /**
     * 查询数据总条数
     * 
     * @return
     */
    public Integer queryCount() {
        return this.getMapper().selectCount(null);
    }

    /**
     * 根据条件分页查询
     * 
     * @param t   查询条件
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<T> queryPageByWhere(T t, Integer page, Integer rows) {
        // 第一个参数是起始页，第二个参数是，页面显示的数据条数
        PageHelper.startPage(page, rows);
        List<T> list = this.getMapper().select(t);
        return new PageInfo<T>(list);
    }
    
    /**
     * 根据条件查询一条数据
     * 
     * @param t
     * @return
     */
    public T queryOne(T t) {
        return this.getMapper().selectOne(t);
    }

    /**
     * 保存
     * 
     * @param t
     */
    public int save(T t) {
        if (t.getCreatedAt() == null) {
            t.setCreatedAt(new Date());
            // 业务上要求，更新时间和创建时间必须一直，如果new的话，很可能不一致，造成业务上的错误
           // t.setUpdatedAt(t.getCreatedAt());
        } else {
           // t.setUpdatedAt(t.getCreatedAt());
        }
        return this.getMapper().insert(t);
    }

    /**
     * 保存(忽略空字段）
     * 
     * @param t
     */
    public int saveSelective(T t) {
        if (t.getCreatedAt() == null) {
            t.setCreatedAt(new Date());
            // 业务上要求，更新时间和创建时间必须一直，如果new的话，很可能不一致，造成业务上的错误
           // t.setUpdatedAt(t.getCreatedAt());
        } else {
           // t.setUpdatedAt(t.getCreatedAt());
        }
        return this.getMapper().insertSelective(t);
    }

    /**
     * 更新
     * 
     * @param t
     */
    public int updateById(T t) {
        t.setUpdatedAt(new Date());
        return this.getMapper().updateByPrimaryKey(t);
    }

    /**
     * 更新（忽略空字段）
     * 
     * @param t
     */
    public int updateByIdSelective(T t) {
        t.setUpdatedAt(new Date());
       return this.getMapper().updateByPrimaryKeySelective(t);
    }

    public int deleteByWhere(T t) throws Exception{
        // 加入分页

        // 声明一个example
        Example example = new Example(this.clazz);

        // 声明条件
        Criteria createCriteria = example.createCriteria();
        // 获取t的字段
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 设置为true，可以获取声明的私有字段的值
            field.setAccessible(true);
            if (field.get(t) != null&& StringUtils.isNotEmpty(field.get(t).toString())) {
                // 非空的字段的值，加入到条件中
                createCriteria.andEqualTo(field.getName(), field.get(t));
            }
        }
        return   this.getMapper().deleteByExample(example);
    }
    /**
     * 根据id删除
     * 
     * @param id
     */
    public int deleteById(Object id) {
       return this.getMapper().deleteByPrimaryKey(id);
    }

    /**
     * 根据ids批量删除
     * 
     * @param ids
     */
    public int deleteByIds(List<Object> ids) {
        // 设置条件
        Example example = new Example(clazz);
        example.createCriteria().andIn("id", ids);
        // 根据条件删除
        return this.getMapper().deleteByExample(example);
    }
    
    public int deleteByIds(Long[] ids) {
       
        return this.getMapper().deleteByIDS(ids);
    }

    /**
     * 分页查询数据，排序
     * 
     * @param t
     * @param page
     * @param rows
     * @param order
     * @return
     * @throws Exception
     */
    public PageInfo<T> queryListByPageAndOrder(T t, Integer page, Integer rows, String order)
            throws Exception {
        // 加入分页
        PageHelper.startPage(page, rows);

        // 声明一个example
        Example example = new Example(this.clazz);
        if (StringUtils.isNotBlank(order)) {
            example.setOrderByClause(order);
        }

        // 如果条件为null，直接返回
        if (t == null) {
            List<T> list = this.getMapper().selectByExample(example);
            return new PageInfo<T>(list);
        }

        // 声明条件
        Criteria createCriteria = example.createCriteria();
        // 获取t的字段
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 设置为true，可以获取声明的私有字段的值
            field.setAccessible(true);
            if (field.get(t) != null&& StringUtils.isNotEmpty(field.get(t).toString())) {
                // 非空的字段的值，加入到条件中
                createCriteria.andEqualTo(field.getName(), field.get(t));
            }
        }

        List<T> list = this.getMapper().selectByExample(example);
        return new PageInfo<T>(list);
    }

    public List<T> queryListAndCreateAtGreaterThan(T t) throws Exception{
        // 如果条件为null，直接返回
        if (t == null) {
            return null;
        }else {
            // 声明一个example
            Example example = new Example(this.clazz);
            // 声明条件
            Criteria createCriteria = example.createCriteria();
            // 获取t的字段
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                // 设置为true，可以获取声明的私有字段的值
                field.setAccessible(true);
                if (field.get(t) != null&& StringUtils.isNotEmpty(field.get(t).toString())) {
                    if (field.getName().equalsIgnoreCase("createAt")){
                        createCriteria.andGreaterThan(field.getName(),field.get(t));
                    }else {
                        // 非空的字段的值，加入到条件中
                        createCriteria.andEqualTo(field.getName(), field.get(t));
                    }
                }
            }

            List<T> list = this.getMapper().selectByExample(example);
            return list;
        }

    }

    public PageInfo<T> queryListByPageAndOrderLike(T t, Integer page, Integer rows, String order)
            throws Exception {
        // 加入分页
        PageHelper.startPage(page, rows);

        // 声明一个example
        Example example = new Example(this.clazz);
        if (StringUtils.isNotBlank(order)) {
            example.setOrderByClause(order);
        }

        // 如果条件为null，直接返回
        if (t == null) {
            List<T> list = this.getMapper().selectByExample(example);
            return new PageInfo<T>(list);
        }

        // 声明条件
        Criteria createCriteria = example.createCriteria();
        // 获取t的字段
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 设置为true，可以获取声明的私有字段的值
            field.setAccessible(true);
            if (field.get(t) != null) {
                // 非空的字段的值，加入到条件中
                createCriteria.andLike(field.getName(), field.get(t).toString());
            }
        }

        List<T> list = this.getMapper().selectByExample(example);
        return new PageInfo<T>(list);
    }
}
