package com.taotao.manage.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {

	//根据ID查询
	public T queryById(Serializable id);
	
	//查询全部
	public List<T> queryAll();
	
	//根据条件查询
	public List<T> querByWhere(T t);
	
	//统计查询
	public long queryByCountByWhere(T t);
	
	//分页查询
	public List<T> queryByPage(Integer pageNo,Integer rows);
	
	//添加
	public void saveSelective(T t);
	
	//根据Id删除
	public void deleteById(Serializable id);
	
	//批量删除
	public void deleteByIds(Serializable[] ids);
	
	//更改
	public void updateSelective(T t);
}
