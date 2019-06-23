package org.linlinjava.litemall.wx.vo;
import org.linlinjava.litemall.db.domain.UserVo;

import java.time.LocalDateTime;
public class DynamicCommentReplyVo {
    private Integer id;
    
    private Integer dynamicId;
    
    private Integer commentId;
    
    private UserVo replyFromUser;
    
    private UserVo replyToUser;
    
    private String content;
    
    private Integer clap;
    
    private LocalDateTime addTime;
    
    private LocalDateTime updateTime;
    
    private Boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public UserVo getReplyFromUser() {
        return replyFromUser;
    }

    public void setReplyFromUser(UserVo replyFromUser) {
        this.replyFromUser = replyFromUser;
    }

    public UserVo getReplyToUser() {
        return replyToUser;
    }

    public void setReplyToUser(UserVo replyToUser) {
        this.replyToUser = replyToUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getClap() {
        return clap;
    }

    public void setClap(Integer clap) {
        this.clap = clap;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "DynamicCommentReplyVo{" +
                "id=" + id +
                ", dynamicId=" + dynamicId +
                ", commentId=" + commentId +
                ", replyFromUser=" + replyFromUser +
                ", replyToUser=" + replyToUser +
                ", content='" + content + '\'' +
                ", clap=" + clap +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}
