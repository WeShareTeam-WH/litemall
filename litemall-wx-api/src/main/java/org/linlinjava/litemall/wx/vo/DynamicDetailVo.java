package org.linlinjava.litemall.wx.vo;
import org.linlinjava.litemall.db.domain.UserVo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicDetailVo {
    private Integer id;
    private UserVo ower;
    private String title;
    private String content;
    private Integer clap;
    private LocalDateTime addTime;
    private LocalDateTime updateTime;
    private Boolean deleted;
    private String[] picture;
    private List<DynamicCommentVo> comments;
    private Integer commentsCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserVo getOwer() {
        return ower;
    }

    public void setOwer(UserVo ower) {
        this.ower = ower;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String[] getPicture() {
        return picture;
    }

    public void setPicture(String[] picture) {
        this.picture = picture;
    }

    public List<DynamicCommentVo> getComments() {
        return comments;
    }

    public void setComments(List<DynamicCommentVo> comments) {
        this.comments = comments;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    @Override
    public String toString() {
        return "DynamicDetailVo{" +
                "id=" + id +
                ", ower=" + ower +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", clap=" + clap +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                ", picture=" + Arrays.toString(picture) +
                ", comments=" + comments +
                ", commentsCount=" + commentsCount +
                '}';
    }
}
