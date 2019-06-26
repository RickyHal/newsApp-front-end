package com.example.win10.personality_newsapp.news_visit;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.user.Myapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class search_result extends Activity implements LoadListView.ILoadListerner{

    public static ArrayList<news_item> newslist;
    LoadListView lv;
    String search;

    static  int page=0;
    MyAdapter myAdapter;
    RequestQueue requestQueue ;
    Myapp myapp;
    TextView news_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        myapp=(Myapp) getApplication();
        lv=findViewById(R.id.searchresultlist);
        lv.setInterface(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                news_id=(TextView) view.findViewById(R.id.newsid);
                Intent intent = new Intent();
                intent.putExtra("news_id", news_id.getText().toString());

                intent.setClass(search_result.this,NewsDetailActivity.class);
                search_result.this.startActivity(intent);

            }
        });
        requestQueue= Volley.newRequestQueue(this);
        newslist= new ArrayList<news_item>();
        Intent intent = getIntent();
        search= intent.getStringExtra("searchcontent");

        if(myapp.getUser_id()==null){

            obtainSearchData(search,"-1",page);

        }else{
            obtainSearchData(search,myapp.getUser_id().toString(),page);
        }



        myAdapter=new MyAdapter(this,requestQueue,newslist);
        lv.setAdapter(myAdapter);



    }




    public void onload() {
        page++;
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(newslist.size()<10){
                    Toast.makeText(getApplicationContext(),"没有更多了",Toast.LENGTH_LONG).show();
                }else{
                    if(myapp.getUser_id()==null){

                        obtainSearchData(search,"-1",page);

                    }else{
                        obtainSearchData(search,myapp.getUser_id().toString(),page);
                    }
                }

                lv.LoadingComplete();
            }
        },1000);
    }



    public void obtainSearchData(String para,String userid,Integer page) {

        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/searchNews/?keyword="+para+"&user_id="+userid+"&page="+page, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        //newslist= new ArrayList<news_item>();
                        JSONArray data = response.getJSONArray("data");
                        Integer code= (Integer)response.get("code");
                        if (code==0&&data.length()>0){
                            for (int i=0;i<data.length();i++){
                                news_item newsitem=new news_item();
                                JSONObject item=data.getJSONObject(i);
                                newsitem.setFrom(item.getString("from"));
                                newsitem.set_id(item.getString("_id"));
                                newsitem.setTitle(item.getString("title"));
                                newsitem.setTag(item.getString("tag"));
                                newsitem.setTimestamp(item.getString("timestamp"));
                                JSONArray jsonArray = item.getJSONArray("imageurls");
                                for(int j=0;j<jsonArray.length();j++){
                                    newsitem.getImg().add((String)jsonArray.get(j));
                                }
                                newslist.add(newsitem);
                            }

                            myAdapter.notifyDataSetChanged();
                        }else{
                            if(code==1) {
                                Toast.makeText(getApplicationContext(), "未找到相关内容！", Toast.LENGTH_SHORT).show();
                            }else if(data.length()<=0){
                                Toast.makeText(getApplicationContext(), "没有更多了", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "未知数据！", Toast.LENGTH_SHORT).show();
                            }
                        }
                        //String size=Integer.toString(newslist.size());
                        //Toast.makeText(getApplicationContext(),size,Toast.LENGTH_LONG).show();

                        //Toast.makeText(getApplicationContext(),"加载完成",Toast.LENGTH_LONG).show();
                    }catch (JSONException e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

