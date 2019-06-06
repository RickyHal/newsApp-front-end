package com.example.win10.personality_newsapp.comment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.win10.personality_newsapp.R;

import java.util.List;

public class CommentListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private RequestQueue requestQueue;
    private List<CommentBean> commentitems;

    public CommentListAdapter(Context context,RequestQueue requestQueue, List<CommentBean> commentitems) {
        this.requestQueue=requestQueue;
        this.inflater=LayoutInflater.from(context);
        this.commentitems=commentitems;
    }

    @Override
    public int getCount(){
        return commentitems.size();
    }

    @Override
    public Object getItem(int position) {
        return commentitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.comment_list_item,null);
        NetworkImageView headpicurl=(NetworkImageView)view.findViewById(R.id.head_picture);
        TextView nickname=(TextView)view.findViewById(R.id.nickname);
        TextView release_time=(TextView)view.findViewById(R.id.release_time);
        TextView comment_content=(TextView)view.findViewById(R.id.comment_content);
        TextView news_item=(TextView)view.findViewById(R.id.news_item);
        networkImageLoad(commentitems.get(position).getHeadpictureurl(),headpicurl);
        nickname.setText(commentitems.get(position).getNickname());
        release_time.setText(commentitems.get(position).getRelease_time());
        comment_content.setText(commentitems.get(position).getComment_content());
        news_item.setText(commentitems.get(position).getNews_item());
        return view;
    }

    private void networkImageLoad(String imageurl,NetworkImageView networkImageView){
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
        networkImageView.setDefaultImageResId(R.drawable.no_headpic);
        networkImageView.setErrorImageResId(R.drawable.no_headpic);
        //设置url和ImageLoader对象
        networkImageView.setImageUrl(imageurl, imageLoader);
    }
}
