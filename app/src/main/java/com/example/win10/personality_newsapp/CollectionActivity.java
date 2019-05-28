package com.example.win10.personality_newsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionActivity extends AppCompatActivity {
    private List<Map<String,Object>> list;
    SimpleAdapter simpleAdapter;

    private List<Map<String,Object>> putData(){

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("author", "腾讯新闻");
        map1.put("time", "2019-05-25");
        map1.put("title", "习近平：稀土是重要的战略资源 要加大科技创新工作力度");
        list.add(map1);
        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("author", "新浪新闻");
        map2.put("time", "2019-06-23");
        map2.put("title", "“中科院研究生被杀案”：接风宴为何变成一场杀戮？");
        list.add(map2);
        Map<String,Object> map3 = new HashMap<String,Object>();
        map3.put("author", "人民网");
        map3.put("time", "2019-05-30");
        map3.put("title", "回答了：我国的芯片落后但我国有哪些技术领先或处于垄断地位？");
        list.add(map3);

        Map<String,Object> map4= new HashMap<String,Object>();
        map4.put("author", "教育网");
        map4.put("time", "2019-12-01");
        map4.put("title", "家长问：如何才能让孩子远离游戏，更加良好地学习呢？");
        list.add(map4);
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ListView listview = (ListView)findViewById(R.id.mylistview);
        this.list=putData();


        this.simpleAdapter = new SimpleAdapter(this,this.list,R.layout.list_item,
                new String[]{"author","time","title"},new int[]{R.id.author,R.id.time,R.id.title});
        listview.setAdapter(simpleAdapter);
        listview.setEmptyView((TextView)findViewById(R.id.collectionnovalue));//设置当ListView为空的时候显示text_tip "暂无数据"

//        删除全部收藏记录
        Button deleteall=(Button)findViewById(R.id.deleteall_collection);
        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder bb=new AlertDialog.Builder(CollectionActivity.this);
                if(CollectionActivity.this.list.size()<=0){
                    bb.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bb.setMessage("您还没收藏新闻哦");
                }else{
                    bb.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CollectionActivity.this.list.clear();
                            simpleAdapter.notifyDataSetChanged();
                            Toast.makeText(getBaseContext(), "成功删除全部收藏", Toast.LENGTH_SHORT).show();
                        }
                    });
                    bb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bb.setMessage("您确定要删除全部收藏吗？");
                }
                bb.setTitle("提示");
                bb.show();
            }
        });


//        每项的点击跳转事件监听
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

//        每项的长按删除时间监听
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean  onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder bb=new AlertDialog.Builder(CollectionActivity.this);
                bb.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(CollectionActivity.this.list.remove(position)!=null){
                            System.out.println("success");
                        }else {
                            System.out.println("failed");
                        }
                        simpleAdapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                bb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                bb.setMessage("您确定要删除该条收藏吗？");
                bb.setTitle("提示");
                bb.show();
                return true;
            }
        });


    }

}
