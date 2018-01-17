package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ContentCategoryService;
import com.taotao.manage.service.ItemCatService;

@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService{

	//注入Mapper
	@Autowired
	private ContentCategoryMapper contentCategoryMapper;

	/**
	 * 添加内容分类
	 * @return 
	 * 
	 */
	@Override
	public ContentCategory saveContenCategory(ContentCategory contentCategory) {
		//将新增的节点保存
		contentCategory.setIsParent(false);
		contentCategory.setStatus(1);
		contentCategory.setSortOrder(100);
		
		saveSelective(contentCategory);
		
		//更新该类目的父类目是父节点
		ContentCategory parent = new ContentCategory();
		parent.setId(contentCategory.getParentId());
		parent.setIsParent(true);
		updateSelective(parent);
		
		return contentCategory;
	}

	/**
	 * 删除节点
	 */
	@Override
	public void deleteContenCategory(ContentCategory contentCategory) {
		List<Long> ids = new ArrayList<Long>();
		//获取父节点Id
		ids.add(contentCategory.getId());
		
		//获取子孙节点Id
		getCategoryIds(contentCategory.getId(), ids);
		//删除所有类目
		deleteByIds(ids.toArray(new Long[]{}));
		
		//更新父类目状态，如果父类目还有子节点，则不改变。如果没有，则修改状态
		ContentCategory param = new ContentCategory();
		param.setParentId(contentCategory.getParentId());
		long count = queryByCountByWhere(param);
		if(count == 0) {
			ContentCategory parent = new ContentCategory();
			parent.setId(contentCategory.getParentId());
			parent.setIsParent(false);
			updateSelective(parent);
		}	
	}

	/**
	 * 获取子孙节点Id
	 * @param ids
	 * @param id
	 */
	private void getCategoryIds(Long id , List<Long> ids) {
		ContentCategory category = new ContentCategory();
		category.setParentId(id);
		List<ContentCategory> list = querByWhere(category);
		if(list != null && list.size() > 0){
			for (ContentCategory cc : list) {
				ids.add(cc.getId());
				getCategoryIds( cc.getId(),ids);
			}
		}
	}
}
