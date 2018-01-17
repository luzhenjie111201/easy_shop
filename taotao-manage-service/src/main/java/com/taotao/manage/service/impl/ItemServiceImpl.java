package com.taotao.manage.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

	// 注入Mapper
	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDescService itemDescMapper;

	@Override
	public Long saveSelective(Item item, String desc) {
		// 保存商品信息
		saveSelective(item);

		// 保存商品详情
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		itemDescMapper.saveSelective(itemDesc);
		return item.getId();
	}

	// 更新商品
	@Override
	public void updateItem(Item item, String desc) {
		// 更新商品信息
		updateSelective(item);
		// 更新商品详情
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateSelective(itemDesc);
	}

	// 查询列表
	@Override
	public DataGridResult queryItemList(String title, Integer page, Integer rows) {
		// 设置查询条件
		Example example = new Example(Item.class);
		// 解码
		try {
			if (StringUtils.isNotBlank(title)) {
				Criteria criteria = example.createCriteria();
				title = URLDecoder.decode(title, "utf-8");
				criteria.andLike("title", "%" + title + "%");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 设置排序
		example.orderBy("updated").desc();

		// 设置分页
		PageHelper.startPage(page, rows);

		// 查询
		List<Item> list = itemMapper.selectByExample(example);
		// 返回对应信息
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
	}


	//下架商品
	@Override
	public void updateStatusToInstock(String ids) {
		String[] string = ids.split(",");
		if(string.length > 1) {
			for (String id : string) {
				Item item = itemMapper.selectByPrimaryKey(Long.parseLong(id));
				item.setStatus(2);
				itemMapper.updateByPrimaryKeySelective(item);
			}
		}else {
			Item item = itemMapper.selectByPrimaryKey(Long.parseLong(ids));
			item.setStatus(2);
			itemMapper.updateByPrimaryKeySelective(item);
		}
		
	}

	//上架商品
	@Override
	public void updateStatusToReshelf(String ids) {
		String[] string = ids.split(",");
		if(string.length > 1) {
			for (String id : string) {
				Item item = itemMapper.selectByPrimaryKey(Long.parseLong(id));
				item.setStatus(1);
				itemMapper.updateByPrimaryKeySelective(item);
			}
		}else {
			Item item = itemMapper.selectByPrimaryKey(Long.parseLong(ids));
			item.setStatus(1);
			itemMapper.updateByPrimaryKeySelective(item);
		}
	}

	
	//删除商品
/*	@Override
	public void updateStatusToDelete(String ids) {
		
		String[] string = ids.split(",");
		if(string.length > 1) {
			for(String id:string) {	
				itemMapper.deleteByPrimaryKey(Long.parseLong(id));
			}
		}else{
			itemMapper.deleteByPrimaryKey(Long.parseLong(ids));
		}
	}*/
	
	/*
	 * 已经使用了BaseService的方法，所以注释
	 * 
	 * @Override public List<ItemCat> queryItemCatListByPage(Integer page,
	 * Integer rows) { //使用分页助手 PageHelper.startPage(page, rows); List<ItemCat>
	 * list = itemCatMapper.selectAll();
	 * 
	 * return list; }
	 */

}
