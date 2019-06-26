package com.example.win10.personality_newsapp.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.MainActivity;
import com.example.win10.personality_newsapp.PropertyUri;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.news_visit.LoadListView;
import com.example.win10.personality_newsapp.news_visit.MyAdapter;
import com.example.win10.personality_newsapp.news_visit.NewsDetailActivity;
import com.example.win10.personality_newsapp.news_visit.News_Manifest;
import com.example.win10.personality_newsapp.news_visit.Search_viewList;
import com.example.win10.personality_newsapp.news_visit.news_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class homeFragment  extends Fragment implements LoadListView.ILoadListerner {
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
    static int newsPage=0;
    static int tecnologyPage=0;
    static int economyPage=0;
    static int entertainPage=0;
    static int sportPage=0;
    static int militaryPage=0;
    Myapp myapp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.news_main,container,false);
        View view = View.inflate(getActivity(),R.layout.news_main,null);

        search_bar=view.findViewById(R.id.search);
        textView1=view.findViewById(R.id.text1);
        textView2=view.findViewById(R.id.text2);
        textView3=view.findViewById(R.id.text3);
        textView4=view.findViewById(R.id.text4);
        textView5=view.findViewById(R.id.text5);
        textView6=view.findViewById(R.id.text6);
        textView7=view.findViewById(R.id.text7);
        textView1.setTextColor(0xFFFF0000);
        textView1.setTypeface(null, Typeface.BOLD);
        textView2.setTextColor(0xFF444444);
        textView3.setTextColor(0xFF444444);
        textView4.setTextColor(0xFF444444);

        textView5.setTextColor(0xFF444444);
        textView6.setTextColor(0xFF444444);
        textView7.setTextColor(0xFF444444);
        classification=0;
        myapp=(Myapp) getActivity().getApplication();

        lv=(LoadListView) view.findViewById(R.id.list);
        lv.setInterface(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                news_id=(TextView) view.findViewById(R.id.newsid);
                Intent intent = new Intent();
                intent.putExtra("news_id", news_id.getText().toString());

                intent.setClass(getActivity(), NewsDetailActivity.class);
               startActivity(intent);

            }
        });
        requestQueue= Volley.newRequestQueue(getActivity());
        newslist= new ArrayList<news_item>();

        obtainData(newsPage);


        myAdapter=new MyAdapter(getActivity(),requestQueue,newslist);
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
                newsPage=0;
                obtainData(newsPage);

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
                newslist.clear();
                myAdapter.notifyDataSetChanged();
                classification=1;
                if(myapp.getUser_id()==null){

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
                tecnologyPage=0;
                obtainClassifyData("科技",tecnologyPage);

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
                sportPage=0;
                obtainClassifyData("体育",sportPage);

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
                entertainPage=0;
                obtainClassifyData("娱乐",entertainPage);
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
                economyPage=0;
                obtainClassifyData("财经",economyPage);
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
                militaryPage=0;
                obtainClassifyData("军事",militaryPage);
            }
        });
        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setClass(getActivity().getApplicationContext(), Search_viewList.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    public void obtainData(int page) {

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
                    new PropertyUri().host+"app/getNewsList/?page="+page, null, new Response.Listener<JSONObject>() {
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
                        Toast.makeText(getActivity().getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
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
                    case 0:newsPage++;obtainMoreData(newsPage);break;
                    case 1:if(myapp.getUser_id()==null){

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
                    case 2:tecnologyPage++;obtainMoreClassifyData("科技",tecnologyPage);break;
                    case 3:sportPage++;obtainMoreClassifyData("体育",sportPage);break;
                    case 4:entertainPage++;obtainMoreClassifyData("娱乐",entertainPage);break;
                    case 5:economyPage++;obtainMoreClassifyData("财经",economyPage);break;
                    case 6:militaryPage++;obtainMoreClassifyData("军事",militaryPage);break;
                }

                lv.LoadingComplete();
            }
        },1000);

    }
    public void obtainMoreData(int page) {

        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    new PropertyUri().host+"app/getNewsList/?page="+page, null, new Response.Listener<JSONObject>() {
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
                        Toast.makeText(getActivity().getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void obtainClassifyData(String para,int activitypage) {

        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    new PropertyUri().host+"app/getNewsList/?news_type="+para+"&page="+activitypage, null, new Response.Listener<JSONObject>() {
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
                        Toast.makeText(getActivity().getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void obtainMoreClassifyData(String para,int activitypage) {

        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    new PropertyUri().host+"app/getNewsList/?news_type="+para+"&page="+activitypage, null, new Response.Listener<JSONObject>() {
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
                        Toast.makeText(getActivity().getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
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
                    new PropertyUri().host+"app/getRecommendList/?user_id="+userID+"&page="+page, null, new Response.Listener<JSONObject>() {
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
                        Toast.makeText(getActivity().getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
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
                    new PropertyUri().host+"app/getRecommendList/?user_id="+userID+"&page="+page, null, new Response.Listener<JSONObject>() {
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
                        Toast.makeText(getActivity().getApplicationContext(),"已经到底了",Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
