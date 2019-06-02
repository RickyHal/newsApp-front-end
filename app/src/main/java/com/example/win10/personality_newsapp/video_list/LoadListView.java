package com.example.win10.personality_newsapp.video_list;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.win10.personality_newsapp.R;

public class LoadListView   extends ListView implements AbsListView.OnScrollListener {
    View footer;
    int totalItemCount;
    int lastVisibleItem;
    boolean isLoading=false;
    ILoadListerner iLoadListerner;
    private int lastVisibleItemPosition;//上次滑动的位置
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

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initview(context);
    }

    private  void initview(Context context){
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        footer=layoutInflater.inflate(R.layout.footer_layout,null);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(totalItemCount==lastVisibleItem&&scrollState==SCROLL_STATE_IDLE){
            if(!isLoading){
                isLoading=true;
                //加载更多数据
                iLoadListerner.onload();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            this.lastVisibleItem=firstVisibleItem+visibleItemCount;
            this.totalItemCount=totalItemCount;
    }
    public void setInterface(ILoadListerner iLoadListerner){
        this.iLoadListerner=iLoadListerner;
    }
    public interface ILoadListerner{
        public  void onload();
    }
    public void LoadingComplete(){
        isLoading=false;
    }
}
