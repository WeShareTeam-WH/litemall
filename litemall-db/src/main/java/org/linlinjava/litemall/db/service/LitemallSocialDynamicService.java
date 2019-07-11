package org.linlinjava.litemall.db.service;
import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallSocialDynamicMapper;
import org.linlinjava.litemall.db.domain.LitemallSocialComment;
import org.linlinjava.litemall.db.domain.LitemallSocialDynamic;
import org.linlinjava.litemall.db.domain.LitemallSocialDynamicExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallSocialDynamicService {
    @Resource
    private LitemallSocialDynamicMapper socialDynamicMapper;

    public LitemallSocialDynamic queryById(Integer id) {
        return socialDynamicMapper.selectByPrimaryKey(id);
    }

    public int update(LitemallSocialDynamic dynamic) {
        return socialDynamicMapper.updateByPrimaryKeySelective(dynamic);
    }

    public int add(LitemallSocialDynamic dynamic){
        dynamic.setAddTime(LocalDateTime.now());
        dynamic.setUpdateTime(LocalDateTime.now());
        return  socialDynamicMapper.insert(dynamic);
    }

    public int logicDelete(Integer id) {
        LitemallSocialDynamic dynamic = new LitemallSocialDynamic();
        dynamic.setId(id);
        dynamic.setDeleted(true);
        dynamic.setUpdateTime(LocalDateTime.now());
        return socialDynamicMapper.updateByPrimaryKeySelective(dynamic);
    }

    public int countByUserId(Integer userId) {
        LitemallSocialDynamicExample example = new LitemallSocialDynamicExample();
        example.or().andOwerIdEqualTo(userId).andDeletedEqualTo(false);
        return (int) socialDynamicMapper.countByExample(example);
    }

    public int count(String keyword) {
        LitemallSocialDynamicExample example = new LitemallSocialDynamicExample();
        if (!StringUtils.isEmpty(keyword)){
            example.or().andDeletedEqualTo(false).andContentLike(keyword);
        } else {
            example.or().andDeletedEqualTo(false);
        }
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

    public List<LitemallSocialDynamic> queryBySelective(Integer page, Integer size, String sort, String order, String keyword) {
        LitemallSocialDynamicExample example = new LitemallSocialDynamicExample();
        if (!StringUtils.isEmpty(keyword)){
            example.or().andDeletedEqualTo(false).andContentLike(keyword);
        } else {
            example.or().andDeletedEqualTo(false);
        }
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        return socialDynamicMapper.selectByExampleWithBLOBs(example);
    }
}
