package com.taotao.manage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService{

	//注入Mapper
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	
	//已经使用了BaseService的方法，所以注释
	/* @Override
	public List<ItemCat> queryItemCatListByPage(Integer page, Integer rows) {
		//使用分页助手
		PageHelper.startPage(page, rows);
		 List<ItemCat> list = itemCatMapper.selectAll();
		 
		 return list;
	}*/

	
	
}
