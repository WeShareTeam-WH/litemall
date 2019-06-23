package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallSocialComment;
import org.linlinjava.litemall.db.domain.LitemallSocialDynamic;
import org.linlinjava.litemall.db.domain.LitemallSocialReply;
import org.linlinjava.litemall.db.domain.UserVo;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.UserInfoService;
import org.linlinjava.litemall.wx.vo.DynamicCommentReplyVo;
import org.linlinjava.litemall.wx.vo.DynamicCommentVo;
import org.linlinjava.litemall.wx.vo.DynamicDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("/wx/social/dynamic")
@Validated
public class WxSocialDynamicController {
    private final Log logger = LogFactory.getLog(WxSocialDynamicController.class);

    @Autowired
    private LitemallSocialDynamicService socialDynamicService;

    @Autowired
    private LitemallSocialCommentsService socialCommentsService;

    @Autowired
    private  LitemallSocialReplyService socialReplyService;

    @Autowired
    private UserInfoService userService;

    /**
     * Get one dynamic social detail
     * @param id
     * @return
     */
    @GetMapping("detail")
    public Object detail(@NotNull Integer id,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "100") Integer size) {
        DynamicDetailVo detail = new DynamicDetailVo();
        LitemallSocialDynamic socialDynamic = socialDynamicService.queryById(id);
        if (socialDynamic != null){
            detail.setId(socialDynamic.getId());
            detail.setOwer(userService.findUserVoById(socialDynamic.getOwerId()));
            detail.setTitle(socialDynamic.getTitle());
            detail.setContent(socialDynamic.getContent());
            detail.setPicture(socialDynamic.getPicture());
            detail.setClap(socialDynamic.getClap());
            detail.setAddTime(socialDynamic.getAddTime());
            detail.setUpdateTime(socialDynamic.getUpdateTime());
            detail.setCommentsCount(socialCommentsService.countByDynamicId(socialDynamic.getId()));
            List<LitemallSocialComment> comments = socialCommentsService.querySelective(socialDynamic.getId(), page, size, "update_time", "desc");
            if (comments != null && comments.size() > 0) {
                List<DynamicCommentVo> litemallSocialComments = new ArrayList<DynamicCommentVo>();
                for (LitemallSocialComment comment : comments) {
                    DynamicCommentVo dynamicCommentVo = new DynamicCommentVo();
                    dynamicCommentVo.setId(comment.getId());
                    dynamicCommentVo.setDynamicId(comment.getDynamicId());
                    dynamicCommentVo.setContent(comment.getContent());
                    dynamicCommentVo.setUser(userService.findUserVoById(comment.getUserId()));
                    dynamicCommentVo.setClap(comment.getClap());
                    dynamicCommentVo.setAddTime(comment.getAddTime());
                    dynamicCommentVo.setUpdateTime(comment.getUpdateTime());
                    dynamicCommentVo.setReplysCount(socialReplyService.countByDynamicIdAndCommentId(comment.getDynamicId(), comment.getId()));
                    List<LitemallSocialReply> replys = socialReplyService.queryByDynamicIdAndCommentId(socialDynamic.getId(), comment.getId(), page,size, "update_time", "asc");
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
                detail.setComments(litemallSocialComments);
            }
        }
        return ResponseUtil.ok(detail);
    }

    @GetMapping("userDynamics")
    public Object userDynamics(@NotNull Integer id,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "100") Integer size) {
        List<DynamicDetailVo> detailsVo = new ArrayList<DynamicDetailVo>();
        Integer dynamicCount = socialDynamicService.countByUserId(id);
        List<LitemallSocialDynamic> details = socialDynamicService.queryByUserSelective(id, page, size, "update_time", "desc");
        UserVo userInfo = userService.findUserVoById(id);
        for (LitemallSocialDynamic socialDynamic : details){
            DynamicDetailVo detail = new DynamicDetailVo();
            detail.setId(socialDynamic.getId());
            detail.setOwer(userInfo);
            detail.setTitle(socialDynamic.getTitle());
            detail.setContent(socialDynamic.getContent());
            detail.setPicture(socialDynamic.getPicture());
            detail.setClap(socialDynamic.getClap());
            detail.setAddTime(socialDynamic.getAddTime());
            detail.setUpdateTime(socialDynamic.getUpdateTime());
            detail.setCommentsCount(socialCommentsService.countByDynamicId(socialDynamic.getId()));
            detailsVo.add(detail);
        }
        Map<String, Object> entity = new HashMap<>();
        entity.put("userInfo", userInfo);
        entity.put("dynamicCount", dynamicCount);
        entity.put("dynamics", detailsVo);
        return ResponseUtil.ok(entity);
    }
}
