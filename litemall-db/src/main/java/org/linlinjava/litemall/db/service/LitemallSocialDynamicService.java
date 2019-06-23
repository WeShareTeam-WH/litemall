package org.linlinjava.litemall.db.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallSocialDynamicMapper;
import org.linlinjava.litemall.db.domain.LitemallSocialComment;
import org.linlinjava.litemall.db.domain.LitemallSocialDynamic;
import org.linlinjava.litemall.db.domain.LitemallSocialDynamicExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallSocialDynamicService {
    @Resource
    private LitemallSocialDynamicMapper socialDynamicMapper;

    public LitemallSocialDynamic queryById(Integer userId) {
        LitemallSocialDynamicExample example = new LitemallSocialDynamicExample();
        example.or().andIdEqualTo(userId).andDeletedEqualTo(false);
        return socialDynamicMapper.selectOneByExampleWithBLOBs(example);
    }

    public int countByUserId(Integer userId) {
        LitemallSocialDynamicExample example = new LitemallSocialDynamicExample();
        example.or().andOwerIdEqualTo(userId).andDeletedEqualTo(false);
        return (int) socialDynamicMapper.countByExample(example);
    }

    public List<LitemallSocialDynamic> queryByUserSelective(Integer userId, Integer page, Integer size, String sort, String order) {
        LitemallSocialDynamicExample example = new LitemallSocialDynamicExample();
        example.or().andOwerIdEqualTo(userId).andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        return socialDynamicMapper.selectByExampleWithBLOBs(example);
    }
}
