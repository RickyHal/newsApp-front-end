package com.example.win10.personality_newsapp.video_list;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import com.example.win10.personality_newsapp.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class LoadListView extends ListView implements AbsListView.OnScrollListener {
    View footer;
    int totalItemCount;
    int lastVisibleItem;
    int visibleCount = 1;
    boolean isLoading = false;
    ILoadListerner iLoadListerner;
    private int Yload;
    private int firstVisible;
    private int headHeight;
    private TextView headtxt;
    private TextView headtime;
    private ProgressBar progressBar;
    private View headview;

    public LoadListView(Context context) {
        super(context);
        initview(context);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview(context);
    }

    private void initview(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        headview = LinearLayout.inflate(context, R.layout.head, null);
        headtxt = (TextView) headview.findViewById(R.id.headtxt);
//        headtime = (TextView) headview.findViewById(R.id.timetxt);
        progressBar = (ProgressBar) headview.findViewById(R.id.headprogress);
//        headtime.setText("上次更新时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        footer = layoutInflater.inflate(R.layout.footer_layout, null);
        headview.measure(0, 0);
        headHeight = headview.getMeasuredHeight();
        headview.setPadding(0, -headHeight, 0, 0);
        this.addHeaderView(headview);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (totalItemCount == lastVisibleItem && scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                //加载更多数据
                iLoadListerner.onload();
            }
        }
        if (firstVisible == 0 && scrollState == SCROLL_STATE_IDLE) {
            headview.setPadding(0, 0, 0, 0);
            headtxt.setText("正在刷新.......");
            progressBar.setVisibility(View.VISIBLE);
            iLoadListerner.PullLoad();
        }
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                //滑动状态
                Log.e("videoTest", "SCROLL_STATE_FLING");
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                //静止状态
                Log.e("videoTest", "SCROLL_STATE_IDLE"+headtxt.getText());
                if (!(headtxt.getText().equals("正在刷新......."))){
                    autoPlayVideo(view);
                }
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                //拖动状态
                Log.e("videoTest", "SCROLL_STATE_TOUCH_SCROLL");
                break;
            default:
                break;
        }
    }

    void autoPlayVideo(AbsListView view) {
//遍历当前界面显示的每个播放器
        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.videoView) != null) {
                JCVideoPlayerStandard videoPlayerStandard1 = (JCVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.videoView);
                Rect rect = new Rect();
                videoPlayerStandard1.getLocalVisibleRect(rect);
                int videoheight3 = videoPlayerStandard1.getHeight();
                //当前播放器能完全显示
                if (rect.top == 0 && rect.bottom == videoheight3) {
                    if (videoPlayerStandard1.currentState == JCVideoPlayer.CURRENT_STATE_NORMAL || videoPlayerStandard1.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                        //调用开始播放的按钮
                        videoPlayerStandard1.startButton.performClick();
                    }
                    return;
                }

            }
        }
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisible = firstVisibleItem;
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
        this.visibleCount = visibleItemCount;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Yload = (int) ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                int paddingY = headHeight + (moveY - Yload) / 2-300;
                if (paddingY < 0) {
                    headtxt.setText("下拉刷新........");
                    progressBar.setVisibility(View.GONE);
                }
                if (paddingY > 0) {
                    headtxt.setText("松开刷新........");
                    progressBar.setVisibility(View.GONE);
                }
                headview.setPadding(0, paddingY, 0, 0);
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setInterface(ILoadListerner iLoadListerner) {
        this.iLoadListerner = iLoadListerner;
    }

    public interface ILoadListerner {
         void onload();
         void PullLoad();
    }

    public void LoadingComplete() {
        isLoading = false;
        headview.setPadding(0, -headHeight, 0, 0);
    }
}
