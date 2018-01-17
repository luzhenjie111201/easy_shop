package com.taotao.common.service.redis;

public interface RedisService {

	//添加
	public String set(String key , String value);
	
	//添加并设置过期时间
	public String setex(String key , int seconds , String value);
	
	//设置key过期时间
	public Long expire(String key , int seconds);
	
	//获得
	public String get(String key);
	
	//删除
	public Long del(String key);
	
	//增长
	public Long incr(String key);
}
