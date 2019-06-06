package com.example.win10.personality_newsapp.showcomment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.comment.CommentActivity;
import com.example.win10.personality_newsapp.comment.CommentBean;
import com.example.win10.personality_newsapp.comment.CommentListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAddCommentActivity extends AppCompatActivity {
    CommentShowAdapter commentShowAdapter;
    RequestQueue requestQueue ;
    private boolean flag=true;
    private int position=-1;
    private int ischecked=0;
    private List<CommentShowBean> list;

    private void checkiscollected(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "http://120.77.144.237/app/checkCollect/?user_id=1&_id="+String.valueOf(getIntent().getStringExtra("news_id")), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"网络异常，请重试",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void putData(String news_id) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getNewsComment/?_id="+news_id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray data = response.getJSONArray("data");
                        Integer code= (Integer)response.get("code");
                        if (code==0){
                            for (int i=0;i<data.length();i++){
                                CommentShowBean commentitem=new CommentShowBean();
                                JSONObject item=data.getJSONObject(i);
                                commentitem.setNickname(item.getString("user_name")+"    回复");
                                commentitem.setHeadpictureurl(item.getString("user_avatar_url"));
                                commentitem.setComment_content(item.getString("comment_text"));
                                commentitem.setRelease_time(item.getString("comment_time"));
                                list.add(commentitem);
                            }
                        }
                        commentShowAdapter.notifyDataSetChanged();
                    }catch (JSONException e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_add_comment);
        final TextView textview=(TextView)findViewById(R.id.comment_text);
        final LinearLayout showstar=(LinearLayout)findViewById(R.id.show_star);
        final LinearLayout showinput=(LinearLayout)findViewById(R.id.show_input);
        final ImageView imagestar=(ImageView)findViewById(R.id.check_Is_Checked);
        final EditText edittext=(EditText)findViewById(R.id.comment_input);
        final Button sendcomment=(Button)findViewById(R.id.send_comment);
        ListView listview = (ListView)findViewById(R.id.comment_display_item);
        requestQueue= Volley.newRequestQueue(this);
        list= new ArrayList<CommentShowBean>();

        putData(String.valueOf(getIntent().getStringExtra("news_id")));
        commentShowAdapter=new CommentShowAdapter(this,requestQueue,list);
        listview.setAdapter(commentShowAdapter);
        listview.setEmptyView((TextView)findViewById(R.id.collectionnovalue));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestAddCommentActivity.this.flag=false;
                TestAddCommentActivity.this.position=position;
                textview.callOnClick();
                edittext.setHint("回复"+list.get(TestAddCommentActivity.this.position).getNickname().split(" ")[0]);
            }
        });

        sendcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                HashMap<String, String> user_partinfo=(HashMap<String,String>)getIntent().getSerializableExtra("user_partinfo");
                CommentShowBean commentitem=new CommentShowBean();
//                commentitem.setNickname(user_partinfo.get("user_name"));
//                commentitem.setHeadpictureurl(user_partinfo.get("user_avatar_url"));
                commentitem.setNickname("罗东升"+"    回复");
                commentitem.setHeadpictureurl("http://b-ssl.duitang.com/uploads/item/201708/22/20170822230245_rkCn4.jpeg");
                String reply_content="";
                if(TestAddCommentActivity.this.flag){
                    reply_content=edittext.getText().toString();
                }else{
                    reply_content=edittext.getText().toString()+"//@"+list.get(TestAddCommentActivity.this.position).getNickname().split(" ")[0]
                            +":"+list.get(TestAddCommentActivity.this.position).getComment_content();

                }
                commentitem.setComment_content(reply_content);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                commentitem.setRelease_time(df.format(new Date()));
                list.add(commentitem);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        "http://120.77.144.237/app/addComment/?_id="+String.valueOf(getIntent().getStringExtra("news_id"))+"&user_id="+"2"+
                                "&comment_text="+reply_content, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getBaseContext(), "发表成功", Toast.LENGTH_SHORT).show();
                        TestAddCommentActivity.this.flag=true;
                        TestAddCommentActivity.this.position=-1;
                        commentShowAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);
                edittext.setText("");
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(edittext.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                commentShowAdapter.notifyDataSetChanged();
            }
        });



//        添加收藏逻辑
        checkiscollected();
        if(ischecked==1){
            imagestar.setImageResource(R.mipmap.yes_collection);
        }else{
            imagestar.setImageResource(R.mipmap.no_collection);
        }
        imagestar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ischecked==1){
                    imagestar.setImageResource(R.mipmap.no_collection);
                    ischecked=0;
                }else{
                    imagestar.setImageResource(R.mipmap.yes_collection);
                    ischecked=1;
                }
            }
        });


        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showstar.setVisibility(View.GONE);
                showinput.setVisibility(View.VISIBLE);
                edittext.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        sendcomment.setEnabled(false);
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    sendcomment.setEnabled(true);
                }else{
                    sendcomment.setEnabled(false);
                }
            }
        });
    }

}
