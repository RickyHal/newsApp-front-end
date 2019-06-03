package com.example.win10.personality_newsapp.news_visit;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MyAdapter extends BaseAdapter {

    ArrayList<news_item> list;
    LayoutInflater inflater;
    RequestQueue requestQueue;

    public MyAdapter(Context context, RequestQueue requestQueue, ArrayList<news_item> list) {
        this.inflater = LayoutInflater.from(context);
        this.requestQueue = requestQueue;
        this.list = list;
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
        View view = inflater.inflate(R.layout.news_item, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView from = (TextView) view.findViewById(R.id.source);
        TextView id = (TextView) view.findViewById(R.id.newsid);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView tag = (TextView) view.findViewById(R.id.tag);
        NetworkImageView networkImageView1 = (NetworkImageView) view.findViewById(R.id.imageView1);
        NetworkImageView networkImageView2 = (NetworkImageView) view.findViewById(R.id.imageView2);
        NetworkImageView networkImageView3 = (NetworkImageView) view.findViewById(R.id.imageView3);
        /*ImageView imageView1=view.findViewById(R.id.imageView1);
        ImageView imageView2=view.findViewById(R.id.imageView2);
        ImageView imageView3=view.findViewById(R.id.imageView3);*/
        title.setText(list.get(position).getTitle());
        from.setText(list.get(position).getFrom());
        id.setText(list.get(position).get_id());
        tag.setText(list.get(position).getTag());
        /*title.setText("测试");
        from.setText("测试");
        id.setText("测试");*/
        Random random = new Random();
        int min = random.nextInt(59) + 1;
        time.setText(min + "分钟前");
        /*imageView1.setImageResource(R.drawable.loading_0);
        imageView2.setImageResource(R.drawable.loading_1);
        imageView3.setImageResource(R.drawable.loading_2);*/
        int syc = 0;
        for (int i = 0; i < list.get(position).getImg().size(); i++) {
            if (i < 3) {
                syc = 1;
                String url = list.get(position).getImg().get(i);
                switch (i) {
                    case 0:
                        networkImageLoad(url, networkImageView1);
                        break;
                    case 1:
                        networkImageLoad(url, networkImageView2);
                        break;
                    case 2:
                        networkImageLoad(url, networkImageView3);
                        break;
                }

            } else
                break;
        }
        if (syc == 0) {
            networkImageView1.setAdjustViewBounds(true);
            networkImageView1.setMaxHeight(10);
            networkImageView2.setAdjustViewBounds(true);
            networkImageView2.setMaxHeight(10);
            networkImageView3.setAdjustViewBounds(true);
            networkImageView3.setMaxHeight(10);
        }
        return view;
    }

    public void networkImageLoad(String imageurl, NetworkImageView networkImageView) {

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

        networkImageView.setDefaultImageResId(R.drawable.ic_launcher);
        networkImageView.setErrorImageResId(R.drawable.chahao);
        //设置url和ImageLoader对象
        networkImageView.setImageUrl(imageurl,
                imageLoader);
    }
}
