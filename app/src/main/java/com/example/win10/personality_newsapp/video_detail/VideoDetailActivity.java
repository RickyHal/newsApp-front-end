package com.example.win10.personality_newsapp.video_detail;

import android.content.Context;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.video_detail.LoadListView;
import com.example.win10.personality_newsapp.video_detail.MyAdapter;
import com.example.win10.personality_newsapp.video_detail.VideoDetailActivity;
import com.example.win10.personality_newsapp.video_list.VideoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class VideoDetailActivity extends AppCompatActivity implements LoadListView.ILoadListerner{
    public static ArrayList<VideoItem> videoList;
    MyAdapter myAdapter;
    RequestQueue requestQueue ;
    LoadListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        Intent intent=getIntent();
        VideoItem vi=(VideoItem) intent.getSerializableExtra("video");
        TextView title=(TextView) findViewById(R.id.title);
        TextView from = (TextView)findViewById(R.id.from);
        TextView id = (TextView) findViewById(R.id.video_id);
        TextView time = (TextView)findViewById(R.id.time);
        title.setText(vi.getTitle());
        from.setText(vi.getFrom());
        title.setText(vi.getTitle());
        VideoView vv=(VideoView)findViewById(R.id.videoView);
        Uri uri=Uri.parse(vi.getUrl());
        vv.setVideoURI(uri);
        ViewGroup.LayoutParams layoutlp=vv.getLayoutParams();
//     vv.getHolder().setFixedSize(dm.widthPixels,300);
//        vv.setLayoutParams(new RelativeLayout.LayoutParams(dm.widthPixels,300));
//        MediaController controller = new MediaController(getActivity());
        vv.setMediaController(new MediaController(this));
        vv.requestFocus();
        vv.start();
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                Toast.makeText(getApplicationContext(),"即将播放下一个视频",Toast.LENGTH_LONG).show();
                Intent intent =new Intent(VideoDetailActivity.this, VideoDetailActivity.class);
                intent.putExtra("video",videoList.get(0));
                //启动
                startActivity(intent);
                finish();
            }
        });
        lv=(LoadListView) findViewById(R.id.list);
        lv.setInterface(this);
        requestQueue= Volley.newRequestQueue(this);
        videoList= new ArrayList<VideoItem>();
        RequestsData();
        myAdapter=new MyAdapter(this,requestQueue,videoList,this);
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 给bnt1添加点击响应事件
                Intent intent =new Intent(VideoDetailActivity.this, VideoDetailActivity.class);
                intent.putExtra("video",videoList.get(position));
                //启动
                startActivity(intent);
                finish();
            }
        });
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
                            for (int i=0;i<6;i++){
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
                            for (int i=0;i<6;i++){
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

