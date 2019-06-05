package com.example.win10.personality_newsapp.user;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.collection.CollectionActivity;
import com.example.win10.personality_newsapp.comment.CommentActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        HashMap<String, String> user=(HashMap<String,String>)getIntent().getSerializableExtra("user");
        intentcollection .putExtra("user_id",user.get("user_id"));
        spec = tabHost.newTabSpec("collection").setIndicator("收藏", null).setContent(intentcollection);
        tabHost.addTab(spec);//添加Tab

        Intent intentcomment = new Intent();
        intentcomment.setClass(this, CommentActivity.class);
        Map<String,String> user_partinfo=new HashMap<String,String>();
        user_partinfo.put("user_id",user.get("user_id"));
        user_partinfo.put("user_name",user.get("user_name"));
        user_partinfo.put("user_avatar_url",user.get("user_avatar_url"));
        intentcomment.putExtra("user_partinfo", (Serializable)user_partinfo);
        spec = tabHost.newTabSpec("comment").setIndicator("评论", null).setContent(intentcomment);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(Integer.parseInt(user.get("open_page")));//选择默认的Tab


        for (int i =0; i < tabWidget.getChildCount(); i++) {
            //修改显示字体大小
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(15);
            tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
        }


    }


}
