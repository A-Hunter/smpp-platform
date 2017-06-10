package com.smpp.platform.dal;

import redis.clients.jedis.Jedis;

public interface JedisAwareAction<T> {
 public T run(Jedis connection);
}
