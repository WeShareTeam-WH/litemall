package org.linlinjava.litemall.core.redis;

public class RedisConnection {
    private static volatile RedisConnection redisConnection;
    private boolean status;
    private RedisConnection() {
    }

    public static RedisConnection getInstance(){
        if (redisConnection != null) {
            return redisConnection;
        } else {
            synchronized (RedisConnection.class) {
                if (redisConnection == null) {
                    redisConnection = new RedisConnection();
                }
            }
            return redisConnection;
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
