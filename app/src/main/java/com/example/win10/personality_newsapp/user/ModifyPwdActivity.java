package com.example.win10.personality_newsapp.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.MainActivity;
import com.example.win10.personality_newsapp.PropertyUri;
import com.example.win10.personality_newsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ModifyPwdActivity extends AppCompatActivity {
        Button button_getCode;
        ImageButton click_put;
        ImageView back;
        EditText new_pwd,new_pwd_again,check_code;
    private TimeCount time;
    RequestQueue requestQueue;
    Myapp myapp;
    private SharedPreferences pref;
    private   SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        myapp=(Myapp)getApplication();
        pref=getSharedPreferences("userData", Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);
        back=(ImageView)findViewById(R.id.back) ;
        button_getCode=(Button)findViewById(R.id.getCode);
        new_pwd=(EditText)findViewById(R.id.new_pwd) ;
        new_pwd_again=(EditText)findViewById(R.id.new_pwd_again);
        check_code=(EditText)findViewById(R.id.codeForPwd);
        button_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCheckcode(myapp.getUser_email());
                time = new ModifyPwdActivity.TimeCount(60000,1000);
                time.start();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(ModifyPwdActivity.this,SystemSettingActivity.class);
                intent.putExtra("login_setting",1);
                startActivity(intent);
            }
        });

        click_put=(ImageButton)findViewById(R.id.click_in);
        click_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userpwd = new_pwd.getText().toString().trim();
                String userpwd_again = new_pwd_again.getText().toString().trim();
                String Pwd_code=check_code.getText().toString();
                String tosaltpwd = userpwd+"i^(ur6dmfe2m!jfmmbi79um5p7=h$(#q9uq18_&+z31g3t+-05";
                String md5pwd=MD5Utils.parseStrToMd532(tosaltpwd);
                if(TextUtils.isEmpty(userpwd)||TextUtils.isEmpty(userpwd_again)||TextUtils.isEmpty(Pwd_code)){
                    BToast.showText(ModifyPwdActivity.this,"有输入项数据为空，请输入后重新提交");
                }
                else if(!userpwd.equals(userpwd_again)) {
                    BToast.showText(ModifyPwdActivity.this,"两次输入密码不一致",false);
                }else {
                    modify_pwd(myapp.getUser_email(), Pwd_code, md5pwd);  //提交修改信息请求
                }
            }
        });
    }

    /**
     *
     * @param email
     * @param code
     * @param md5pwd
     */
    public void modify_pwd(final String email, final String code, final String md5pwd){
        time.start();
        try {
            String url= new PropertyUri().host+"app/modifyPasswd/";
            Log.d("url",url);
            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String data = jsonObject.getString("data");
                        Integer code = (Integer) jsonObject.get("code");
                        Toast.makeText(ModifyPwdActivity.this,data,Toast.LENGTH_SHORT).show();
                        if(code==0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(ModifyPwdActivity.this);
                            builder.setTitle("修改密码成功").setMessage("请重新登录").setCancelable(false);
                            builder.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            editor=pref.edit();
                                            editor.clear();
                                            editor.apply();
                                            Intent intent=new Intent();
                                            intent.setClass(ModifyPwdActivity.this,MainActivity.class);
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
                        }else{
                            BToast.showText(ModifyPwdActivity.this,"修改密码失败",false);
                        }
                    }catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ModifyPwdActivity.this,"'网络请求失败",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_email", email);
                    params.put("new_passwd",md5pwd);
                    params.put("check_code",code);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String sesseionid = new ConnectViaSession(ModifyPwdActivity.this).GetSession();
                    if (!sesseionid.equals("")) {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("cookie", sesseionid);
                        return headers;
                    }else {
                        return super.getHeaders();
                    }

                }
            };
            requestQueue.add(jsonObjectRequest);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取验证码
     * @param email
     */
    public void getCheckcode(final String email){
        try {
            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,new PropertyUri()+"app/sendCheckCode/",new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String data = jsonObject.getString("data");
                        Integer code = (Integer) jsonObject.get("code");

                        Toast.makeText(ModifyPwdActivity.this,"验证码"+data,Toast.LENGTH_SHORT).show();



                    }catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ModifyPwdActivity.this,"'网络请求失败",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_email", email);
                    return params;
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    new ConnectViaSession(ModifyPwdActivity.this).getSession(response);
                    return super.parseNetworkResponse(response);
                }
            };
            requestQueue.add(jsonObjectRequest);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture,long countDownInterval){
            super(millisInFuture,countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            button_getCode.setClickable(false);
            button_getCode.setText(millisUntilFinished/1000+"秒");
        }

        @Override
        public void onFinish() {
            button_getCode.setClickable(true);
           button_getCode.setText("重新获取");
        }
    }
}
