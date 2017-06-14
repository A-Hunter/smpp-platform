package com.smpp.platform.dal;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface JedisAwareAction<T> {
    public T run(Jedis connection);
}
