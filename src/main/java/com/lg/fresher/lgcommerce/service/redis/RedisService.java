package com.lg.fresher.lgcommerce.service.redis;

public interface RedisService {
    void setValue(String key, Object value, long time);
    Object getValue(String key);
    Boolean isExisted(String key);
    void deleteValue(String key);
    void deleteKeysWithPrefix(String prefix);
}
