package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.service.redis.RedisService;
import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ContentCategoryService;
import com.taotao.manage.service.ContentService;
import com.taotao.manage.service.ItemCatService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService{

	@Value("${CONTENT_CATEGORY_BIG_AD_ID}")
	private Long CONTENT_CATEGORY_BIG_AD_ID;
	@Value("${TAOTAO_PORTAL_INDEX_BIG_AD_NUMBER}")
	private Integer TAOTAO_PORTAL_INDEX_BIG_AD_NUMBER;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final String CONTENT_KEY = "CONTENT_CATEGORY_BIG_AD_DATA";
	private static final int CONTENT_EXPIRED_TIME = 60*60*24;
	
	//注入Mapper
	@Autowired
	private ContentMapper contentMapper;
	
	//注入RedisService接口
	@Autowired
	private RedisService redisService;

	/**
	 * 查询内容
	 * @return 
	 */
	@Override
	public DataGridResult queryContentListByCategoryId(Long categoryId, Integer page, Integer rows) {
		//创建查询对象
		Example example = new Example(Content.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("categoryId", categoryId);
		
		//排序
		example.orderBy("updated").desc();
		//分页
		PageHelper.startPage(page, rows);
		//查询
		List<Content> list = contentMapper.selectByExample(example);
		
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
	}

	@Override
	public String queryPortalBigAdData() throws Exception {
		String resultStr = "";

		//先到Redis中查
		try {
			resultStr = redisService.get(CONTENT_KEY);
			
			if(StringUtils.isNotBlank(resultStr)) {
				return resultStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		DataGridResult dataGrigResult = queryContentListByCategoryId(CONTENT_CATEGORY_BIG_AD_ID, 1, TAOTAO_PORTAL_INDEX_BIG_AD_NUMBER);
		
		List<Content> contentList = (List<Content>) dataGrigResult.getRows();
		
		if(contentList != null && contentList.size() > 0) {
			List<Map<String,Object>> adList = new ArrayList<>();
			Map<String,Object> map = null;
			for (Content content : contentList) {
				map = new HashMap<>();
				map.put("alt", content.getTitle());
				map.put("height", 240);
				map.put("heightB", 240);
				map.put("href", content.getUrl());
				map.put("src", content.getPic());
				map.put("srcB", content.getPic2());
				map.put("width", 670);
				map.put("widthB", 550);
				
				adList.add(map);
			}
			//转为字符串
			String str = objectMapper.writeValueAsString(adList);
			
			//就算Redis不可用，也不影响在数据库查询
			try {
				redisService.setex(CONTENT_KEY, CONTENT_EXPIRED_TIME, str);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return resultStr;
	}
}
