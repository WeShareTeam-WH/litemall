package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.UserVo;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoService {
    @Autowired
    private LitemallUserService userService;


    public UserInfo getInfo(Integer userId) {
        LitemallUser user = userService.findById(userId);
        Assert.state(user != null, "用户不存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatarUrl(user.getAvatar());
        return userInfo;
    }

    public UserVo findUserVoById(Integer id) {
        UserVo userVo = UserInfoMemeryCache.get(id);
        if (userVo == null) {
            userVo = userService.findUserVoById(id);
        }
        if (userVo != null) {
            UserInfoMemeryCache.set(userVo);
        }
        return userVo;
    }

}
