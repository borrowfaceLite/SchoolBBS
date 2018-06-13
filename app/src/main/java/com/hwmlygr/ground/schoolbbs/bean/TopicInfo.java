package com.hwmlygr.ground.schoolbbs.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MA on 2018/6/12.
 */

public class TopicInfo implements Parcelable{
    int topicId;
    String topicName;
    String topicCategory;
    String topicContent;
    String topicUploadTime;

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

    public String getTopicUploadTime() {
        return topicUploadTime;
    }

    public void setTopicUploadTime(String topicUploadTime) {
        this.topicUploadTime = topicUploadTime;
    }

    public TopicInfo() {
    }

    private TopicInfo(Parcel parcel){
        this.topicId = parcel.readInt();
        this.topicName = parcel.readString();
        this.topicCategory = parcel.readString();
        this.topicContent = parcel.readString();
        this.topicUploadTime = parcel.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(topicId);
        dest.writeString(topicName);
        dest.writeString(topicCategory);
        dest.writeString(topicContent);
        dest.writeString(topicUploadTime);
    }
    public static final Parcelable.Creator<TopicInfo> CREATOR = new Parcelable.Creator<TopicInfo>(){

        @Override
        public TopicInfo createFromParcel(Parcel source) {
            return new TopicInfo(source);
        }

        @Override
        public TopicInfo[] newArray(int size) {
            return new TopicInfo[size];
        }
    };
}
