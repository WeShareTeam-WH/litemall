package org.linlinjava.litemall.wx.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallSocialComment;
import org.linlinjava.litemall.db.domain.LitemallSocialReply;
import org.linlinjava.litemall.db.service.LitemallSocialCommentsService;
import org.linlinjava.litemall.db.service.LitemallSocialDynamicService;
import org.linlinjava.litemall.db.service.LitemallSocialReplyService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.UserInfoService;
import org.linlinjava.litemall.wx.vo.DynamicCommentReplyVo;
import org.linlinjava.litemall.wx.vo.DynamicCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wx/social/comment")
@Validated
public class WxSocialCommentController {
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
     * @param id dynamic id
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list")
    public Object list(@NotNull Integer id,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        List<DynamicCommentVo> litemallSocialComments = new ArrayList<DynamicCommentVo>();
        List<LitemallSocialComment> comments = socialCommentsService.querySelective(id, page, size, "update_time", "desc");
        if (comments != null && comments.size() > 0) {
            for (LitemallSocialComment comment : comments) {
                DynamicCommentVo dynamicCommentVo = new DynamicCommentVo();
                dynamicCommentVo.setId(comment.getId());
                dynamicCommentVo.setDynamicId(comment.getDynamicId());
                dynamicCommentVo.setContent(comment.getContent());
                dynamicCommentVo.setUser(userService.findUserVoById(comment.getUserId()));
                dynamicCommentVo.setClap(comment.getClap());
                dynamicCommentVo.setAddTime(comment.getAddTime());
                dynamicCommentVo.setUpdateTime(comment.getUpdateTime());
                List<LitemallSocialReply> replys = socialReplyService.queryByDynamicIdAndCommentId(id, comment.getId(), page,size, "update_time", "asc");
                if (replys != null && replys.size() > 0) {
                    List<DynamicCommentReplyVo> dynamicCommentReplyVos = new ArrayList<DynamicCommentReplyVo>();
                    for (LitemallSocialReply reply : replys){
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
                    dynamicCommentVo.setReplys(dynamicCommentReplyVos);
                }
                litemallSocialComments.add(dynamicCommentVo);
            }
        }
        return ResponseUtil.ok(litemallSocialComments);
    }
    
    @PostMapping("post")
    public Object post(@LoginUser Integer userId,@RequestBody LitemallSocialComment comment) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(comment);
        if (error != null){
            return error;
        }else{
            socialCommentsService.add(comment);
            DynamicCommentVo dynamicCommentVo = new DynamicCommentVo();
            dynamicCommentVo.setId(comment.getId());
            dynamicCommentVo.setDynamicId(comment.getDynamicId());
            dynamicCommentVo.setContent(comment.getContent());
            dynamicCommentVo.setUser(userService.findUserVoById(comment.getUserId()));
            dynamicCommentVo.setClap(comment.getClap());
            dynamicCommentVo.setAddTime(comment.getAddTime());
            dynamicCommentVo.setUpdateTime(comment.getUpdateTime());
            return ResponseUtil.ok(dynamicCommentVo);
        }
    }

    @PostMapping("delete")
    public Object delete(@RequestBody LitemallSocialComment comment) {
        socialCommentsService.logicDelete(comment.getId());
        return ResponseUtil.ok(comment.getId());
    }

    @PostMapping("clap")
    public Object clapUpdate(@RequestBody LitemallSocialComment comment) {
        LitemallSocialComment updateComment = socialCommentsService.queryById(comment.getId());
        updateComment.setClap(updateComment.getClap() + 1);
        socialCommentsService.update(updateComment);
        return ResponseUtil.ok(updateComment);
    }

    private Object validate(LitemallSocialComment comment) {
        String content = comment.getContent();
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}
