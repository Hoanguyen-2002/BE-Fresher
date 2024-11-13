package com.lg.fresher.lgcommerce.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : RedisServiceImpl
 * @ Description : lg_ecommerce_be RedisServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation
 * 11/7/2024       63200502      add method for delete key with prefix
 * */
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     *
     * @param key
     * @param value
     * @param time
     */
    @Override
    public void setValue(String key, Object value, long time) {
        redisTemplate.opsForValue().set(
                key,
                value,
                time,
                TimeUnit.SECONDS);
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public Boolean isExisted(String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     *
     * @param key
     */
    @Override
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    /**
     *
     * @param prefix
     */
    @Override
    public void deleteKeysWithPrefix(String prefix) {
        ScanOptions scanOptions = ScanOptions.scanOptions().match(prefix + "*").count(1000).build();
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(scanOptions);
        while (cursor.hasNext()) {
            redisTemplate.delete(new String(cursor.next()));
        }
        cursor.close();
    }
}
