package com.example.win10.personality_newsapp.video_list;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.example.win10.personality_newsapp.R;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.Random;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MyAdapter extends BaseAdapter {

    ArrayList<VideoItem> list;
    LayoutInflater inflater;
    RequestQueue requestQueue;
    FragmentActivity videoActivity;
    Handler handler = new Handler();
    JCVideoPlayerStandard jCVideoPlayer;
    int position=0;
    LoadListView lv;
    public MyAdapter(Context context, RequestQueue requestQueue, ArrayList<VideoItem> list, FragmentActivity videoActivity, LoadListView lv) {
        this.inflater = LayoutInflater.from(context);
        this.requestQueue = requestQueue;
        this.list = list;
        this.videoActivity=videoActivity;
        this.lv=lv;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position=position;
        View view = inflater.inflate(R.layout.video_item, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView from = (TextView) view.findViewById(R.id.from);
        TextView time = (TextView) view.findViewById(R.id.time);
        jCVideoPlayer=(JCVideoPlayerStandard)  view.findViewById(R.id.videoView);
        title.setText(list.get(position).getTitle());
        from.setText(list.get(position).getFrom());
        jCVideoPlayer.setUp(list.get(position).getUrl(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL,list.get(position).getTitle());
        Glide.with(videoActivity).load(list.get(position).getPic()).into(jCVideoPlayer.thumbImageView);
        time.setText(list.get(position).getTimestamp());
        return view;
    }

    public void networkImageLoad(String imageurl,NetworkImageView networkImageView){

        //创建一个ImageLoader
        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        //NetworkImageView

//        networkImageView.setDefaultImageResId(R.drawable.loading);
//        networkImageView.setErrorImageResId(R.drawable.loading);
        //设置url和ImageLoader对象
        Log.d("INFO",imageurl);
        networkImageView.setImageUrl(imageurl,
                imageLoader);
    }
}
