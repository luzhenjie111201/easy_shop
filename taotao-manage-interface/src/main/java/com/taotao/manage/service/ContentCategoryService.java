package com.taotao.manage.service;


import com.taotao.manage.pojo.ContentCategory;

public interface ContentCategoryService extends BaseService<ContentCategory>{

	ContentCategory saveContenCategory(ContentCategory contentCategory);

	void deleteContenCategory(ContentCategory contentCategory);

}
