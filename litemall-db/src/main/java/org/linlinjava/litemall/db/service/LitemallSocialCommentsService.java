package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallCommentMapper;
import org.linlinjava.litemall.db.dao.LitemallSocialCommentMapper;
import org.linlinjava.litemall.db.domain.LitemallGoodsExample;
import org.linlinjava.litemall.db.domain.LitemallSocialComment;
import org.linlinjava.litemall.db.domain.LitemallSocialCommentExample;
import org.linlinjava.litemall.db.domain.LitemallSocialReply;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallSocialCommentsService {
    @Resource
    private LitemallSocialCommentMapper commentMapper;

    public  LitemallSocialComment queryById(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    public int countByDynamicId(Integer id) {
        LitemallSocialCommentExample example = new LitemallSocialCommentExample();
        example.or().andDynamicIdEqualTo(id).andDeletedEqualTo(false);
        return (int) commentMapper.countByExample(example);
    }

    public List<LitemallSocialComment> querySelective(Integer dynamicId, Integer page,  Integer size, String sort, String order){
        LitemallSocialCommentExample example = new LitemallSocialCommentExample();
        example.or().andDynamicIdEqualTo(dynamicId).andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        return commentMapper.selectByExample(example);
    }

    public int add(LitemallSocialComment comment){
        comment.setAddTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        return commentMapper.insertSelective(comment);
    }

    public int logicDelete(Integer id) {
        LitemallSocialComment comment = new LitemallSocialComment();
        comment.setId(id);
        comment.setDeleted(true);
        comment.setUpdateTime(LocalDateTime.now());
        return commentMapper.updateByPrimaryKeySelective(comment);
    }

    public int delete(Integer id) {
        return commentMapper.deleteByPrimaryKey(id);
    }

    public int update(LitemallSocialComment comment) {
        return commentMapper.updateByPrimaryKeySelective(comment);
    }

}
