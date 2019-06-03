package com.example.win10.personality_newsapp.showcomment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.comment.CommentBean;

import java.util.List;

public class CommentShowAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private RequestQueue requestQueue;
    private List<CommentShowBean> commentitems;

    public CommentShowAdapter(Context context, RequestQueue requestQueue, List<CommentShowBean> commentitems) {
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
        View view=inflater.inflate(R.layout.comment_display_item,null);
        NetworkImageView user_headepicture=(NetworkImageView)view.findViewById(R.id.user_headepicture);
        TextView user_name=(TextView)view.findViewById(R.id.user_name);
        TextView user_release_time=(TextView)view.findViewById(R.id.user_release_time);
        TextView comment_item_content=(TextView)view.findViewById(R.id.comment_item_content);
        networkImageLoad(commentitems.get(position).getHeadpictureurl(),user_headepicture);
        user_name.setText(commentitems.get(position).getNickname());
        user_release_time.setText(commentitems.get(position).getRelease_time());
        comment_item_content.setText(commentitems.get(position).getComment_content());
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
