package com.example.win10.personality_newsapp.news_visit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.PropertyUri;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.comment.CommentActivity;
import com.example.win10.personality_newsapp.showcomment.TestAddCommentActivity;
import com.example.win10.personality_newsapp.user.Myapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class NewsDetailActivity extends Activity {

    RequestQueue requestQueue;
    LinearLayout vg;
    List<JSONObject> datalist;
    news_item newsitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.news_details,null);
        vg=view.findViewById(R.id.news_detail_layout);
        vg.setPadding(5,0,5,0);
        //vg=new LinearLayout(NewsDetailActivity.this);
        //vg.setOrientation(LinearLayout.VERTICAL);
       setContentView(view);
        Intent intent = getIntent();
        String newsid = intent.getStringExtra("news_id");

        requestQueue= Volley.newRequestQueue(this);
        datalist=new ArrayList<JSONObject>();

        Myapp myapp = (Myapp)getApplication();
        if(myapp.getUser_id()!=null){
            obtainData(newsid,myapp.getUser_id().toString());
        }else{
            obtainData(newsid,"-1");
        }

    }
    public void obtainData(String news_id,String user_id) {

        try {

            /*RequestQueue mQueue = Volley.newRequestQueue(News_Manifest.this);
            JsonArrayRequest jsonArray = new JsonArrayRequest("http://120.77.144.237/getNewsList/",
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                           // Log.d("Tag:", response.toString());

                            Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.e("Error: ", error.getMessage());
                    Toast.makeText(getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
                }
            });

            mQueue.add(jsonArray);*/

            // String jsonUrl = "http://152.123.55.102:8080/json.html";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    new PropertyUri().host+"app/getNewsDetail/?_id="+news_id+"&user_id="+user_id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        //newslist= new ArrayList<news_item>();
                        JSONArray data = response.getJSONArray("data");
                        Integer code= (Integer)response.get("code");
                        if (code==0){
                            for (int i=0;i<data.length();i++){
                                newsitem=new news_item();
                                JSONObject item=data.getJSONObject(i);
                                newsitem.setFrom(item.getString("from"));
                                newsitem.set_id(item.getString("_id"));
                                newsitem.setTitle(item.getString("title"));
                                newsitem.setTag(item.getString("tag"));
                                newsitem.setTimestamp(item.getString("timestamp"));
                                JSONArray jsonArray = item.getJSONArray("imageurls");
                                for(int j=0;j<jsonArray.length();j++){
                                    newsitem.getImg().add((String)jsonArray.get(j));
                                }
                                JSONArray jsonArrayDetail = item.getJSONArray("detail");
                                for(int k=0;k<jsonArrayDetail.length();k++){
                                    datalist.add(jsonArrayDetail.getJSONObject(k));
                                }
                            }
                            addview();
                            Toast.makeText(getApplicationContext(),newsitem.getTag(),Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(),"数据错误",Toast.LENGTH_LONG).show();
                        }
                        //String size=Integer.toString(newslist.size());
                        //Toast.makeText(getApplicationContext(),size,Toast.LENGTH_LONG).show();

                        //Toast.makeText(getApplicationContext(),"加载完成",Toast.LENGTH_LONG).show();
                    }catch (JSONException e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"网络错误",Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addview(){
        //Toast.makeText(getApplicationContext(),"执行add方法",Toast.LENGTH_LONG).show();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,100,0,100);
        TextView title=new TextView(NewsDetailActivity.this);
        title.setText(newsitem.getTitle());
        title.setTextSize(30);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(Color.parseColor("#000000"));
        title.setLayoutParams(lp);
        vg.addView(title);
        LinearLayout linearLayout=new LinearLayout(NewsDetailActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1);

        TextView from=new TextView(NewsDetailActivity.this);
        from.setText(newsitem.getFrom());
        from.setTextColor(Color.parseColor("#FF8C00"));
        from.setTextSize(16);
        from.setGravity(Gravity.CENTER);
        from.setLayoutParams(text);



        TextView tag=new TextView(NewsDetailActivity.this);
        tag.setText(newsitem.getTag());
        tag.setTextColor(Color.parseColor("#006400"));
        tag.setTextSize(16);
        tag.setGravity(Gravity.CENTER);
        tag.setLayoutParams(text);
        TextView time=new TextView(NewsDetailActivity.this);

        String stringdate =newsitem.getTimestamp();

        Long timestamp = Long.parseLong(stringdate)*1000;
        Date date = new Date(timestamp);
        SimpleDateFormat format=new SimpleDateFormat("MM月dd日 HH时");
        String nowDateString=format.format(date);

        time.setText(nowDateString);
        time.setTextColor(Color.parseColor("#9400D3"));
        time.setTextSize(16);
        time.setGravity(Gravity.CENTER);
        time.setLayoutParams(text);
        linearLayout.addView(from);
        linearLayout.addView(tag);
        linearLayout.addView(time);

        lp.setMargins(0,0,0,40);
        linearLayout.setLayoutParams(lp);
        vg.addView(linearLayout);
        for(int i=0;i<datalist.size();i++) {
            try{
            JSONObject con = datalist.get(i);
            if (con.getString("type").equals("text")) {
                TextView tv=new TextView(NewsDetailActivity.this);
                tv.setText("        "+con.getString("data"));
                tv.setTextSize(20);

                lp.setMargins(0,10,0,10);

                tv.setLayoutParams(lp);
                //tv.setGravity(Gravity.CENTER);
                vg.addView(tv);

            }else if(con.getString("type").equals("image")){
                NetworkImageView nv=new NetworkImageView(NewsDetailActivity.this);
                String dataurl=con.getString("data");
                networkImageLoad(dataurl,nv);
                vg.addView(nv);

            }
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
        }

        Button btn=new  Button(NewsDetailActivity.this);
        btn.setText("获取用户评论");
        vg.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("news_id",getIntent().getStringExtra("news_id"));
                intent.setClass(NewsDetailActivity.this, TestAddCommentActivity.class);
                NewsDetailActivity.this.startActivity(intent);
            }
        });
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

        networkImageView.setDefaultImageResId(R.drawable.ic_launcher);
        networkImageView.setErrorImageResId(R.drawable.chahao);
        //设置url和ImageLoader对象
        networkImageView.setImageUrl(imageurl,
                imageLoader);
    }
}
