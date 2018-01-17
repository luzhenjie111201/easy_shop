package com.taotao.common.service.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.common.service.redis.RedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPoolServiceImpl implements RedisService {
	@Autowired(required=false)
	private JedisPool jedisPool;

	@Override
	public String set(String key, String value) {
		Jedis jedis = null;		
		try{
			jedis = jedisPool.getResource();
			return set(key,value);
		} finally {
			jedis.close();
		}	
	}

	@Override
	public String setex(String key, int seconds, String value) {
		Jedis jedis = null;		
		try{
			jedis = jedisPool.getResource();
			return setex(key,seconds,value);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = null;		
		try{
			jedis = jedisPool.getResource();
			return expire(key,seconds);
		} finally {
			jedis.close();
		}
	}

	@Override
	public String get(String key) {
		Jedis jedis = null;		
		try{
			jedis = jedisPool.getResource();
			return get(key);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long del(String key) {
		Jedis jedis = null;		
		try{
			jedis = jedisPool.getResource();
			return del(key);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long incr(String key) {
		Jedis jedis = null;		
		try{
			jedis = jedisPool.getResource();
			return incr(key);
		} finally {
			jedis.close();
		}
	}

}
