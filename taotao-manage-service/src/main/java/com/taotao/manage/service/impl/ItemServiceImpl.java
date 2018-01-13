package com.taotao.manage.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService{

	//注入Mapper
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescService itemDescMapper;

	@Override
	public Long saveSelective(Item item, String desc) {
		//保存商品信息
		saveSelective(item);
		
		//保存商品详情
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		itemDescMapper.saveSelective(itemDesc);
		return item.getId();
	}

	//更新商品
	@Override
	public void updateItem(Item item , String desc) {
		//更新商品信息
		updateSelective(item);
		//更新商品详情
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateSelective(itemDesc);
	}
	
	
	/*已经使用了BaseService的方法，所以注释
	 * @Override
	public List<ItemCat> queryItemCatListByPage(Integer page, Integer rows) {
		//使用分页助手
		PageHelper.startPage(page, rows);
		 List<ItemCat> list = itemCatMapper.selectAll();
		 
		 return list;
	}
*/
	
	
}
