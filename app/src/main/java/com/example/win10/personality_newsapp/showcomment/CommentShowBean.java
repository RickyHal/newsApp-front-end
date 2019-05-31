package com.example.win10.personality_newsapp.showcomment;

public class CommentShowBean {
    private String headpictureurl;
    private String nickname;
    private String release_time;
    private String comment_content;

    public CommentShowBean() {
    }

    public CommentShowBean(String headpictureurl, String nickname, String release_time, String comment_content) {
        this.headpictureurl = headpictureurl;
        this.nickname = nickname;
        this.release_time = release_time;
        this.comment_content = comment_content;
    }

    public String getHeadpictureurl() {
        return headpictureurl;
    }

    public void setHeadpictureurl(String headpictureurl) {
        this.headpictureurl = headpictureurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }
}
