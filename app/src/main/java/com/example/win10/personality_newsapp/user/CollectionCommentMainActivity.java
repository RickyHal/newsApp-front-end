package com.example.win10.personality_newsapp.user;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.collection.CollectionActivity;
import com.example.win10.personality_newsapp.comment.CommentActivity;

public class CollectionCommentMainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_comment_main);

        TabHost tabHost =getTabHost();
        TabHost.TabSpec spec;
        TabWidget tabWidget = tabHost.getTabWidget();
//

        Intent intentcollection = new Intent();//用于跳转
        intentcollection.setClass(this, CollectionActivity.class);
        intentcollection .putExtra("user_id",getIntent().getStringExtra("user_id"));
        spec = tabHost.newTabSpec("collection").setIndicator("收藏", null).setContent(intentcollection);

       tabHost.addTab(spec);//添加Tab

        Intent intentcomment = new Intent();
        intentcomment.setClass(this, CommentActivity.class);
//        intentcomment .putExtra("user_id",getIntent().getStringExtra("user_id"));
        spec = tabHost.newTabSpec("comment").setIndicator("评论", null).setContent(intentcomment);
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

