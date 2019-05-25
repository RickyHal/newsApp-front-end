package com.example.win10.personality_newsapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {


    private List<Map<String,Object>> putData(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ListView listview = (ListView)findViewById(R.id.mylist);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,putData(),R.layout.list_item,
                new String[]{"author","time","title"},new int[]{R.id.author,R.id.time,R.id.title});
        listview.setAdapter(simpleAdapter);
        listview.setEmptyView((TextView)findViewById(R.id.comentnovalue));//设置当ListView为空的时候显示text_tip "暂无数据"
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

}
