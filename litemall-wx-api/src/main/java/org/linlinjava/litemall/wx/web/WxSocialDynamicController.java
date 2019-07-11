package org.linlinjava.litemall.wx.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.redis.RedisBackupCounter;
import org.linlinjava.litemall.core.redis.RedisCommonService;
import org.linlinjava.litemall.core.redis.RedisConnection;
import org.linlinjava.litemall.core.redis.RedisDBEnum;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallSocialComment;
import org.linlinjava.litemall.db.domain.LitemallSocialDynamic;
import org.linlinjava.litemall.db.domain.LitemallSocialReply;
import org.linlinjava.litemall.db.domain.UserVo;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.HomeCacheManager;
import org.linlinjava.litemall.wx.service.SocialTopicService;
import org.linlinjava.litemall.wx.service.UserInfoService;
import org.linlinjava.litemall.wx.vo.DynamicCommentReplyVo;
import org.linlinjava.litemall.wx.vo.DynamicCommentVo;
import org.linlinjava.litemall.wx.vo.DynamicDetailVo;
import org.linlinjava.litemall.wx.vo.DynamicTopicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private SocialTopicService socialTopicService;

    @Autowired
    private RedisCommonService redisService;


    /**
     * Get one dynamic social detail
     * @param id
     * @return
     */
    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId,
                         @NotNull Integer id,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "100") Integer size) {
        DynamicDetailVo detail = new DynamicDetailVo();
        Boolean isRedisAvailable = RedisConnection.getInstance().isStatus();
        LitemallSocialDynamic socialDynamic = socialDynamicService.queryById(id);
        if (socialDynamic != null){
            detail.setId(socialDynamic.getId());
            detail.setOwer(userService.findUserVoById(socialDynamic.getOwerId()));
            detail.setTitle(socialDynamic.getTitle());
            detail.setContent(socialDynamic.getContent());
            detail.setPicture(socialDynamic.getPicture());
            detail.setClap(isRedisAvailable ? redisService.bitCount(RedisDBEnum.WXSOCIALDYNAMIC+socialDynamic.getId()).intValue(): socialDynamic.getClap());
            detail.setLoginUserClap(userId == null || !isRedisAvailable ? false : redisService.getBit(RedisDBEnum.WXSOCIALDYNAMIC+socialDynamic.getId(), userId));
            detail.setAddTime(socialDynamic.getAddTime());
            detail.setUpdateTime(socialDynamic.getUpdateTime());
            detail.setCommentsCount(socialCommentsService.countByDynamicId(socialDynamic.getId()));
            detail.setTopics(socialTopicService.selectByDynamicId(socialDynamic.getId()));
            List<LitemallSocialComment> comments = socialCommentsService.querySelective(socialDynamic.getId(), page, size, "update_time", "desc");
            if (comments != null && comments.size() > 0) {
                List<DynamicCommentVo> litemallSocialComments = new ArrayList<DynamicCommentVo>();
                for (LitemallSocialComment comment : comments) {
                    DynamicCommentVo dynamicCommentVo = new DynamicCommentVo();
                    dynamicCommentVo.setId(comment.getId());
                    dynamicCommentVo.setDynamicId(comment.getDynamicId());
                    dynamicCommentVo.setContent(comment.getContent());
                    dynamicCommentVo.setUser(userService.findUserVoById(comment.getUserId()));
                    dynamicCommentVo.setClap(isRedisAvailable ? redisService.bitCount(RedisDBEnum.WXSOCIALCOMMENT+comment.getId()).intValue() : comment.getClap());
                    dynamicCommentVo.setLoginUserClap(userId == null || !isRedisAvailable ? false : redisService.getBit(RedisDBEnum.WXSOCIALCOMMENT+comment.getId(), userId));
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
    public Object userDynamics(@LoginUser Integer userId,
                               @NotNull Integer id,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "100") Integer size) {
        List<DynamicDetailVo> detailsVo = new ArrayList<DynamicDetailVo>();
        Integer dynamicCount = socialDynamicService.countByUserId(id);
        Boolean isRedisAvailable = RedisConnection.getInstance().isStatus();
        List<LitemallSocialDynamic> details = socialDynamicService.queryByUserSelective(id, page, size, "update_time", "desc");
        UserVo userInfo = userService.findUserVoById(id);
        for (LitemallSocialDynamic socialDynamic : details){
            DynamicDetailVo detail = new DynamicDetailVo();
            detail.setId(socialDynamic.getId());
            detail.setOwer(userInfo);
            detail.setTitle(socialDynamic.getTitle());
            detail.setContent(socialDynamic.getContent());
            detail.setPicture(socialDynamic.getPicture());
            detail.setClap(isRedisAvailable ? redisService.bitCount(RedisDBEnum.WXSOCIALDYNAMIC+socialDynamic.getId()).intValue(): socialDynamic.getClap());
            detail.setAddTime(socialDynamic.getAddTime());
            detail.setUpdateTime(socialDynamic.getUpdateTime());
            detail.setLoginUserClap(userId == null || !isRedisAvailable ? false : redisService.getBit(RedisDBEnum.WXSOCIALDYNAMIC+socialDynamic.getId(), userId));
            detail.setCommentsCount(socialCommentsService.countByDynamicId(socialDynamic.getId()));
            detail.setTopics(socialTopicService.selectByDynamicId(socialDynamic.getId()));
            detailsVo.add(detail);
        }
        Map<String, Object> entity = new HashMap<>();
        entity.put("userInfo", userInfo);
        entity.put("dynamicCount", dynamicCount);
        entity.put("dynamics", detailsVo);
        return ResponseUtil.ok(entity);
    }

    @PostMapping("add")
    public Object add(@LoginUser Integer userId,
                         @RequestBody DynamicDetailVo dynamicVo) {
        LitemallSocialDynamic dynamic = new LitemallSocialDynamic();
        dynamic.setContent(dynamicVo.getContent());
        dynamic.setTitle(dynamicVo.getTitle());
        dynamic.setPicture(dynamicVo.getPicture());
        Boolean isRedisAvailable = RedisConnection.getInstance().isStatus();
        if (userId == null || userId == 0) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(dynamic);
        if (error != null) {
            return error;
        }
        dynamic.setOwerId(userId);
        dynamic.setClap(0);
        dynamic.setDeleted(false);
        socialDynamicService.add(dynamic);
        if (dynamicVo.getTopics() != null) {
            for (DynamicTopicVo topicVo : dynamicVo.getTopics()) {
                topicVo.setDynamicId(dynamic.getId());
                socialTopicService.add(topicVo);
            }
        }
        DynamicDetailVo detail = new DynamicDetailVo();
        if (HomeCacheManager.hasData(HomeCacheManager.SOCIAL)) {
            Object data = HomeCacheManager.getCacheData(HomeCacheManager.SOCIAL);
            List<DynamicDetailVo> dynamics = (List)((Map<String, Object>) data).get("dynamic");
            detail.setId(dynamic.getId());
            detail.setOwer(userService.findUserVoById(dynamic.getOwerId()));
            detail.setClap(isRedisAvailable ? redisService.bitCount(RedisDBEnum.WXSOCIALDYNAMIC+dynamic.getId()).intValue(): dynamic.getClap());
            detail.setLoginUserClap(userId == null || !isRedisAvailable ? false : redisService.getBit(RedisDBEnum.WXSOCIALDYNAMIC+dynamic.getId(), userId));
            detail.setTitle(dynamic.getTitle());
            detail.setContent(dynamic.getContent());
            detail.setPicture(dynamic.getPicture());
            detail.setAddTime(dynamic.getAddTime());
            detail.setUpdateTime(dynamic.getUpdateTime());
            detail.setCommentsCount(socialCommentsService.countByDynamicId(dynamic.getId()));
            detail.setTopics(dynamicVo.getTopics());
            dynamics.add(0, detail);
        }
        return ResponseUtil.ok(detail);
    }

    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId,
                         @RequestBody LitemallSocialDynamic dynamic) {
        socialDynamicService.logicDelete(dynamic.getId());
        if (HomeCacheManager.hasData(HomeCacheManager.SOCIAL)) {
            Object data = HomeCacheManager.getCacheData(HomeCacheManager.SOCIAL);
            List<DynamicDetailVo> dynamics = (List) ((Map<String, Object>) data).get("dynamic");
            DynamicDetailVo deleteVo = null;
            for (DynamicDetailVo findVo : dynamics) {
                if (findVo.getId() == dynamic.getId()) {
                    deleteVo = findVo;
                    break;
                }
            }
            if (deleteVo != null) {
                dynamics.remove(deleteVo);
            }
        }
        return ResponseUtil.ok(dynamic.getId());
    }

    @PostMapping("clap")
    public Object clapUpdate(@LoginUser Integer userId,
                             @RequestBody LitemallSocialDynamic dynamic) {
        LitemallSocialDynamic dynamicUpdate = dynamic;
        if (RedisConnection.getInstance().isStatus() && userId != null) {
            redisService.setBit(RedisDBEnum.WXSOCIALDYNAMIC+dynamic.getId(), userId, true);
            if (RedisBackupCounter.getInstance().setCountIncWithRedisReplicator(RedisDBEnum.WXSOCIALDYNAMIC, dynamic.getId().intValue())){
                dynamicUpdate = socialDynamicService.queryById(dynamic.getId());
                dynamicUpdate.setClap(redisService.bitCount(RedisDBEnum.WXSOCIALCOMMENT+dynamic.getId()).intValue());
                socialDynamicService.update(dynamicUpdate);
            }
        }else{
            dynamicUpdate = socialDynamicService.queryById(dynamic.getId());
            dynamicUpdate.setClap(dynamicUpdate.getClap() == null ? 0 + 1: dynamicUpdate.getClap() + 1);
            socialDynamicService.update(dynamicUpdate);
        }
        return ResponseUtil.ok(dynamicUpdate);
    }

    @PostMapping("cancelClap")
    public Object cancelClap(@LoginUser Integer userId,
                             @RequestBody LitemallSocialDynamic dynamic) {
        LitemallSocialDynamic dynamicUpdate = dynamic;
        if (RedisConnection.getInstance().isStatus() && userId != null) {
            redisService.setBit(RedisDBEnum.WXSOCIALDYNAMIC+dynamic.getId(), userId, false);
        }else{
            dynamicUpdate = socialDynamicService.queryById(dynamic.getId());
            dynamicUpdate.setClap(dynamicUpdate.getClap() == null ? 0: dynamicUpdate.getClap() - 1);
            socialDynamicService.update(dynamicUpdate);
        }
        return ResponseUtil.ok(dynamicUpdate);
    }

    private Object validate(LitemallSocialDynamic dynamic) {
        String content = dynamic.getContent();
        if (StringUtils.isBlank(content)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}
