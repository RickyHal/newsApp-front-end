package com.example.win10.personality_newsapp.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.win10.personality_newsapp.R;

import java.util.ArrayList;
import java.util.List;

public class userFragment extends Fragment {
    private List<Setting> settingList=new ArrayList<>();
    private DividerItemDecoration mDividerItemDecoration;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.unlogin_user_view,container,false);
        View view = View.inflate(getActivity(),R.layout.unlogin_user_view,null);
        Button b=(Button) view.findViewById(R.id.icon_login);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        initSetting();  //初始化用户设置列表
        RecyclerView recyclerView =(RecyclerView) view.findViewById(R.id.recycler_user) ;
        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false){
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

       /* //隐藏标题栏
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }*/
        return view;
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
