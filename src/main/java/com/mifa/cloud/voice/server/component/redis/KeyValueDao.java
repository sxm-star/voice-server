package com.mifa.cloud.voice.server.component.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.nio.charset.Charset;
import java.util.*;
import static com.mifa.cloud.voice.server.component.redis.ObjectByteUtil.*;

@Component
public class KeyValueDao {

    @Autowired(required = false)
    protected ShardedJedisPool shardedJedisPool;
    public static final Charset CHARSET = Charset.forName("UTF-8");

    private KeyValueDao() {
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }


    public KeyValueDao(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    //对所有类型都有效的操作

    /**
     * 删除集合或者元素（直接让底层数据过期）
     *
     * @param key
     */
    public Long remove(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.del(key);
        }
    }

    /**
     * 使对象或集合过期无效
     *
     * @param key
     */
    public Long expire(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.expire(key.getBytes(CHARSET), 0);
        }
    }

    public Long expire(String key, int seconds) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.expire(key.getBytes(CHARSET), seconds);
        }
    }

    public Long expireAt(String key, long unixTime) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.expire(key.getBytes(CHARSET), 0);
        }
    }

    public Long ttl(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.ttl(key.getBytes(CHARSET));
        }
    }

    public Object get(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return bytesToObject(jedis.get(key.getBytes(CHARSET)));
        }
    }

    public Object getSet(String key, Object value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return bytesToObject(jedis.getSet(key.getBytes(CHARSET), objectToBytes(value)));
        }
    }

    public Long incr(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.incr(key);
        }
    }

    public Long decr(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.decr(key);
        }
    }

    public boolean exists(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.exists(key);
        }
    }

    public void set(String key, Object obj) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.set(key.getBytes(CHARSET), objectToBytes(obj));
        }
    }

    public void set(String key, Object obj, int seconds) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.setex(key.getBytes(CHARSET), seconds, objectToBytes(obj));
        }
    }

    //Sets集合操作

    /**
     * 向集合中添加值
     *
     * @param key
     * @param obj
     */
    public void sadd(String key, Object obj) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.sadd(key.getBytes(CHARSET), objectToBytes(obj));
        }
    }

    /**
     * 从集合中随机提取对象，并在集合中删除该对象
     *
     * @param key
     * @return
     */
    public Object spop(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return bytesToObject(jedis.spop(key.getBytes(CHARSET)));
        }
    }

    /**
     * 返回集合数大小
     *
     * @param key
     * @return
     */
    public Long scard(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.scard(key.getBytes(CHARSET));
        }
    }

    //hashes操作

    /**
     * 添加值到指定的hash表中
     *
     * @param key
     * @param field
     * @param value
     */
    public void put(String key, String field, Object value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.hset(key.getBytes(CHARSET), field.getBytes(CHARSET), objectToBytes(value));
        }
    }

    /**
     * 从hash表中得到field对应的值
     *
     * @param key
     * @param field
     * @return
     */
    public Object get(String key, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return bytesToObject(jedis.hget(key.getBytes(CHARSET), field.getBytes(CHARSET)));
        }
    }

    /**
     * 一次从hash表中读取多个值
     *
     * @param key
     * @param fields
     * @return
     */
    public List<Object> getForKey(String key, String... fields) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            List<byte[]> fieldsByte = new ArrayList<byte[]>();
            for (String field : fields) {
                fieldsByte.add(field.getBytes(CHARSET));
            }
            List<byte[]> lists = jedis.hmget(key.getBytes(CHARSET), (byte[][]) fieldsByte.toArray());
            List<Object> objList = new ArrayList<Object>();
            for (byte[] bytes : lists) {
                objList.add(bytesToObject(bytes));
            }
            return objList;
        }
    }

    /**
     * 从hash表中移除filed指定的值
     *
     * @param key
     * @param field
     */
    public Long remove(String key, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.hdel(key.getBytes(CHARSET), field.getBytes(CHARSET));
        }
    }

    /**
     * hash表中对应的值是一个累加操作值，累加值为指定的value
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hincrBy(String key, String field, long value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.hincrBy(key, field, value);
        }
    }

    public Long hgetLong(String key, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            long num = 0;
            String numStinrg = jedis.hget(key, field);
            if (numStinrg != null) {
                num = Long.valueOf(numStinrg);
            }
            return num;
        }
    }

    /**
     * 得到hash表集合的大小
     *
     * @param key
     * @return
     */
    public Long hlen(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.hlen(key.getBytes(CHARSET));
        }
    }

    public Map<String, String> getMap(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.hgetAll(key);
        }
    }

    public Map<byte[], byte[]> getMapByte(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.hgetAll(key.getBytes(CHARSET));
        }
    }

    public void hset(String key, String field, String value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.hset(key, field, value);
        }
    }

    public void hset(String key, String field, byte[] value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.hset(key.getBytes(CHARSET), field.getBytes(CHARSET), value);
        }
    }

    public void hdel(String key, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.hdel(key, field);
        }
    }

    public String hget(String key, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.hget(key, field);
        }
    }

    public Object hgetb(String key, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return bytesToObject(jedis.hget(key.getBytes(CHARSET), field.getBytes(CHARSET)));
        }
    }

    public long hsize(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.hlen(key);
        }
    }


    //排序Set操作（Sorted Sets）

    /**
     * 添加元素到指定集合中
     *
     * @param key
     * @param score
     * @param field
     * @return
     */
    public Long zadd(String key, double score, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.zadd(key, score, field);
        }
    }

    /**
     * 增加指定集合中指定元素的分值
     *
     * @param key
     * @param score
     * @param field
     * @return
     */
    public Double zincrBy(String key, double score, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.zincrby(key, score, field);
        }
    }

    /**
     * 得到集合中指定元素的分值
     *
     * @param key
     * @param field
     * @return
     */
    public Double zscore(String key, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.zscore(key, field);
        }
    }

    /**
     * 删除集合中指定的元素
     *
     * @param key
     * @param field
     * @return
     */
    public Long zrem(String key, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.zrem(key, field);
        }
    }

    /**
     * 得到key中set的集合数
     *
     * @param key
     * @return
     */
    public Long zcard(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.zcard(key);
        }
    }

    /**
     * 得到元素在的集合中的正向位置
     *
     * @param key
     * @return
     */
    public Long zrank(String key, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.zrank(key, field);
        }
    }

    /**
     * 得到元素在的集合中的反向位置
     *
     * @param key
     * @param field
     * @return
     */
    public Long zrevrank(String key, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.zrevrank(key, field);
        }
    }

    /**
     * 得到反向排序列表
     *
     * @param key
     * @return
     */
    public Set<String> zrevrangeByScore(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.zrevrangeByScore(key, Double.MAX_VALUE, Double.MIN_VALUE);
        }
    }

    /**
     * 得到正向排序列表
     *
     * @param key
     * @return
     */
    public Set<String> zrangeByScore(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.zrangeByScore(key, Double.MAX_VALUE, Double.MIN_VALUE);
        }
    }

    public void destroy() {
        shardedJedisPool.destroy();
    }

    public Collection<Jedis> getAllShards() {
        return shardedJedisPool.getResource().getAllShards();
    }

}
