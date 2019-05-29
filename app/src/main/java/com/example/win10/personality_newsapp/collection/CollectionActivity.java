package com.example.win10.personality_newsapp.collection;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.MainActivity;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.news_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionActivity extends AppCompatActivity {
    private List<Map<String,Object>> list;
    SimpleAdapter simpleAdapter;

    private static String timestampToDate(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(time))));
        return sd;
    }

    private List<Map<String,Object>> putData(RequestQueue requestQueue,String user_id) {
            final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            //发送volley请求
            final String url="http://120.77.144.237/getCollectRec/?user_id=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {//jsonObject为请求返回的Json格式数据
                        Toast.makeText(CollectionActivity.this,jsonObject.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(CollectionActivity.this,volleyError.toString(),Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
        return list;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ListView listview = (ListView)findViewById(R.id.mylistview);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        String user_id=getIntent().getStringExtra("user_id");
        this.list=putData(requestQueue,"1");
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
