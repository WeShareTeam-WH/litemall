package org.linlinjava.litemall.core.redis;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/9/26.
 */
public interface IRedisCommonService {

    public Boolean checkRedisConnection();
    //object
    public Boolean set(String key, String value);
    public Boolean set(String key, byte[] value);
    //    public Boolean set(String key, Object o);
    public Boolean setWithExpire(final String key, final String value, final int expireSeconds);
    public <T> T get(String key);

//    public List<byte[]> mget();

    public byte[] getForBytes(String key);
    public Boolean expire(String key, long seconds);
    public Long incr(String key);
    public Long incrBy(String key, long delta);
    public Long decr(String key);
    public Long decrBy(String key, long delta);
    public Boolean setNX(final String key, final String value);
    public Boolean del(final String key);

    public Boolean hSet(String key, String hashkey, String value);
    public Boolean hSet(String key, String hashkey, byte[] value);
    public byte[] hGetForBytes(String key, String hashKey);
    public String hGet(String key, String hashKey);

    /**
     * 批量查询，key对应值为空的也要不返回，返回结果集中不包含NULL值
     */
    public List<String> hMGet(final String key, final String... hashkeys);
    /**
     * 批量查询，key对应值为空的也要返回null，返回结果集个数与键个数相同
     */
    public List<String> hMGetOnKeyCount(final String key, final String... hashkeys);
    public List<byte[]> hMGetForBytes(final String key, final String... hashkeys);
    public List<byte[]> hMGet(String key, byte[]... hashkeys);
    public Boolean hMSet(String key, Map<String, String> params);
    public Boolean hMSetForBytes(String key, Map<String, byte[]> params);
    public <T> Map<byte[], byte[]> hGetAll(String key);
    public  List<byte[]> hGetAllForBytes(final String key);
    public  List<Object> hMGetALL(final Map<String, List<String>> keys);
    public Boolean hDel(String key, String... files);
    public Set<String> hkeys(final String key);
    //SortedSet
    public Set<byte[]> zRangeByScore(final String key, final double min, final double max);
    public Set<byte[]> zRevRangeByScore(final byte[] key, final double min, final double max);
    //先写这些吧，事后再补充

    public Set<String> sMembers(String key);

    public Set<String> sMembers(String... key);

    public Boolean getBit(String key, long value);
    public Boolean setBit(String key, long value, boolean flag);
    public Long bitCount(String key);
    public Long bitCount(String key, long value1, long value2);
}
