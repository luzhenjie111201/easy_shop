package com.taotao.manage.service;

import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.pojo.Item;

public interface ItemService extends BaseService<Item>{

	Long saveSelective(Item item, String desc);


	void updateItem(Item item, String desc);


	DataGridResult queryItemList(String title, Integer page, Integer rows);

	/*//删除商品
	void updateStatusToDelete(String ids);*/

	//下架
	void updateStatusToInstock(String ids);

	//上架
	void updateStatusToReshelf(String ids);
}
