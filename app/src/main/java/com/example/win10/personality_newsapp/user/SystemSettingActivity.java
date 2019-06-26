package com.example.win10.personality_newsapp.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.win10.personality_newsapp.MainActivity;
import com.example.win10.personality_newsapp.R;

import java.lang.reflect.Field;

public class SystemSettingActivity extends AppCompatActivity {

    ImageView back;
    private  SharedPreferences pref;
    private   SharedPreferences.Editor editor;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent("android.intent.action.CART_BROADCAST");
            intent.putExtra("data","refresh");
        LocalBroadcastManager.getInstance(SystemSettingActivity.this).sendBroadcast(intent);
        sendBroadcast(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
       pref=getSharedPreferences("userData", Context.MODE_PRIVATE);
        back=(ImageView) findViewById(R.id.login_back1) ;
        LinearLayout modify_pwd=(LinearLayout) findViewById(R.id.sys_modifyPwd) ;
        Intent intent=getIntent();
        int flag=intent.getIntExtra("login_setting",0);
        RelativeLayout view=(RelativeLayout) findViewById(R.id.system_setting_view);
        TextView tips=(TextView)findViewById(R.id.tip_text);    //提示登录的文本
        Button exit_button=(Button) findViewById(R.id.login_exit);
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

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SystemSettingActivity.this);
                builder.setTitle("退出确认").setMessage("退出当前账号，将不能收藏，发布评论").setNegativeButton("取消", null).setCancelable(true);
                builder.setPositiveButton("确认退出",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                editor=pref.edit();
                                editor.clear();
                                editor.apply();
                                Intent intent=new Intent();
                                intent.setClass(SystemSettingActivity.this,MainActivity.class);
                                intent.putExtra("exit",true);
                                startActivity(intent);
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
                try {
                    Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                    mAlert.setAccessible(true);
                    Object mAlertController = mAlert.get(dialog);
                    Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                    mMessage.setAccessible(true);
                    TextView mMessageView = (TextView) mMessage.get(mAlertController);
                    mMessageView.setTextColor(Color.rgb(196, 194, 194));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
               dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
            }
        });




    }
}
