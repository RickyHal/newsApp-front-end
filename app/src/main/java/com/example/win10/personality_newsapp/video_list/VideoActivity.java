package com.example.win10.personality_newsapp.video_list;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.MainActivity;
import com.example.win10.personality_newsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoActivity extends AppCompatActivity implements LoadListView.ILoadListerner {
    public static ArrayList<VideoItem> videoList;
    MyAdapter myAdapter;
    RequestQueue requestQueue;
    LoadListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Button top = (Button) findViewById(R.id.toTop);
        lv = (LoadListView) findViewById(R.id.list);
        lv.setInterface(this);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setSelection(0);
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        videoList = new ArrayList<VideoItem>();
        RequestsData(false);
        myAdapter = new MyAdapter(this, requestQueue, videoList, this, lv);
        lv.setAdapter(myAdapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // 给bnt1添加点击响应事件
//                Intent intent =new Intent(VideoActivity.this, VideoDetailActivity.class);
//                intent.putExtra("video",videoList.get(position));
//                //启动
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            JCVideoPlayerStandard.releaseAllVideos();
        } catch (Exception e) {
        }
    }

    @Override
    public void PullLoad() {
        // 设置延时三秒获取时局，用于显示加载效果
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // 这里处理请求返回的结果（这里使用模拟数据）
                RequestsData(true);
                // 更新数据
                myAdapter.notifyDataSetChanged();
                // 加载完毕
                lv.LoadingComplete();
            }
        }, 3000);

    }

    public void RequestsData(boolean isClean) {
        try {
            if (isClean) {
                videoList = new ArrayList<>();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getVideoList/", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        JSONArray data = response.getJSONArray("data");
                        Integer code = (Integer) response.get("code");
                        if (code == 0) {
                            for (int i = 0; i < 10; i++) {
                                VideoItem vi = new VideoItem();
                                JSONObject item = data.getJSONObject(i);
                                vi.set_id(item.getString("_id"));
                                vi.setTitle(item.getString("title"));
                                vi.setFrom(item.getString("from"));
                                vi.setPic(item.getString("pic"));
                                vi.setUrl(item.getString("url"));
                                vi.setTimestamp(item.getString("timestamp"));
                                videoList.add(vi);
                            }

                        }
                        myAdapter.notifyDataSetChanged();
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
                loadMoreData();
                lv.LoadingComplete();
            }
        }, 1000);

    }

    public void loadMoreData() {

        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getVideoList/", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        JSONArray data = response.getJSONArray("data");
                        Integer code = (Integer) response.get("code");
                        if (code == 0) {
                            for (int i = 0; i < 6; i++) {
                                VideoItem vi = new VideoItem();
                                JSONObject item = data.getJSONObject(i);
                                vi.set_id(item.getString("_id"));
                                vi.setTitle(item.getString("title"));
                                vi.setFrom(item.getString("from"));
                                vi.setPic(item.getString("pic"));
                                vi.setUrl(item.getString("url"));
                                vi.setTimestamp(item.getString("timestamp"));
                                videoList.add(vi);
                            }
                        }
                        myAdapter.notifyDataSetChanged();
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
