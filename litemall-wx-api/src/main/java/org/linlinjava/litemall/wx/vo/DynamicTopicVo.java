package org.linlinjava.litemall.wx.vo;

public class DynamicTopicVo {
    private Integer topicId;
    private Integer dynamicId;
    private String title;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer id) {
        this.topicId = id;
    }

    public Integer getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "DynamicTopicVo{" +
                "topicId=" + topicId +
                ", dynamicId=" + dynamicId +
                ", title='" + title + '\'' +
                '}';
    }
}
