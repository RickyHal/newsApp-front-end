package com.example.win10.personality_newsapp.user;

public class Setting {
    private String name;
    private int imageId;

    public Setting(String name, int imageId){
        this.name=name;
        this.imageId=imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
