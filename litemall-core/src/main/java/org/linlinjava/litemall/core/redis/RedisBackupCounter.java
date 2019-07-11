package org.linlinjava.litemall.core.redis;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisBackupCounter {
    private static volatile RedisBackupCounter instance;

    // Count for SocialDynamicClap : every 20 clap trigger, write redis -> DB
    // Count for SocialDynamicCommentClap : every 20 clap trigger, write redis -> DB
    // Better 20 can be config in backend.
    private int replicatorCount = 20;
    private int cleanMoreThanTime = 1 * 24 * 3600; // 1 day, lastupdatetime more then 1 day from now, need clean.
    private Map<String, CounterType> counter = new ConcurrentHashMap<String, CounterType>();

    private RedisBackupCounter() {
    }

    public static RedisBackupCounter getInstance(){
        if (instance != null) {
            return instance;
        } else {
            synchronized (RedisBackupCounter.class) {
                if (instance == null) {
                    instance = new RedisBackupCounter();
                }
            }
            return instance;
        }
    }

    class CounterType{
        private Integer id;
        private Integer count;
        private LocalDateTime lastUpdateTime;

        public CounterType(Integer id, Integer count, LocalDateTime lastUpdateTime) {
            this.id = id;
            this.count = count;
            this.lastUpdateTime = lastUpdateTime;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public LocalDateTime getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        @Override
        public String toString() {
            return "CounterType{" +
                    "id=" + id +
                    ", count=" + count +
                    ", lastUpdateTime=" + lastUpdateTime +
                    '}';
        }
    }

    // True => means more then 20 count clap, need write redis to MySQL DB.
    // False => means continue count.
    public boolean setCountIncWithRedisReplicator(String type, Integer id) {
        CounterType counterType = counter.get(type + id);
        if (counterType == null) {
            counter.put(type+id, new CounterType(id, 0, LocalDateTime.now()));
            return false;
        } else {
            counterType.count += 1;
            counterType.lastUpdateTime = LocalDateTime.now();
            if (counterType.count > replicatorCount) {
                counterType.count = 0;
                return true;
            }
            else {
                return false;
            }
        }
    }

    /**
     * When lastupdatetime is more than 1 day.
     */
    public void cleanUselessCounter() {
        Collection<CounterType> values = counter.values();
        List<CounterType> removeList = new ArrayList<CounterType>();
        for (CounterType counterType : values) {
            if (Period.between(counterType.getLastUpdateTime().toLocalDate(), LocalDateTime.now().toLocalDate()).getDays() > 1) {
                removeList.add(counterType);
            }
        }
        values.removeAll(removeList);
    }

}
