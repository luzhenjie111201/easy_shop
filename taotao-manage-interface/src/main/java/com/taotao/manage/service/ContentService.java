package com.taotao.manage.service;


import java.util.List;
import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.pojo.Content;

public interface ContentService extends BaseService<Content>{

	DataGridResult queryContentListByCategoryId(Long categoryId, Integer page, Integer rows);

	String queryPortalBigAdData() throws Exception;

}
