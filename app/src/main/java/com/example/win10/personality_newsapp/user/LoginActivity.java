package com.example.win10.personality_newsapp.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.MainActivity;
import com.example.win10.personality_newsapp.PropertyUri;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.news_visit.news_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //private Button button;
    private ImageView button;
    private EditText et_username;
    private EditText et_userpwd;
    private TextView register;
    private ImageView back;
    private int flag=0;
    private Myapp myapp;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
          button= (ImageView) findViewById(R.id.click_in);
          et_username = (EditText) findViewById(R.id.et_username);
          et_userpwd =(EditText)  findViewById(R.id.et_password);
          back=(ImageView) findViewById(R.id.login_back) ;
          register=(TextView)  findViewById(R.id.btn_register);
        et_username.setText("944981730@qq.com");
        et_userpwd.setText("123");
          button.setOnClickListener(this);
          back.setOnClickListener(this);
          register.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click_in:
                String username = et_username.getText().toString();
                String userpwd = et_userpwd.getText().toString();
                String tosaltpwd = userpwd+"i^(ur6dmfe2m!jfmmbi79um5p7=h$(#q9uq18_&+z31g3t+-05";
                String md5pwd=MD5Utils.parseStrToMd532(tosaltpwd);
                getLogincode(username,md5pwd);  //登录账号密码检查
                break;
            case R.id.login_back:
                Intent intent1=new Intent(LoginActivity.this,MainActivity.class);    //登录返回
                intent1.putExtra("back",true);
                startActivity(intent1);
                break;
            case  R.id.btn_register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);  //跳转到注册页面
                startActivity(intent);
            default:
                break;
        }
    }

    public int getLogincode(String username,String pwd){
        try {
            final String username1=username;
            final String pwd1=pwd;
            // final String requestBody="user_email="+username+"&user_passwd="+pwd;
           Log.d("test",pwd);
            //JSONObject paramJsonObject = new JSONObject(params);
            /**
             * Post请求，验证账号密码，正确则返回用户数据
             */
            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,new PropertyUri().host+"app/logIn/",new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Integer code = (Integer) jsonObject.get("code");

                        if (code == 0) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < 1; i++) {
                                JSONObject item = data.getJSONObject(i);
                                Integer id=(Integer) item.get("user_id");
                                String name=item.getString("user_name");
                                String email=item.getString("user_email");
                                String birth=item.getString("user_birth");
                                String location=item.getString("user_location");
                                Integer sex=(Integer) item.get("user_gender");
                                String picture=item.getString("user_avatar_url");
                                String introduce=item.getString("user_introduce");
                                SharedPreferences.Editor editor=getSharedPreferences("userData",MODE_PRIVATE).edit();
                                editor.putBoolean("loginStatus",true);
                                editor.putBoolean("isFirstLogin",true);
                                editor.putInt("user_id",id);
                                editor.putString("user_name",name);
                                editor.putString("user_avatar_url",picture);
                                editor.putString("user_email",email);
                                editor.putString("user_birth",birth);
                                editor.putString("user_location",location);
                                editor.putString("user_introduce",introduce);
                                editor.putInt("user_gender",sex);
                                editor.apply();
                                myapp=(Myapp)getApplication();
                                myapp.setUser_id(id);
                                myapp.setUser_name(name);
                                myapp.setUser_avatar_url(picture);
                                myapp.setUser_birth(birth);
                                myapp.setUser_gender(sex);
                                myapp.setUser_location(location);
                                myapp.setUser_email(email);
                                myapp.setUser_introduce(introduce);
                            }
                            AlertDialog.Builder dialog=new AlertDialog.Builder(LoginActivity.this);
                            dialog.setMessage("登录成功");
                            dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                }
                            });
                            dialog.create();
                            dialog.show();
                        }else {
                            String data = jsonObject.getString("data");
                            Toast.makeText(LoginActivity.this, data, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                /**
                 *  Post参数设置
                 * @return
                 * @throws AuthFailureError
                 */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_email", username1);
                    params.put("user_passwd", pwd1);
                    return params;
                }
            };
            requestQueue.add(jsonObjectRequest);
            System.out.println(flag);
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }
}
