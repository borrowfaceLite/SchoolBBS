package com.hwmlygr.ground.schoolbbs.bean;

/**
 * Created by MA on 2018/6/12.
 */

public class TopicInfo {
    int topicId;
    String topicName;
    String topicCategory;
    String topicContent;
    long topicUploadTime;

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicCategory() {
        return topicCategory;
    }

    public void setTopicCategory(String topicCategory) {
        this.topicCategory = topicCategory;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public long getTopicUploadTime() {
        return topicUploadTime;
    }

    public void setTopicUploadTime(long topicUploadTime) {
        this.topicUploadTime = topicUploadTime;
    }
}
