package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

@RequestMapping("/item")
@Controller
public class ItemController {
	// 注入Sercvice
	@Autowired
	private ItemService itemService;

	/*// 删除商品
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<Void> updateStatusToDelete(@RequestParam(value = "ids") String ids) {
		try {
			itemService.updateStatusToDelete(ids);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}*/
	
	// 下架商品
	@RequestMapping(value = "/instock", method = RequestMethod.POST)
	public ResponseEntity<Void> updateStatusToInstock(@RequestParam(value = "ids") String ids) {
		try {
			itemService.updateStatusToInstock(ids);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}
	
	
	// 上架商品
	@RequestMapping(value = "/reshelf", method = RequestMethod.POST)
	public ResponseEntity<Void> updateStatusToReshelf(@RequestParam(value = "ids") String ids) {
		try {
			System.out.println(ids);
			itemService.updateStatusToReshelf(ids);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}
	

	// 显示商品列表
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<DataGridResult> queryItemList(@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "30") Integer rows) {
		try {
			DataGridResult dataGridResult = itemService.queryItemList(title, page, rows);
			return ResponseEntity.ok(dataGridResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}

	// 添加商品
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item, @RequestParam(value = "desc", required = false) String desc) {
		try {

			itemService.saveSelective(item, desc);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// 更新商品
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateItem(Item item, @RequestParam(value = "desc", required = false) String desc) {
		try {

			itemService.updateItem(item, desc);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
