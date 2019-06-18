package com.example.win10.personality_newsapp.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.win10.personality_newsapp.MainActivity;
import com.example.win10.personality_newsapp.R;

public class SystemSettingActivity extends AppCompatActivity {

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        back=(ImageView) findViewById(R.id.login_back1) ;
        LinearLayout modify_pwd=(LinearLayout) findViewById(R.id.sys_modifyPwd) ;
        Intent intent=getIntent();
        int flag=intent.getIntExtra("login_setting",0);
        RelativeLayout view=(RelativeLayout) findViewById(R.id.system_setting_view);
        TextView tips=(TextView)findViewById(R.id.tip_text);
        if(flag==1){
            view.setVisibility(View.VISIBLE);
            tips.setVisibility(View.GONE);
        }
        LinearLayout modify_userinfo=(LinearLayout) findViewById(R.id.sys_modify) ;
        modify_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SystemSettingActivity.this, ModifyUserinfoActivity.class);    //跳转到修改用户信息页面
                startActivity(intent);
            }
        });

        modify_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SystemSettingActivity.this, ModifyPwdActivity.class);    //跳转到修改用户密码页面
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(SystemSettingActivity.this, MainActivity.class);    //系统设置返回
                intent1.putExtra("back",true);
                startActivity(intent1);
            }
        });


    }
}
