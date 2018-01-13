package com.taotao.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/page")
@Controller
public class PageController {

	/**
	 * 跳转到页面
	 */
	@RequestMapping("/{pageName}")
	public String toPage(@PathVariable("pageName") String pageName) {
		//解析视图并返回视图给用户，即index.jsp
		return pageName;
	}
}
