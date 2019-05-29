package com.example.win10.personality_newsapp;

import java.util.ArrayList;
import java.util.List;

public class news_item {
    private String from;
    private String _id;
    private List<String> img =new ArrayList<String>();
    private String title;
    private String tag;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }



    public List<String> getImg() {
        return img;
    }


}
