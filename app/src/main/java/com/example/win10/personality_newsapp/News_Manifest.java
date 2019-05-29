package com.example.win10.personality_newsapp;

import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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

public class News_Manifest extends AppCompatActivity {

    public static ArrayList<news_item> newslist;
    ListView lv;

    MyAdapter myAdapter;
    RequestQueue requestQueue ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_main);
        lv=(ListView) findViewById(R.id.list);
        requestQueue= Volley.newRequestQueue(this);
        newslist= new ArrayList<news_item>();
        obtainData();
        myAdapter=new MyAdapter(this,requestQueue,newslist);
        lv.setAdapter(myAdapter);

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
                    "http://120.77.144.237/getNewsList/", null, new Response.Listener<JSONObject>() {
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
}
