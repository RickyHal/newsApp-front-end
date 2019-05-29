package com.example.win10.personality_newsapp.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.TestAddCommentActivity;
import com.example.win10.personality_newsapp.comment.CommentActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginedActivity extends AppCompatActivity {

    private List<Setting> settingList=new ArrayList<>();
    private DividerItemDecoration mDividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user_view);

        initSetting();  //初始化用户设置列表
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.recycler_user) ;
        LinearLayoutManager layoutManager =new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        SettingAdapter adapter =new SettingAdapter(settingList);
        recyclerView.setAdapter(adapter);
        TextView mycollection=(TextView)findViewById(R.id.my_collection);
        mycollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("user_id", "1");//设置参数,""
                intent.setClass(LoginedActivity.this, CollectionCommentMainActivity.class);//从哪里跳到哪里
                startActivity(intent);
            }
        });

    }

    private void initSetting(){
        for(int i=0;i<1;i++){
            /*Setting list1 = new Setting("我的关注",R.drawable.next);
            settingList.add(list1);
            Setting list2 = new Setting("我的钱包",R.drawable.next);
            settingList.add(list2);
            Setting list3= new Setting("消息通知",R.drawable.next);
            settingList.add(list3);
            Setting list4 = new Setting("扫一扫",R.drawable.next);
            settingList.add(list4);
            Setting list5 = new Setting("阅读公益",R.drawable.next);
            settingList.add(list5);
            Setting list6 = new Setting("用户反馈",R.drawable.next);
            settingList.add(list6);*/
            Setting list7= new Setting("系统设置",R.drawable.next);
            settingList.add(list7);
        }
    }
}
