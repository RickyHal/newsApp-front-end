package com.example.win10.personality_newsapp.news_visit;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.user.Myapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class News_Manifest extends AppCompatActivity implements LoadListView.ILoadListerner {

    public static ArrayList<news_item> newslist;
    LoadListView lv;

    TextView search_bar;
    int classification;
    MyAdapter myAdapter;
    RequestQueue requestQueue ;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView news_id;
    static int recommendPage=0;
    Myapp myapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_main);
        search_bar=findViewById(R.id.search);
        textView1=findViewById(R.id.text1);
        textView2=findViewById(R.id.text2);
        textView3=findViewById(R.id.text3);
        textView4=findViewById(R.id.text4);
        textView5=findViewById(R.id.text5);
        textView6=findViewById(R.id.text6);
        textView7=findViewById(R.id.text7);
        textView1.setTextColor(0xFFFF0000);
        textView1.setTypeface(null, Typeface.BOLD);
        textView2.setTextColor(0xFF444444);
        textView3.setTextColor(0xFF444444);
        textView4.setTextColor(0xFF444444);

        textView5.setTextColor(0xFF444444);
        textView6.setTextColor(0xFF444444);
        textView7.setTextColor(0xFF444444);
        classification=0;
        myapp=(Myapp) getApplication();
        lv=(LoadListView) findViewById(R.id.list);
        lv.setInterface(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                news_id=(TextView) view.findViewById(R.id.newsid);
                Intent intent = new Intent();
                intent.putExtra("news_id", news_id.getText().toString());

                intent.setClass(News_Manifest.this,NewsDetailActivity.class);
                News_Manifest.this.startActivity(intent);

            }
        });
        requestQueue= Volley.newRequestQueue(this);
        newslist= new ArrayList<news_item>();

        obtainData();


        myAdapter=new MyAdapter(this,requestQueue,newslist);
        lv.setAdapter(myAdapter);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView1.setTextColor(0xFFFF0000);
                textView1.setTypeface(null, Typeface.BOLD);
                textView2.setTextColor(0xFF444444);
                textView2.setTypeface(null, Typeface.NORMAL);
                textView3.setTextColor(0xFF444444);
                textView3.setTypeface(null, Typeface.NORMAL);
                textView4.setTextColor(0xFF444444);
                textView4.setTypeface(null, Typeface.NORMAL);
                textView5.setTextColor(0xFF444444);
                textView5.setTypeface(null, Typeface.NORMAL);
                textView6.setTextColor(0xFF444444);
                textView6.setTypeface(null, Typeface.NORMAL);
                textView7.setTextColor(0xFF444444);
                textView7.setTypeface(null, Typeface.NORMAL);
                newslist.clear();
                myAdapter.notifyDataSetChanged();
                classification=0;
                obtainData();

            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView2.setTextColor(0xFFFF0000);
                textView2.setTypeface(null, Typeface.BOLD);
                textView1.setTextColor(0xFF444444);
                textView1.setTypeface(null, Typeface.NORMAL);
                textView3.setTextColor(0xFF444444);
                textView3.setTypeface(null, Typeface.NORMAL);
                textView4.setTextColor(0xFF444444);
                textView4.setTypeface(null, Typeface.NORMAL);
                textView5.setTextColor(0xFF444444);
                textView5.setTypeface(null, Typeface.NORMAL);
                textView6.setTextColor(0xFF444444);
                textView6.setTypeface(null, Typeface.NORMAL);
                textView7.setTextColor(0xFF444444);
                textView7.setTypeface(null, Typeface.NORMAL);

                if(myapp.getUser_id()==null){

                    AlertDialog.Builder builder = new AlertDialog.Builder(News_Manifest.this);

                    builder.setTitle("提示");
                    builder.setMessage("请先登录！");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    //一样要show
                    builder.show();
                }else{
                    recommendPage=0;
                    obtainRecommendData(myapp.getUser_id().toString(),String.valueOf(recommendPage));
                }

            }

        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView3.setTextColor(0xFFFF0000);
                textView3.setTypeface(null, Typeface.BOLD);
                textView2.setTextColor(0xFF444444);
                textView2.setTypeface(null, Typeface.NORMAL);
                textView1.setTextColor(0xFF444444);
                textView1.setTypeface(null, Typeface.NORMAL);
                textView4.setTextColor(0xFF444444);
                textView4.setTypeface(null, Typeface.NORMAL);
                textView5.setTextColor(0xFF444444);
                textView5.setTypeface(null, Typeface.NORMAL);
                textView6.setTextColor(0xFF444444);
                textView6.setTypeface(null, Typeface.NORMAL);
                textView7.setTextColor(0xFF444444);
                textView7.setTypeface(null, Typeface.NORMAL);
                newslist.clear();
                myAdapter.notifyDataSetChanged();
                classification=2;
                obtainClassifyData("科技");

            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView4.setTextColor(0xFFFF0000);
                textView4.setTypeface(null, Typeface.BOLD);
                textView2.setTextColor(0xFF444444);
                textView2.setTypeface(null, Typeface.NORMAL);
                textView3.setTextColor(0xFF444444);
                textView3.setTypeface(null, Typeface.NORMAL);
                textView1.setTextColor(0xFF444444);
                textView1.setTypeface(null, Typeface.NORMAL);
                textView5.setTextColor(0xFF444444);
                textView5.setTypeface(null, Typeface.NORMAL);
                textView6.setTextColor(0xFF444444);
                textView6.setTypeface(null, Typeface.NORMAL);
                textView7.setTextColor(0xFF444444);
                textView7.setTypeface(null, Typeface.NORMAL);
                newslist.clear();
                myAdapter.notifyDataSetChanged();
                classification=3;
                obtainClassifyData("体育");

            }
        });
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView5.setTextColor(0xFFFF0000);
                textView5.setTypeface(null, Typeface.BOLD);
                textView2.setTextColor(0xFF444444);
                textView2.setTypeface(null, Typeface.NORMAL);
                textView3.setTextColor(0xFF444444);
                textView3.setTypeface(null, Typeface.NORMAL);
                textView4.setTextColor(0xFF444444);
                textView4.setTypeface(null, Typeface.NORMAL);
                textView1.setTextColor(0xFF444444);
                textView1.setTypeface(null, Typeface.NORMAL);
                textView6.setTextColor(0xFF444444);
                textView6.setTypeface(null, Typeface.NORMAL);
                textView7.setTextColor(0xFF444444);
                textView7.setTypeface(null, Typeface.NORMAL);
                newslist.clear();
                myAdapter.notifyDataSetChanged();
                classification=4;
                obtainClassifyData("娱乐");
            }
        });
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView6.setTextColor(0xFFFF0000);
                textView6.setTypeface(null, Typeface.BOLD);
                textView2.setTextColor(0xFF444444);
                textView2.setTypeface(null, Typeface.NORMAL);
                textView3.setTextColor(0xFF444444);
                textView3.setTypeface(null, Typeface.NORMAL);
                textView4.setTextColor(0xFF444444);
                textView4.setTypeface(null, Typeface.NORMAL);
                textView5.setTextColor(0xFF444444);
                textView5.setTypeface(null, Typeface.NORMAL);
                textView1.setTextColor(0xFF444444);
                textView1.setTypeface(null, Typeface.NORMAL);
                textView7.setTextColor(0xFF444444);
                textView7.setTypeface(null, Typeface.NORMAL);
                newslist.clear();
                myAdapter.notifyDataSetChanged();
                classification=5;
                obtainClassifyData("财经");
            }
        });
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView7.setTextColor(0xFFFF0000);
                textView7.setTypeface(null, Typeface.BOLD);
                textView2.setTextColor(0xFF444444);
                textView2.setTypeface(null, Typeface.NORMAL);
                textView3.setTextColor(0xFF444444);
                textView3.setTypeface(null, Typeface.NORMAL);
                textView4.setTextColor(0xFF444444);
                textView4.setTypeface(null, Typeface.NORMAL);
                textView5.setTextColor(0xFF444444);
                textView5.setTypeface(null, Typeface.NORMAL);
                textView6.setTextColor(0xFF444444);
                textView6.setTypeface(null, Typeface.NORMAL);
                textView1.setTextColor(0xFF444444);
                textView1.setTypeface(null, Typeface.NORMAL);
                newslist.clear();
                myAdapter.notifyDataSetChanged();
                classification=6;
                obtainClassifyData("军事");
            }
        });
        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setClass(News_Manifest.this,Search_viewList.class);
                News_Manifest.this.startActivity(intent);
            }
        });
    }



    public void obtainData() {

        try {

            /*RequestQueue mQueue = Volley.newRequestQueue(News_Manifest.this);
            JsonArrayRequest jsonArray = new JsonArrayRequest("http://120.77.144.237/getNewsList/",
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                           // Log.d("Tag:", response.toString());

                            Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.e("Error: ", error.getMessage());
                    Toast.makeText(getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
                }
            });

            mQueue.add(jsonArray);*/

            // String jsonUrl = "http://152.123.55.102:8080/json.html";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getNewsList/", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {

                        //newslist= new ArrayList<news_item>();
                        JSONArray data = response.getJSONArray("data");
                        Integer code= (Integer)response.get("code");
                        if (code==0){
                            for (int i=0;i<data.length();i++){
                                news_item newsitem=new news_item();
                                JSONObject item=data.getJSONObject(i);
                                Log.d("11",item.getString("imageurls"));
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

                        }
                        myAdapter.notifyDataSetChanged();
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

    @Override
    public void onload() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (classification){
                    case 0:obtainMoreData();break;
                    case 1:
                        if(myapp.getUser_id()==null){

                            AlertDialog.Builder builder = new AlertDialog.Builder(News_Manifest.this);

                            builder.setTitle("提示");
                            builder.setMessage("请先登录！");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            //一样要show
                            builder.show();
                        }else{
                            recommendPage++;
                            obtainMoreRecommendData(myapp.getUser_id().toString(),String.valueOf(recommendPage));
                        }
                        break;
                    case 2:obtainMoreClassifyData("科技");break;
                    case 3:obtainMoreClassifyData("体育");break;
                    case 4:obtainMoreClassifyData("娱乐");break;
                    case 5:obtainMoreClassifyData("财经");break;
                    case 6:obtainMoreClassifyData("军事");break;
                }

                lv.LoadingComplete();
            }
        },1000);

    }
    public void obtainMoreData() {

        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getNewsList/", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        //newslist= new ArrayList<news_item>();
                        JSONArray data = response.getJSONArray("data");
                        Integer code= (Integer)response.get("code");
                        if (code==0){
                            for (int i=0;i<6;i++){
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

                        }
                        myAdapter.notifyDataSetChanged();
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
    public void obtainClassifyData(String para) {

        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getNewsList/?news_type="+para, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        //newslist= new ArrayList<news_item>();
                        JSONArray data = response.getJSONArray("data");
                        Integer code= (Integer)response.get("code");
                        if (code==0){
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

                        }
                        myAdapter.notifyDataSetChanged();
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
    public void obtainMoreClassifyData(String para) {

        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getNewsList/?news_type="+para, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        //newslist= new ArrayList<news_item>();
                        JSONArray data = response.getJSONArray("data");
                        Integer code= (Integer)response.get("code");
                        if (code==0){
                            for (int i=0;i<6;i++){
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

                        }
                        myAdapter.notifyDataSetChanged();
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

    public void obtainRecommendData(String userID,String page) {

        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getRecommendList/?user_id="+userID+"&page="+page, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        //newslist= new ArrayList<news_item>();
                        JSONArray data = response.getJSONArray("data");
                        Integer code= (Integer)response.get("code");
                        if (code==0){
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

                        }
                        myAdapter.notifyDataSetChanged();

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
    public void obtainMoreRecommendData(String userID,String page) {

        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getRecommendList/?user_id="+userID+"&page="+page, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        //newslist= new ArrayList<news_item>();
                        JSONArray data = response.getJSONArray("data");
                        Integer code= (Integer)response.get("code");
                        if (code==0){
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

                        }
                        myAdapter.notifyDataSetChanged();

                    }catch (JSONException e){
                        Toast.makeText(getApplicationContext(),"已经到底了",Toast.LENGTH_LONG).show();
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