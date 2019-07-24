package org.linlinjava.litemall.wx.service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class TestCache {
    public static Map<Integer, String> mapCacheData = new ConcurrentHashMap<Integer, String>();
    public static Queue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(1000);
}
