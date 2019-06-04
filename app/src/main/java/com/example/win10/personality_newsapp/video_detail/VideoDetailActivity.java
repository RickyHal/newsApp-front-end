package com.example.win10.personality_newsapp.video_detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.video_detail.LoadListView;
import com.example.win10.personality_newsapp.video_detail.MyAdapter;
import com.example.win10.personality_newsapp.video_detail.VideoDetailActivity;
import com.example.win10.personality_newsapp.video_list.VideoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import com.example.win10.personality_newsapp.video_detail.JzvdStdComplete;
import com.jaeger.library.StatusBarUtil;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
public class VideoDetailActivity extends AppCompatActivity  implements LoadListView.ILoadListerner{
    private static ArrayList<VideoItem> videoList;
    MyAdapter myAdapter;
    RequestQueue requestQueue ;
    LoadListView lv;
    JCVideoPlayerStandard  jCVideoPlayer;
    int position=0;
    Handler handler = new Handler();
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_video_detail);
        Intent intent=getIntent();
        VideoItem vi=(VideoItem) intent.getSerializableExtra("video");
        TextView from = (TextView)findViewById(R.id.from);
        from.setText(vi.getFrom());

        lv=(LoadListView) findViewById(R.id.list);
        lv.setInterface(this);
        requestQueue= Volley.newRequestQueue(this);
        videoList= new ArrayList<VideoItem>();
        RequestsData();
        myAdapter=new MyAdapter(this,requestQueue,videoList,this);
        lv.setAdapter(myAdapter);
        jCVideoPlayer = (JCVideoPlayerStandard ) findViewById(R.id.videoView);
        jCVideoPlayer.setUp(vi.getUrl(),JCVideoPlayer.SCREEN_LAYOUT_NORMAL,vi.getTitle());
        Glide.with(VideoDetailActivity.this).load(vi.getPic()).into(jCVideoPlayer.thumbImageView);
        jCVideoPlayer.startVideo();
        handler.post(runnable);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jCVideoPlayer = (JCVideoPlayerStandard ) findViewById(R.id.videoView);
                jCVideoPlayer.setUp(videoList.get(position).getUrl(),JCVideoPlayer.SCREEN_LAYOUT_NORMAL,videoList.get(position).getTitle());
                Glide.with(VideoDetailActivity.this).load(videoList.get(position).getPic()).into(jCVideoPlayer.thumbImageView);
                jCVideoPlayer.startVideo();
                videoList.remove(0);
                myAdapter.notifyDataSetChanged();
//                myAdapter=new MyAdapter(getApplicationContext(),requestQueue,videoList,VideoDetailActivity.this);
//                lv.setAdapter(myAdapter);
                handler.post(runnable);
            }
        });
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (videoList.size() > 0 && null != jCVideoPlayer) {
                if (jCVideoPlayer.currentState == JCVideoPlayerStandard.CURRENT_STATE_AUTO_COMPLETE ||
                        jCVideoPlayer.currentState == JCVideoPlayerStandard.CURRENT_STATE_ERROR) {
                    if (position >= videoList.size()) {
                        position = 0;
                    }
                    Toast.makeText(getApplicationContext(),"自动切换到下一个视频",Toast.LENGTH_LONG).show();
                    jCVideoPlayer.setUp(videoList.get(0).getUrl(), JCVideoPlayerStandard.SCROLL_AXIS_HORIZONTAL,videoList.get(0).getTitle());
                    Glide.with(VideoDetailActivity.this).load(videoList.get(0).getPic()).into(jCVideoPlayer.thumbImageView);
                    jCVideoPlayer.startVideo();
                    videoList.remove(0);
//                    position += 1;
                    myAdapter.notifyDataSetChanged();
//                    myAdapter=new MyAdapter(getApplicationContext(),requestQueue,videoList,VideoDetailActivity.this);
//                    lv.setAdapter(myAdapter);
                }
            }else{
                videoList= new ArrayList<VideoItem>();
                RequestsData();
                myAdapter=new MyAdapter(getApplicationContext(),requestQueue,videoList,VideoDetailActivity.this);
//                myAdapter=new MyAdapter(this,requestQueue,videoList,this);
                lv.setAdapter(myAdapter);
            }
//                Log.d("1","11111");
//                videoList=new ArrayList<>();
//                RequestsData();
//                myAdapter.notifyDataSetChanged();
//                myAdapter=new MyAdapter(getApplicationContext(),requestQueue,videoList,VideoDetailActivity.this);
//                lv.setAdapter(myAdapter);
//            }
                handler.postDelayed(this, 1000);
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }
    public void  RequestsData(){
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getVideoList/", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        JSONArray data = response.getJSONArray("data");
                        Integer code= (Integer)response.get("code");
                        if (code==0){
                            for (int i=0;i<10;i++){
                                VideoItem vi=new VideoItem();
                                JSONObject item=data.getJSONObject(i);
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
                loadMoreData();
                lv.LoadingComplete();
            }
        },1000);

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
                        Integer code= (Integer)response.get("code");
                        if (code==0){
                            for (int i=0;i<10;i++){
                                VideoItem vi=new VideoItem();
                                JSONObject item=data.getJSONObject(i);
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

