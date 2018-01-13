package com.taotao.manage.service;


import com.taotao.manage.pojo.Item;

public interface ItemService extends BaseService<Item>{

	Long saveSelective(Item item, String desc);


	void updateItem(Item item, String desc);
}
