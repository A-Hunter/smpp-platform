package com.smpp.platform.dal;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JedisPersistenceUnit {

    private String host;
    private int port;
    private Map<String, JedisEntityManager> entities = new HashMap<String, JedisEntityManager>();

    public JedisPersistenceUnit(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public <T> T invoke(JedisAwareAction<T> action) {
        synchronized (this) {
            Jedis jedis = null;
            try {
                jedis = new Jedis(host);//new Jedis(host, port);
                return action.run(jedis);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public void hmset(final String a, final Map<String, String> map) {
        invoke(connection -> {
            connection.hmset(a, map);
            return null;
        });
    }

    public void set(final String a, final String b) {
        invoke(connection -> {connection.set(a, b);
            return null;
        });
    }

    public String get(final String a) {
        return invoke(connection -> connection.get(a));
    }

    public Map<String, String> hgetAll(final String a) {
        return invoke(connection ->connection.hgetAll(a));
    }


    public void del(final String... a) {
        invoke(connection -> {connection.del(a);
            return null;
        });
    }

    public void registerEntity(Class t) {
        entities.put(t.getSimpleName(), new JedisEntityManager(t, this));
    }

    public JedisEntityManager getEntityManager(Class c) {
        return entities.get(c.getSimpleName());
    }


    public <T> T findById(Class<T> type, long id) {
        return (T) getEntityManager(type).findById(id);
    }

    public <T> List<T> findAll(Class<T> type, ObjectFilter<T> filter) {
        return (List<T>) getEntityManager(type).findAll(filter);
    }

    public <T> T findByIndexedProperty(Class<T> type, String property, String value) {
        return (T) getEntityManager(type).findByIndexedProperty(property, value);
    }

    public void persist(Object t) {
        getEntityManager(t.getClass()).persist(t);
    }

    public void merge(Object t) {
        getEntityManager(t.getClass()).merge(t);
    }

    public void delete(Object t) {
        getEntityManager(t.getClass()).delete(t);
    }

}
