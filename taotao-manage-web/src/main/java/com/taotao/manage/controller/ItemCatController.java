package com.taotao.manage.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@RequestMapping("/item/cat")
@Controller
public class ItemCatController {
	//注入Sercvice
	private ItemCatService itemCatService;
	
	@RequestMapping(value = "/query/{pageNo}",method=RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatByPage(@PathVariable("pageNo") Integer page , 
			@RequestParam(value = "rows",defaultValue="20") Integer rows) {
		
		try {
			List<ItemCat> list = itemCatService.queryItemCatListByPage(page, rows);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
