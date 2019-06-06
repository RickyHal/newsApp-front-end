package com.example.win10.personality_newsapp.comment;

public class CommentBean {
    private String headpictureurl;
    private String nickname;
    private String release_time;
    private String comment_content;
    private String news_item;
    private int _id;
    private long new_id;



    public CommentBean(String headpictureurl, String nickname, String release_time, String comment_content, String news_item, int _id, long new_id) {
        this.headpictureurl = headpictureurl;
        this.nickname = nickname;
        this.release_time = release_time;
        this.comment_content = comment_content;
        this.news_item = news_item;
        this._id = _id;
        this.new_id = new_id;
    }

    public CommentBean() {
    }
    public long getNew_id() {
        return new_id;
    }

    public void setNew_id(long new_id) {
        this.new_id = new_id;
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public String getNews_item() {
        return news_item;
    }

    public void setNews_item(String news_item) {
        this.news_item = news_item;
    }
}
