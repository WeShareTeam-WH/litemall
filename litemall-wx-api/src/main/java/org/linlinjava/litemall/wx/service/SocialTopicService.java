package org.linlinjava.litemall.wx.service;

import org.apache.commons.lang3.StringUtils;
import org.linlinjava.litemall.core.redis.RedisCommonService;
import org.linlinjava.litemall.core.redis.RedisConnection;
import org.linlinjava.litemall.core.redis.RedisDBEnum;
import org.linlinjava.litemall.db.domain.LitemallSocialTopic;
import org.linlinjava.litemall.db.domain.LitemallTopic;
import org.linlinjava.litemall.db.service.LitemallSocialTopicService;
import org.linlinjava.litemall.db.service.LitemallTopicService;
import org.linlinjava.litemall.wx.vo.DynamicTopicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocialTopicService {
    @Autowired
    private LitemallTopicService litemallTopicService;

    @Autowired
    private LitemallSocialTopicService litemallSocialTopicService;

    @Autowired
    private RedisCommonService redisService;

    public void add(DynamicTopicVo topicVo){
        Boolean isRedisAvailable = RedisConnection.getInstance().isStatus();
        LitemallTopic topic = null;
        if (topicVo.getTopicId() != null && topicVo.getTopicId() != 0) {
            topic = litemallTopicService.findById(topicVo.getTopicId());
        } else {
            List<LitemallTopic> topics = litemallTopicService.queryByTopicTitle(topicVo.getTitle());
            if (topics != null && topics.size() > 0) {
                topic = topics.get(0);
            }
        }
        if (topic == null) {
            topic = new LitemallTopic();
            topic.setTitle(topicVo.getTitle());
            topic.setDeleted(false);
            topic.setReadCount("0");
            topic.setSortOrder(0);
            topic.setAddTime(LocalDateTime.now());
            topic.setUpdateTime(LocalDateTime.now());
            litemallTopicService.add(topic);
        };
        topicVo.setTopicId(topic.getId());
        LitemallSocialTopic socialTopic = new LitemallSocialTopic();
        socialTopic.setDynamicId(topicVo.getDynamicId());
        socialTopic.setTopicId(topic.getId());
        socialTopic.setAddTime(LocalDateTime.now());
        socialTopic.setUpdateTime(LocalDateTime.now());
        socialTopic.setDeleted(false);
        if (isRedisAvailable) {
            redisService.setWithExpire(RedisDBEnum.WXSOCIALCTOPIC+topic.getId(), topic.getTitle(), 24*3600);
        }
        litemallSocialTopicService.add(socialTopic);
    }

    public List<DynamicTopicVo> selectByDynamicId(Integer dynamicID) {
        Boolean isRedisAvailable = RedisConnection.getInstance().isStatus();
        List<LitemallSocialTopic> socialTopics = litemallSocialTopicService.queryByDynamicId(dynamicID);
        List<DynamicTopicVo> dynamicTopicVos = new ArrayList<DynamicTopicVo>();
        for (LitemallSocialTopic socialTopic : socialTopics) {
            DynamicTopicVo findVo = new DynamicTopicVo();
            findVo.setTopicId(socialTopic.getTopicId());
            findVo.setDynamicId(socialTopic.getDynamicId());
            if (isRedisAvailable) {
                String title = redisService.get(RedisDBEnum.WXSOCIALCTOPIC+socialTopic.getTopicId());
                if (!StringUtils.isBlank(title)){
                    findVo.setTitle(title);
                } else {
                    LitemallTopic litemallTopic = litemallTopicService.findById(findVo.getTopicId());
                    findVo.setTitle(litemallTopic.getTitle());
                    redisService.setWithExpire(RedisDBEnum.WXSOCIALCTOPIC+findVo.getTopicId(), findVo.getTitle(), 24*3600);
                }
            } else {
                LitemallTopic litemallTopic = litemallTopicService.findById(findVo.getTopicId());
                findVo.setTitle(litemallTopic.getTitle());
            }
            dynamicTopicVos.add(findVo);
        }
        return  dynamicTopicVos;
    }

}
