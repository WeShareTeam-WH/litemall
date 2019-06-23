package org.linlinjava.litemall.wx.vo;
import org.linlinjava.litemall.db.domain.UserVo;

import java.time.LocalDateTime;
import java.util.List;

public class DynamicCommentVo {
    private Integer id;
    
    private Integer dynamicId;
    
    private UserVo user;
    
    private String content;
    
    private Integer clap;
    
    private LocalDateTime addTime;
    
    private LocalDateTime updateTime;
    
    private Boolean deleted;

    private List<DynamicCommentReplyVo> replys;

    private Integer replysCount;

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

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
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

    public List<DynamicCommentReplyVo> getReplys() {
        return replys;
    }

    public void setReplys(List<DynamicCommentReplyVo> replys) {
        this.replys = replys;
    }

    public Integer getReplysCount() {
        return replysCount;
    }

    public void setReplysCount(Integer replysCount) {
        this.replysCount = replysCount;
    }

    @Override
    public String toString() {
        return "DynamicCommentVo{" +
                "id=" + id +
                ", dynamicId=" + dynamicId +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", clap=" + clap +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                ", replys=" + replys +
                ", replysCount=" + replysCount +
                '}';
    }
}
