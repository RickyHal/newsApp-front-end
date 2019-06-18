package com.example.win10.personality_newsapp.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class loginedFragment extends Fragment {
    private Myapp myapp;
    private List<Setting> settingList=new ArrayList<>();
    private DividerItemDecoration mDividerItemDecoration;
    RequestQueue requestQueue;
    private SettingAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),R.layout.login_user_view,null);
         myapp = (Myapp)getActivity().getApplication();
        requestQueue= Volley.newRequestQueue(getActivity());
        TextView nickname=(TextView) view.findViewById(R.id.user_name);
        nickname.setText(myapp.getUser_name());
        CircleImageView p=(CircleImageView) view.findViewById(R.id.icon_image) ;
        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(p,
                R.drawable.ic_launcher,R.drawable.chahao);
        imageLoader.get(myapp.getUser_avatar_url(), listener);
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
       adapter =new SettingAdapter(settingList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MyItemClickListener);
        TextView mycomment=(TextView)view.findViewById(R.id.my_comment);
        mycomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Map<String,String> user=new HashMap<String,String>();

                user.put("open_page","1");
                user.put("user_id",myapp.getUser_id().toString());
                user.put("user_name",myapp.getUser_name());
                user.put("user_avatar_url",myapp.getUser_avatar_url());

                intent.putExtra("user", (Serializable)user);
                intent.setClass(getActivity(), CollectionCommentMainActivity.class);//从哪里跳到哪里
                startActivity(intent);
            }
        });
        TextView mycollection=(TextView)view.findViewById(R.id.my_collection);
        mycollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Map<String,String> user=new HashMap<String,String>();

                user.put("open_page","0");
                user.put("user_id",myapp.getUser_id().toString());
                user.put("user_name",myapp.getUser_name());
                user.put("user_avatar_url",myapp.getUser_avatar_url());

                intent.putExtra("user", (Serializable)user);
                intent.setClass(getActivity(), CollectionCommentMainActivity.class);//从哪里跳到哪里
                startActivity(intent);
            }
        });
        return view;
    }
    private void initSetting(){
        settingList.clear();
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

    private SettingAdapter.OnItemClickListener MyItemClickListener = new SettingAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, int position) {
            switch (v.getId()) {
                case R.id.sys_setting:
                    //对item进行判断如果是第一个那么我们进行跳转反之则提示消息
                    if(position==0) {//这里position用于判断item是第几个条目然后我们对其设置就可以跳转了。
                        Intent intent = new Intent(getActivity(),SystemSettingActivity.class);
                        intent.putExtra("login_setting",1);
                        startActivity(intent);
                    }
            }
        }

        @Override
        public void onItemLongClick(View v) {

        }


    };

}
