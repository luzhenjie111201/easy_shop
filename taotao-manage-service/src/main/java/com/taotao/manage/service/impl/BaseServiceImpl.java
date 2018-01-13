package com.taotao.manage.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.taotao.manage.pojo.BasePojo;
import com.taotao.manage.service.BaseService;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

public class BaseServiceImpl<T extends BasePojo> implements BaseService<T> {
	
	@Autowired
	private Mapper<T> mapper;

	private Class<T> clazz;
	

	public BaseServiceImpl() {	
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

	/**
	 * 根据ID查询
	 */
	@Override
	public T queryById(Serializable id) {
		return mapper.selectByPrimaryKey(id);
	}

	/**
	 * 查询全部
	 */
	@Override
	public List<T> queryAll() {
		return mapper.selectAll();
	}
	
	/**
	 * 根据条件查询
	 */
	@Override
	public List<T> querByWhere(T t) {
		return mapper.select(t);
	}

	/**
	 * 统计查询
	 */
	@Override
	public long queryByCountByWhere(T t) {
		return mapper.selectCount(t);
	}
	
	/**
	 * 分页查询
	 */
	@Override
	public List<T> queryByPage(Integer pageNo, Integer rows) {
		PageHelper.startPage(pageNo, rows);
		
		return mapper.select(null);
	}

	/**
	 * 添加
	 */
	@Override
	public void saveSelective(T t) {
		
		if(t.getCreated() == null) {
			t.setCreated(new Date());
			t.setUpdated(t.getCreated());
		}else if(t.getUpdated() == null) {
			t.setUpdated(new Date());
		} 
		
		mapper.insertSelective(t);
	}

	/**
	 * 根据ID删除
	 */
	@Override
	public void deleteById(Serializable id) {
		mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void deleteByIds(Serializable[] ids) {
		Example example = new Example(this.clazz);
		Criteria criteria = example.createCriteria();
		criteria.andIn("ids",Arrays.asList(ids));
		
		mapper.deleteByPrimaryKey(ids);
	}

	/**
	 * 更新
	 */
	@Override
	public void updateSelective(T t) {
		if(t.getUpdated() == null) {
			t.setUpdated(new Date());
		}
		mapper.updateByPrimaryKeySelective(t);
	}
}
