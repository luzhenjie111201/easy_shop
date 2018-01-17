package com.taotao.common.service.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.taotao.common.service.redis.RedisService;

import redis.clients.jedis.JedisCluster;

public class RedisClusterServiceImpl implements RedisService {

	@Autowired(required=false)
	private JedisCluster jedisCluster;
	
	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value) ;
	}

	@Override
	public String setex(String key, int seconds, String value) {
		return jedisCluster.setex(key, seconds, value);
	}

	@Override
	public Long expire(String key, int seconds) {
		return expire(key, seconds);
	}

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public Long del(String key) {
		return jedisCluster.del(key);
	}

	@Override
	public Long incr(String key) {
		return jedisCluster.incr(key);
	}
}
