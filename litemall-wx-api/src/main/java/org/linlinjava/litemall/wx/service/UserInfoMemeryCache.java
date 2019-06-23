package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.db.domain.UserVo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserInfoMemeryCache {
    private static Map<Integer, UserVo> cacheData = new ConcurrentHashMap<Integer, UserVo>();

    public static UserVo get(Integer id) {
        return cacheData.get(id);
    }

    public static void set(UserVo user) {
        try{
            cacheData.put(user.getId(), user);
        }
        catch (Exception e){
            // No handle
        }
    }

    public  static void clear() {
        try{
            cacheData.clear();
        }
        catch (Exception e){
            // No handle
        }
    }

}
