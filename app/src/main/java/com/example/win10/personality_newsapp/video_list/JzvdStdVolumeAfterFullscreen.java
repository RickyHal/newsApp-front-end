package com.example.win10.personality_newsapp.video_list;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.win10.personality_newsapp.R;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class JzvdStdVolumeAfterFullscreen extends JCVideoPlayerStandard {
    public JzvdStdVolumeAfterFullscreen(Context context) {
        super(context);
    }

    public JzvdStdVolumeAfterFullscreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        startVideo();
    }

    /**
     * 退出全屏模式的时候开启静音模式
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if(i==R.id.back){
            JCVideoPlayerManager.getCurrentJcvd().currentState = CURRENT_STATE_NORMAL;
            JCVideoPlayerManager.getFirstFloor().clearFloatScreen();
            JCMediaManager.instance().releaseMediaPlayer();
            JCVideoPlayerManager.setFirstFloor(null);
        }else if(i==R.id.fullscreen){
            if(currentScreen == SCREEN_WINDOW_FULLSCREEN){
                JCVideoPlayerManager.getCurrentJcvd().currentState = CURRENT_STATE_NORMAL;
                JCVideoPlayerManager.getFirstFloor().clearFloatScreen();
                JCMediaManager.instance().releaseMediaPlayer();
                JCVideoPlayerManager.setFirstFloor(null);
            }
        }
    }
}
