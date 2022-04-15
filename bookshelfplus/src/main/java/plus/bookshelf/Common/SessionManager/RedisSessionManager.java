package plus.bookshelf.Common.SessionManager;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisSessionManager implements SessionManager {

    /**
     * 私有化构造函数
     */
    private RedisSessionManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    static SessionManager sessionManager = null;

    /**
     * 通过此方法获取当前类的实例
     *
     * @return
     */
    public static SessionManager getInstance(RedisTemplate redisTemplate) {
        if (sessionManager == null)
            sessionManager = new RedisSessionManager(redisTemplate);
        return sessionManager;
    }

    static RedisTemplate redisTemplate = null;

    @Override
    public Object getValue(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setValue(String key, Object value) {
        redisTemplate.expire(key, 1, TimeUnit.HOURS);

        // 建立token和用户登录态之间的联系
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
