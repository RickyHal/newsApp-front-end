package com.example.win10.personality_newsapp.collection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.news_visit.NewsDetailActivity;
import com.example.win10.personality_newsapp.news_visit.News_Manifest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    private void putData(String user_id){
        final List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try {
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            Log.d("22","http://120.77.144.237/app/getCollectRec/?user_id="+user_id);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getCollectRec/?user_id="+user_id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray data = response.getJSONArray("data");
                        Integer code= (Integer)response.get("code");
                        if(code==0){
                            for (int i=0;i<data.length();i++){
                                JSONObject oneitem=data.getJSONObject(i).getJSONArray("news_info").getJSONObject(0);
                                Map<String,Object> map = new HashMap<String,Object>();
                                map.put("author",oneitem.getString("tag")+" "+oneitem.getString("from"));
                                map.put("time",timestampToDate(oneitem.getLong("timestamp")));
                                map.put("title",oneitem.getString("title"));
                                map.put("_id",oneitem.getLong("_id"));
                                list.add(map);
                            }
                           // 给listview赋值
                            CollectionActivity.this.list=list;
                            ListView listview = (ListView)findViewById(R.id.mylistview);
                            CollectionActivity.this.simpleAdapter = new SimpleAdapter(CollectionActivity.this,
                                    CollectionActivity.this.list,
                                    R.layout.list_item,
                                    new String[]{"author","time","title"},
                                    new int[]{R.id.author,R.id.time,R.id.title});
                            listview.setAdapter(simpleAdapter);

                        }else{
                            Toast.makeText(CollectionActivity.this.getApplicationContext(),"请求失败。",Toast.LENGTH_LONG).show();
                        }
                    }catch (JSONException e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CollectionActivity.this.getApplicationContext(),"网络异常，请重试",Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ListView listview = (ListView)findViewById(R.id.mylistview);

        putData(getIntent().getStringExtra("user_id"));
        listview.setEmptyView((TextView)findViewById(R.id.collectionnovalue));

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
                            RequestQueue requestQueue= Volley.newRequestQueue(CollectionActivity.this);
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                    "http://120.77.144.237/app/deleteAllCollect?user_id="+getIntent().getStringExtra("user_id"),
                                    null, new Response.Listener<JSONObject>() {
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(getBaseContext(), "成功删除全部收藏", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getBaseContext(), "出现网络问题", Toast.LENGTH_SHORT).show();
                                }
                            });
                            requestQueue.add(jsonObjectRequest);
                            CollectionActivity.this.list.clear();
                            simpleAdapter.notifyDataSetChanged();
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

                Intent intent = new Intent();
                intent.putExtra("news_id",list.get(position).get("_id").toString());
                intent.setClass(CollectionActivity.this, NewsDetailActivity.class);
                CollectionActivity.this.startActivity(intent);

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
                        RequestQueue requestQueue= Volley.newRequestQueue(CollectionActivity.this);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                "http://120.77.144.237/app/deleteCollectRec/?user_id="+getIntent().getStringExtra("user_id")
                                        +"&_id="+CollectionActivity.this.list.get(position).get("_id"),
                                null, new Response.Listener<JSONObject>() {
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getBaseContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getBaseContext(), "出现网络问题", Toast.LENGTH_SHORT).show();
                            }
                        });
                        requestQueue.add(jsonObjectRequest);
                        if(CollectionActivity.this.list.remove(position)!=null){
                            System.out.println("success");
                        }else {
                            System.out.println("failed");
                        }
                        simpleAdapter.notifyDataSetChanged();
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

