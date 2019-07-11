package org.linlinjava.litemall.core.jobs;


import org.linlinjava.litemall.core.redis.RedisBackupCounter;
import org.linlinjava.litemall.core.redis.RedisCommonService;
import org.linlinjava.litemall.core.redis.RedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RedisJob {
    @Autowired
    RedisCommonService redisService;

    /**
     * 检查Redis链接状态
     * 每隔5分钟执行一次
     */
    @Scheduled(cron="0 0/5 * * * ?")
    public void checkRedisConnectionJob(){
        Boolean redisConnected = false;
        try {
            redisConnected = redisService.checkRedisConnection();
            RedisConnection.getInstance().setStatus(redisConnected);
        } catch (Exception e) {
            e.printStackTrace();
            RedisConnection.getInstance().setStatus(redisConnected);
        }
    }

    /**
     * 检查Redis同步计数器不频繁计数的缓存清理
     * 每天早上4点执行一遍
     */
    @Scheduled(cron="0 0 4 * * ?")
    public void cleanRedisCounterJob(){
        try {
            RedisBackupCounter.getInstance().cleanUselessCounter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
