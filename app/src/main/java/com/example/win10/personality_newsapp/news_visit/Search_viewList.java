package com.example.win10.personality_newsapp.news_visit;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win10.personality_newsapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Search_viewList extends Activity {

    ArrayList<String> data;
    ListView listView;
    ArrayAdapter arrayAdapter;
    private static String DB_NAME="mydb";
    private SQLite_history sqlite;
    private SQLiteDatabase db;
    private Cursor cursor;
    EditText content;
    ImageView chahao;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view_list);
        data=new ArrayList<String>();
        listView=findViewById(R.id.historylist);
        content=findViewById(R.id.search_input);
        chahao=findViewById(R.id.searchclear);
        btn=findViewById(R.id.historyclearbutton);
        sqlite=new SQLite_history(this,DB_NAME,null,1);
        db= sqlite.getWritableDatabase();
        dbfindall();
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String listitem=(String)listView.getItemAtPosition(position);
                        content.setText(listitem);
                        String text=content.getText().toString().trim();
                        dbDel(text);
                        dbadd(text);
                        Intent intent = new Intent();

                        intent.putExtra("searchcontent",text);
                        intent.setClass(Search_viewList.this,search_result.class);
                        Search_viewList.this.startActivity(intent);
                    }
                }
        );
        content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //Toast.makeText(getApplicationContext(),"你点击了回车",Toast.LENGTH_LONG).show();
                    String text=content.getText().toString().trim();
                    if(text.equals("")){
                        Toast.makeText(getApplicationContext(),"请输入内容！",Toast.LENGTH_LONG).show();
                        return false;
                    }
                    if(data.contains(text)){
                        dbDel(text);
                    }

                    dbadd(text);
                    Intent intent = new Intent();

                    intent.putExtra("searchcontent",text);
                    intent.setClass(Search_viewList.this,search_result.class);
                    Search_viewList.this.startActivity(intent);

                }
                return false;
            }
        });

        chahao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setText("");
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Search_viewList.this);

                builder.setTitle("提示");
                builder.setMessage("确认要清空历史记录？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbDelAll();
                        dbfindall();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                //一样要show
                builder.show();

            }
        });
    }
    protected void dbfindall(){
        data.clear();
        cursor=db.query(SQLite_history.TB_name,null,
                null,null,null,
                null,"history_id DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String id = cursor.getString(0);
            String content = cursor.getString(1);

            data.add(content);
            cursor.moveToNext();
        }
        showlist();

    }
    private void showlist(){

        /*listadaptator=new SimpleAdapter(this,
                data,
                R.layout.listview,
                new String[]{"_id","title","content"},
                new int[]{R.id.ID,R.id.TITLE,R.id.CONTENT});*/
        arrayAdapter=new ArrayAdapter(this,R.layout.historylist,R.id.searchlistitem,data);
        listView.setAdapter(arrayAdapter);
    }

    protected void dbadd(String item){
        ContentValues values=new ContentValues();
        values.put("content",item);
        long rowid = db.insert(SQLite_history.TB_name,"history_id",values);
        if(rowid==-1){
            Log.i("MySQLite","数据库更新失败！");
        }else{
            Log.i("MySQLite","数据库更新成功！");
        }
    }
    protected void dbDel(String delcon){
        String where="content='"+delcon+"'";
        int i=db.delete(SQLite_history.TB_name,where,null);
        if(i>0){
            Log.i("MySQLite","数据删除成功！");
        }else{
            Log.i("MySQLite","数据删除失败！");
        }
    }
    protected void dbDelAll(){
        int i=db.delete(SQLite_history.TB_name,null,null);
        if(i>0){
            Log.i("MySQLite","数据库删除成功！");
        }else{
            Log.i("MySQLite","数据库删除失败！");
        }
    }
}
