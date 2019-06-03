package com.example.win10.personality_newsapp.news_visit;


import android.graphics.Typeface;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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

    MyAdapter myAdapter;
    RequestQueue requestQueue;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_main);
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);
        textView4 = (TextView) findViewById(R.id.text4);
        textView5 = (TextView) findViewById(R.id.text5);
        textView6 = (TextView) findViewById(R.id.text6);
        textView7 = (TextView) findViewById(R.id.text7);
        textView1.setTextColor(0xFFFF0000);
        textView1.setTypeface(null, Typeface.BOLD);
        textView2.setTextColor(0xFF444444);
        textView3.setTextColor(0xFF444444);
        textView4.setTextColor(0xFF444444);

        textView5.setTextColor(0xFF444444);
        textView6.setTextColor(0xFF444444);
        textView7.setTextColor(0xFF444444);
        lv = (LoadListView) findViewById(R.id.list);
        lv.setInterface(this);
        requestQueue = Volley.newRequestQueue(this);
        newslist = new ArrayList<news_item>();
        obtainData();
        myAdapter = new MyAdapter(this, requestQueue, newslist);
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
                        Integer code = (Integer) response.get("code");
                        if (code == 0) {
                            for (int i = 0; i < data.length(); i++) {
                                news_item newsitem = new news_item();
                                JSONObject item = data.getJSONObject(i);
                                newsitem.setFrom(item.getString("from"));
                                newsitem.set_id(item.getString("_id"));
                                newsitem.setTitle(item.getString("title"));
                                newsitem.setTag(item.getString("tag"));
                                JSONArray jsonArray = item.getJSONArray("imageurls");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    newsitem.getImg().add((String) jsonArray.get(j));
                                }
                                newslist.add(newsitem);
                            }

                        }
                        myAdapter.notifyDataSetChanged();
                        //String size=Integer.toString(newslist.size());
                        //Toast.makeText(getApplicationContext(),size,Toast.LENGTH_LONG).show();

                        //Toast.makeText(getApplicationContext(),"加载完成",Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onload() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                obtainMoreData();
                lv.LoadingComplete();
            }
        }, 1000);

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
                        Integer code = (Integer) response.get("code");
                        if (code == 0) {
                            for (int i = 0; i < 6; i++) {
                                news_item newsitem = new news_item();
                                JSONObject item = data.getJSONObject(i);
                                newsitem.setFrom(item.getString("from"));
                                newsitem.set_id(item.getString("_id"));
                                newsitem.setTitle(item.getString("title"));
                                newsitem.setTag(item.getString("tag"));
                                JSONArray jsonArray = item.getJSONArray("imageurls");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    newsitem.getImg().add((String) jsonArray.get(j));
                                }
                                newslist.add(newsitem);
                            }

                        }
                        myAdapter.notifyDataSetChanged();
                        //String size=Integer.toString(newslist.size());
                        //Toast.makeText(getApplicationContext(),size,Toast.LENGTH_LONG).show();

                        //Toast.makeText(getApplicationContext(),"加载完成",Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
