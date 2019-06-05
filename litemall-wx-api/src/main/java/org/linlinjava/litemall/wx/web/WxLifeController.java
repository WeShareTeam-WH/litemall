package org.linlinjava.litemall.wx.web;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.HomeCacheManager;
import org.linlinjava.litemall.wx.service.WxLifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生活服务
 */
@RestController
@RequestMapping("/wx/life")
@Validated
public class WxLifeController {
    private final Log logger = LogFactory.getLog(WxLifeController.class);

    @Autowired
    private WxLifeService wxLifeService;

    @GetMapping("/cache")
    public Object cache(@NotNull String key) {
        if (!key.equals("litemall_cache")) {
            return ResponseUtil.fail();
        }

        // 清除缓存
        HomeCacheManager.clearAll();
        return ResponseUtil.ok("缓存已清除");
    }

    /**
     * 首页数据
     * @param userId 当用户已经登录时，非空。为登录状态为null
     * @return 生活数据
     */
    @GetMapping("/index")
    public Object index(@LoginUser Integer userId) {
        //优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.LIFE)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.LIFE));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(1);

       /* Callable<List> bannerListCallable = () -> adService.queryIndex();

        FutureTask<List> bannerTask = new FutureTask<>(bannerListCallable);

        executorService.submit(bannerTask);

        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("banner", bannerTask.get());
            //缓存数据
            HomeCacheManager.loadData(HomeCacheManager.LIFE, entity);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }finally {
            executorService.shutdown();
        }*/
        //return ResponseUtil.ok(entity);
        return null;
    }

    @PostMapping("upload")
    public Object receive(@LoginUser Integer userId, @RequestBody String body) {
        return wxLifeService.submitSocialDynamic(userId, body);
    }
}