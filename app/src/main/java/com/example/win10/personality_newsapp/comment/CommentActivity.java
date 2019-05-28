package com.example.win10.personality_newsapp.comment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.TestAddCommentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    private List<Map<String,Object>> list;
    SimpleAdapter simpleAdapter;

    private List<Map<String,Object>> putData(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("head_picture", R.drawable.ic_launcher_background);
        map1.put("nickname", "大仙的第250位小迷弟");
        map1.put("release_time", "2019-05-31");
        map1.put("comment_content", "祖国万岁");
        map1.put("news_item", "@新华社:习近平的数次地方考察，都与这件大事密切相关。");
        list.add(map1);

        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("head_picture", R.drawable.ic_launcher_background);
        map2.put("nickname", "大仙的第250位小迷弟");
        map2.put("release_time", "2019-05-31");
        map2.put("comment_content", "点赞");
        map2.put("news_item", "@新华社:习近平的数次地方考察，都与这件大事密切相关。");
        list.add(map2);
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ListView listview = (ListView)findViewById(R.id.mylist);
        this.list=putData();

        this.simpleAdapter = new SimpleAdapter(this,this.list,R.layout.comment_list_item,
                new String[]{"head_picture","nickname","release_time","comment_content","news_item"},new int[]{R.id.head_picture,
                R.id.nickname,R.id.release_time,R.id.comment_content,R.id.news_item});
        listview.setAdapter(simpleAdapter);
        listview.setEmptyView((TextView)findViewById(R.id.comentnovalue));//设置当ListView为空的时候显示text_tip "暂无数据"

        //        删除全部收藏记录
        Button deleteall=(Button)findViewById(R.id.deleteall_comment);
        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder bb=new AlertDialog.Builder(CommentActivity.this);
                if(CommentActivity.this.list.size()<=0){
                    bb.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bb.setMessage("您还没参与评论哦");
                }else{
                    bb.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CommentActivity.this.list.clear();
                            simpleAdapter.notifyDataSetChanged();
                            Toast.makeText(getBaseContext(), "成功删除全部评论", Toast.LENGTH_SHORT).show();
                        }
                    });
                    bb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bb.setMessage("您确定要删除全部评论吗？");
                }
                bb.setTitle("提示");
                bb.show();
            }
        });



//       跳转监听
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CommentActivity.this, TestAddCommentActivity.class));
            }
        });
//        长按删除评论监听
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean  onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder bb=new AlertDialog.Builder(CommentActivity.this);
                bb.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(CommentActivity.this.list.remove(position)!=null){
                            System.out.println("success");
                        }else {
                            System.out.println("failed");
                        }
                        CommentActivity.this.simpleAdapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                bb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                bb.setMessage("您确定要删除该条评论吗？");
                bb.setTitle("提示");
                bb.show();
                return true;
            }
        });

    }

}
