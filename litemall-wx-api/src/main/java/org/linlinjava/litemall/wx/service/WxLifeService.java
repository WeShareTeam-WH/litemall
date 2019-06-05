package org.linlinjava.litemall.wx.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallSocialDynamic;
import org.springframework.stereotype.Service;

@Service
public class WxLifeService {
    private final Log logger = LogFactory.getLog(WxLifeService.class);
    
    /**
     * 评价订单商品
     * <p>
     * 确认商品收货或者系统自动确认商品收货后7天内可以评价，过期不能评价。
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    public Object submitSocialDynamic(Integer userId, String body) {
    	if (userId == null) {
            return ResponseUtil.unlogin();
        }

        String content = JacksonUtil.parseString(body, "content");
        Boolean hasPicture = JacksonUtil.parseBoolean(body, "hasPicture");
        List<String> picUrls = JacksonUtil.parseStringList(body, "picUrls");
        if (hasPicture == null || !hasPicture) {
            picUrls = new ArrayList<>(0);
        }

        if (content == null && picUrls == null) {
            return ResponseUtil.badArgument();
        }

        LitemallSocialDynamic litemallSocialDynamic = new LitemallSocialDynamic();
        litemallSocialDynamic.setTitle("");
        litemallSocialDynamic.setContent(content);
        litemallSocialDynamic.setPicture(picUrls.toArray(new String[]{}));
        return ResponseUtil.ok();
    }
}
