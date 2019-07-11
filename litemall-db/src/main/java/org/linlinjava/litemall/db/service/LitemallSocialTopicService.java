package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallSocialTopicMapper;
import org.linlinjava.litemall.db.domain.LitemallSocialTopic;
import org.linlinjava.litemall.db.domain.LitemallSocialTopicExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallSocialTopicService {
    @Resource
    private LitemallSocialTopicMapper litemallSocialTopicMapper;

    public List<LitemallSocialTopic> queryByDynamicId(Integer dynamicID){
        LitemallSocialTopicExample example = new LitemallSocialTopicExample();
        example.or().andDynamicIdEqualTo(dynamicID);
        return litemallSocialTopicMapper.selectByExample(example);
    }

    public int add(LitemallSocialTopic socialTopic){
        return litemallSocialTopicMapper.insertSelective(socialTopic);
    }

}
