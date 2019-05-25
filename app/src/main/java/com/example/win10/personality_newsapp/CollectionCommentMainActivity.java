package com.example.win10.personality_newsapp;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.lang.reflect.Field;

public class CollectionCommentMainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_comment_main);

        TabHost tabHost =getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        TabWidget tabWidget = tabHost.getTabWidget();
        intent = new Intent().setClass(this, CollectionActivity.class);//用于跳转
        spec = tabHost.newTabSpec("collection").setIndicator("收藏", null).setContent(intent);
        tabHost.addTab(spec);//添加Tab

        intent = new Intent().setClass(this, CommentActivity.class);
        spec = tabHost.newTabSpec("comment").setIndicator("评论", null).setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);//选择默认的Tab


        for (int i =0; i < tabWidget.getChildCount(); i++) {
            //修改显示字体大小
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(15);

            tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
        }


}


}
