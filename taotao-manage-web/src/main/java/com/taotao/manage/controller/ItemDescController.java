package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemCatService;
import com.taotao.manage.service.ItemDescService;

@RequestMapping("/item/desc")
@Controller
public class ItemDescController {
	//注入Sercvice
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping(value = "/{itemId}",method=RequestMethod.GET)
	public ResponseEntity<ItemDesc> queryItemCatByPage(@PathVariable("itemId") Long itemId) {
		
		try {
			//List<ItemCat> list = itemCatService.queryItemCatListByPage(page, rows);
			ItemDesc itemDesc = itemDescService.queryById(itemId);
			return ResponseEntity.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
