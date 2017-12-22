package com.yestic.winter.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * redis存储操作
 * Created by chenyi on 2017/12/19
 */
@Component
public class RedisCache{
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private String name;

    public RedisCache() {
    }

    private String fillKey(String key) {
        return this.name + ":" + key;
    }

    public void put(String key, Object value) {
        this.put(key, value, (Long)null);
    }

    public void put(final String key, Object value, final Long expireat) {
        final byte[] vbytes = this.serialize(value);
        this.redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] fkey = RedisCache.this.redisTemplate.getStringSerializer().serialize(RedisCache.this.fillKey(key));
                if (connection.exists(fkey).booleanValue()) {
                    connection.del(new byte[][]{fkey});
                }

                connection.set(fkey, vbytes);
                if (null != expireat && expireat.longValue() > 0L) {
                    connection.expire(fkey, expireat.longValue());
                }

                return null;
            }
        });
    }

    public <T> T get(final String key, Class<T> elementType) {
        return this.redisTemplate.execute(new RedisCallback<T>() {
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keybytes = RedisCache.this.redisTemplate.getStringSerializer().serialize(RedisCache.this.fillKey(key));
                if (connection.exists(keybytes).booleanValue()) {
                    byte[] valuebytes = connection.get(keybytes);
                    ByteArrayInputStream bais = null;
                    try {
                        bais = new ByteArrayInputStream(valuebytes);
                        ObjectInputStream ois = new ObjectInputStream(bais);
                        return (T) ois.readObject();
                    } catch (Exception var3) {
                        throw new RuntimeException(var3.getMessage(), var3);
                    }
//                    T value = WinterUtils.unserialize(valuebytes);
//                    return value;
                } else {
                    return null;
                }
            }
        });
    }

    public void del(final String key) {
        this.redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keybytes = RedisCache.this.redisTemplate.getStringSerializer().serialize(RedisCache.this.fillKey(key));
                return connection.exists(keybytes).booleanValue() ? connection.del(new byte[][]{keybytes}) : null;
            }
        });
    }

    public Boolean expire(final String key, final long seconds) throws DataAccessException {
        return (Boolean)this.redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keybytes = RedisCache.this.redisTemplate.getStringSerializer().serialize(RedisCache.this.fillKey(key));
                return connection.expire(keybytes, seconds);
            }
        }, true);
    }

    public Boolean hasKey(final String key) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) {
                byte[] keybytes = RedisCache.this.redisTemplate.getStringSerializer().serialize(RedisCache.this.fillKey(key));
                return connection.exists(keybytes);
            }
        }, true);
    }

    public void putString(String key, String value) {
        this.stringRedisTemplate.opsForValue().set(this.fillKey(key), value);
    }

    public void putString(String key, String value, long expire) {
        this.stringRedisTemplate.opsForValue().set(this.fillKey(key), value, expire, TimeUnit.SECONDS);
    }

    public String getString(String key) {
        String fkey = this.fillKey(key);
        return this.stringRedisTemplate.hasKey(fkey).booleanValue() ? (String)this.stringRedisTemplate.opsForValue().get(fkey) : null;
    }

    public void delString(String key) {
        String fkey = this.fillKey(key);
        if (this.stringRedisTemplate.hasKey(fkey).booleanValue()) {
            this.stringRedisTemplate.delete(fkey);
        }

    }

    public Boolean hasKeyString(String key) {
        return this.stringRedisTemplate.hasKey(this.fillKey(key));
    }

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage(), var4);
        }
    }

    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;

        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }

}
