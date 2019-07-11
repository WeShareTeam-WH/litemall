package org.linlinjava.litemall.wx.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallSocialReply;
import org.linlinjava.litemall.db.service.LitemallSocialCommentsService;
import org.linlinjava.litemall.db.service.LitemallSocialDynamicService;
import org.linlinjava.litemall.db.service.LitemallSocialReplyService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.UserInfoService;
import org.linlinjava.litemall.wx.vo.DynamicCommentReplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wx/social/reply")
@Validated
public class WxSocialCommentReplyController {
    private final Log logger = LogFactory.getLog(WxSocialCommentController.class);

    @Autowired
    private LitemallSocialDynamicService socialDynamicService;

    @Autowired
    private LitemallSocialCommentsService socialCommentsService;

    @Autowired
    private LitemallSocialReplyService socialReplyService;

    @Autowired
    private UserInfoService userService;

    /**
     *
     * @param id
     * @param commentId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId,
                       @NotNull Integer id,
                       @NotNull Integer commentId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        List<DynamicCommentReplyVo> dynamicCommentReplyVos = new ArrayList<DynamicCommentReplyVo>();
        List<LitemallSocialReply> replys = socialReplyService.queryByDynamicIdAndCommentId(id, commentId, page, size, "update_time", "desc");
        if (replys != null && replys.size() > 0) {
            for (LitemallSocialReply reply : replys) {
                DynamicCommentReplyVo dynamicCommentReplyVo = new DynamicCommentReplyVo();
                dynamicCommentReplyVo.setId(reply.getId());
                dynamicCommentReplyVo.setDynamicId(reply.getDynamicId());
                dynamicCommentReplyVo.setCommentId(reply.getCommentId());
                dynamicCommentReplyVo.setReplyFromUser(userService.findUserVoById(reply.getReplyFrom()));
                dynamicCommentReplyVo.setReplyToUser(userService.findUserVoById(reply.getReplyTo()));
                dynamicCommentReplyVo.setContent(reply.getContent());
                dynamicCommentReplyVo.setClap(reply.getClap());
                dynamicCommentReplyVo.setAddTime(reply.getAddTime());
                dynamicCommentReplyVo.setUpdateTime(reply.getUpdateTime());
                dynamicCommentReplyVos.add(dynamicCommentReplyVo);
            }
        }
        return  dynamicCommentReplyVos;
    }

    /**
     *  Send reply
     * @param reply
     * @return
     */
    @PostMapping("post")
    public Object post(@LoginUser Integer userId, @RequestBody LitemallSocialReply reply) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(reply);
        if (error != null){
            return error;
        }else{
            socialReplyService.add(reply);
            DynamicCommentReplyVo dynamicCommentReplyVo = new DynamicCommentReplyVo();
            dynamicCommentReplyVo.setId(reply.getId());
            dynamicCommentReplyVo.setDynamicId(reply.getDynamicId());
            dynamicCommentReplyVo.setCommentId(reply.getCommentId());
            dynamicCommentReplyVo.setReplyFromUser(userService.findUserVoById(reply.getReplyFrom()));
            dynamicCommentReplyVo.setReplyToUser(userService.findUserVoById(reply.getReplyTo()));
            dynamicCommentReplyVo.setContent(reply.getContent());
            dynamicCommentReplyVo.setClap(reply.getClap());
            dynamicCommentReplyVo.setAddTime(reply.getAddTime());
            dynamicCommentReplyVo.setUpdateTime(reply.getUpdateTime());
            return ResponseUtil.ok(dynamicCommentReplyVo);
        }
    }

    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody LitemallSocialReply reply) {
        socialReplyService.logicDelete(reply.getId());
        return ResponseUtil.ok(reply.getId());
    }

    private Object validate(LitemallSocialReply reply) {
        String content = reply.getContent();
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}
