package org.blankjee.cloudfast.service;

/**
 * @Description 缓存服务
 * @Author blankjee
 * @Date 2020/7/8 9:57
 */
public interface ICacheService {

    /**
     * 保存字符串到缓存
     * @param cacheKey 缓存key
     * @param cacheValue 缓存值
     * @param timeout 过期时间（分钟）
     */
    void cacheStringData(String cacheKey, String cacheValue, long timeout);

    /**
     * 获取key获取缓存信息
     * @param cacheKey 缓存key
     * @return
     */
    String getCacheInfoByCode(String cacheKey);

    /**
     * 缓存对象到缓存
     * @param cacheKey
     * @param cacheValue
     * @param timeout
     */
    void cacheObjData(String cacheKey, Object cacheValue, long timeout);

    /**
     * 获取缓存对象
     * @param cacheKey
     * @return
     */
    Object getObjCacheByCode(String cacheKey);

    /**
     * 删除指定code的缓存
     * @param cacheKey
     * @return
     */
    boolean delCacheByCode(String cacheKey);
}
