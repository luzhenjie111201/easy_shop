package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

@RequestMapping("/content/category")
@Controller
public class ContentCategroyController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	

	/**
	 * 删除节点
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public ResponseEntity<Void> deleteContenCategory(ContentCategory contentCategory) {
		
		try {
			System.out.println(contentCategory.getId());
			contentCategoryService.deleteContenCategory(contentCategory);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	
	/**
	 * 重命名节点
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ResponseEntity<ContentCategory> updateContenCategory(ContentCategory contentCategory) {
		
		try {
		
			contentCategoryService.updateSelective(contentCategory);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	/**
	 * 新增节点
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ResponseEntity<ContentCategory> saveContenCategory(ContentCategory contentCategory) {
		
		try {
			System.out.println(contentCategory);
			ContentCategory result = contentCategoryService.saveContenCategory(contentCategory);
	
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 查询树结构
	 * @param parentId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ContentCategory>> queryCategoryListByParentId(
			@RequestParam(value = "id" , defaultValue = "0") Long parentId) {
		try {
			//System.out.println(parentId);
			ContentCategory cc = new ContentCategory();
			cc.setParentId(parentId);
			List<ContentCategory> list = contentCategoryService.querByWhere(cc);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
