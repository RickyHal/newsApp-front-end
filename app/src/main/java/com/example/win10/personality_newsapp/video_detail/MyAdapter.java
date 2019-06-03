package com.example.win10.personality_newsapp.video_detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.video_list.VideoActivity;
import com.example.win10.personality_newsapp.video_list.VideoItem;

import java.util.ArrayList;
import java.util.Random;

public class MyAdapter extends BaseAdapter {

    ArrayList<VideoItem> list;
    LayoutInflater inflater;
    RequestQueue requestQueue;
    VideoDetailActivity videoDetailActivity;
    public MyAdapter(Context context, RequestQueue requestQueue, ArrayList<VideoItem> list, VideoDetailActivity videoDetailActivity) {
        this.inflater = LayoutInflater.from(context);
        this.requestQueue = requestQueue;
        this.list = list;
        this.videoDetailActivity=videoDetailActivity;
    }

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
        boolean isTouch = false;
        int totalTime;
        int currentTime;
        View view = inflater.inflate(R.layout.video_item, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView from = (TextView) view.findViewById(R.id.from);
        TextView id = (TextView) view.findViewById(R.id.video_id);
        TextView time = (TextView) view.findViewById(R.id.time);
        ImageView play_btn=(ImageView) view.findViewById(R.id.play_btn);
        title.setText(list.get(position).getTitle());
        from.setText(list.get(position).getFrom());
//        Uri uri= Uri.parse(list.get(position).getPic());
//        video_pic.setImageURI(uri);
        NetworkImageView video_pic=(NetworkImageView)view.findViewById(R.id.video_pic);
        ViewGroup.LayoutParams layoutlp=video_pic.getLayoutParams();
        WindowManager wm = (WindowManager) this.videoDetailActivity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        layoutlp.width=dm.widthPixels;
        layoutlp.height=dm.heightPixels/3;
        video_pic.setMaxWidth( dm.widthPixels);
        video_pic.setMaxHeight( dm.heightPixels/3);
        networkImageLoad(list.get(position).getPic(),video_pic);
        video_pic.setAdjustViewBounds(true);
//        networkImageView.setMaxHeight(10);
//        video.setVideoPath(list.get(position).getUrl());
//        video.requestFocus();
//        video.setBackground(this.createVideoThumbnail(list.get(position).getPic(),100,180));
//        MediaController localMediaController = new MediaController(new VideoActivity());
//        video.setMediaController(localMediaController);
//        video.start();
        Random random = new Random();
        int min = random.nextInt(59) + 1;
        time.setText(min + "分钟前");
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
