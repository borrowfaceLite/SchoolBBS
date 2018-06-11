package com.hwmlygr.ground.schoolbbs;

/**
 * Created by yt on 2018/6/11.
 */

public class Comment {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Comment(String userName, int userId, String content, String time) {
        this.userName = userName;
        this.userId = userId;
        this.content = content;
        this.time = time;
    }

    private String userName;
    private int userId;
    private String content;
    private String time;

}
