package org.linlinjava.litemall.wx.web;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.service.TestCache;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/test")
public class WxTestController {
    private final Log logger = LogFactory.getLog(WxIndexController.class);

    /**
     * 测试数据
     *
     * @return 测试数据
     */
    @PostMapping("/add")
    public Object add(String url) {
        boolean success = false;
        try {
            success = TestCache.arrayBlockingQueue.add(url);
        }catch (Exception ex) {
            success = false;
        }
        return ResponseUtil.ok(success);
    }

    @GetMapping("/get")
    public Object get() {
        String data = "";
        try {
            data = TestCache.arrayBlockingQueue.remove();
        }catch (Exception ex) {
        }
        return ResponseUtil.ok(data);
    }
}
