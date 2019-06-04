package com.example.win10.personality_newsapp.video_detail;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.video_list.VideoItem;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class JzvdStdComplete extends JCVideoPlayerStandard {
    private  ArrayList<VideoItem> videoList;
    int position;

    public ArrayList<VideoItem> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<VideoItem> videoList) {
        this.videoList = videoList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public VideoDetailActivity getVideoDetail() {
        return videoDetail;
    }

    public void setVideoDetail(VideoDetailActivity videoDetail) {
        this.videoDetail = videoDetail;
    }

    VideoDetailActivity videoDetail;



    public JzvdStdComplete(Context context) {
        super(context);
//        this.videoDetail=videoDetail;
//        this.position=position;
//        this.videoList=videoList;
    }

    public JzvdStdComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
//        Toast.makeText( videoDetail.this,"视频播放结束",Toast.LENGTH_LONG).show();
//        JzvdStdComplete  jCVideoPlayer=new JzvdStdComplete(this.context,position+1,videoList);
//        jCVideoPlayer = (JzvdStdComplete ) findViewById(R.id.videoView);
//        jCVideoPlayer.setUp(videoList.get(position).getUrl(),JCVideoPlayer.SCREEN_LAYOUT_NORMAL,videoList.get(position).getTitle());
//        Glide.with(context).load(videoList.get(position).getPic()).into(jCVideoPlayer.thumbImageView);
//        jCVideoPlayer.startVideo();
    }
}
