package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallSocialReplyMapper;
import org.linlinjava.litemall.db.domain.LitemallSocialReply;
import org.linlinjava.litemall.db.domain.LitemallSocialReplyExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallSocialReplyService {

    @Resource
    private LitemallSocialReplyMapper socialReplyMapper;

    public List<LitemallSocialReply> queryByDynamicIdAndCommentId(Integer dynamicId, Integer commentId, Integer page,  Integer size, String sort, String order){
        LitemallSocialReplyExample example = new LitemallSocialReplyExample();
        example.or().andDynamicIdEqualTo(dynamicId).andCommentIdEqualTo(commentId).andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        return socialReplyMapper.selectByExample(example);
    }

    public int countByDynamicIdAndCommentId(Integer dynamicId, Integer commentId) {
        LitemallSocialReplyExample example = new LitemallSocialReplyExample();
        example.or().andDynamicIdEqualTo(dynamicId).andCommentIdEqualTo(commentId).andDeletedEqualTo(false);
        return (int) socialReplyMapper.countByExample(example);
    }

    public int add(LitemallSocialReply reply){
        reply.setAddTime(LocalDateTime.now());
        reply.setUpdateTime(LocalDateTime.now());
        return socialReplyMapper.insertSelective(reply);
    }

    public int logicDelete(Integer id) {
        LitemallSocialReply reply = new LitemallSocialReply();
        reply.setId(id);
        reply.setDeleted(true);
        reply.setUpdateTime(LocalDateTime.now());
        return socialReplyMapper.updateByPrimaryKeySelective(reply);
    }

    public int delete(Integer id) {
        return socialReplyMapper.deleteByPrimaryKey(id);
    }
}
