package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

@RequestMapping("/item/interface")
@RestController
public class ItemInterfaceController {

	@Autowired
	private ItemService itemService;

	// 根据Id查询商品
	@RequestMapping(value = "/{ItemId}", method = RequestMethod.GET)
	public ResponseEntity<Item> queryById(@PathVariable("itemId") Long itemId) {
		try {
			if (itemId > 0) {
				Item item = itemService.queryById(itemId);
				return ResponseEntity.ok(item);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	// 新增商品
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item) {
		try {
			itemService.saveSelective(item);
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	//更新商品
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item) {
		try {
			itemService.updateSelective(item);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	//删除商品
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteItemByIds(@RequestParam(value = "ids",required = false)Long[] ids) {
		try {
			itemService.deleteByIds(ids);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
