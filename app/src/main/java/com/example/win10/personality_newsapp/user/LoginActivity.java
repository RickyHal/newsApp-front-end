package com.example.win10.personality_newsapp.user;

import android.content.DialogInterface;
import android.content.Intent;
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
          /*if(et_username.getText().toString()!=""){
              button.setColorFilter(0xD81B1B);


          }*/
        et_username.setText("944981730@qq.com");
        et_userpwd.setText("123");
          button.setOnClickListener(this);
          back.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click_in:
                String username = et_username.getText().toString();
                String userpwd = et_userpwd.getText().toString();
                String tosaltpwd = userpwd+"i^(ur6dmfe2m!jfmmbi79um5p7=h$(#q9uq18_&+z31g3t+-05";
                String md5pwd=MD5Utils.parseStrToMd532(tosaltpwd);
                getLogincode(username,md5pwd);
               // Log.d("pwd",md5pwd);
                /*if (statuscode==1) {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(LoginActivity.this);
                    dialog.setMessage("登录成功");
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(LoginActivity.this,LoginedActivity.class);
                            startActivity(intent);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "账号和密码不匹配，请重新输入", Toast.LENGTH_SHORT).show();
                }*/
                break;
            case R.id.login_back:
                Intent intent1=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    public int getLogincode(String username,String pwd){
        try {
            final String username1=username;
            final String pwd1=pwd;
            // final String requestBody="user_email="+username+"&user_passwd="+pwd;
           //Log.d("test",pwd1);
            //JSONObject paramJsonObject = new JSONObject(params);
            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,"http://120.77.144.237/app/logIn/",new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray data = jsonObject.getJSONArray("data");
                        Integer code = (Integer) jsonObject.get("code");
                        if (code == 0) {
                            for (int i = 0; i < 1; i++) {
                                JSONObject item = data.getJSONObject(i);
                                Integer id=(Integer) item.get("user_id");
                                String name=item.getString("user_name");
                                String email=item.getString("user_email");
                                String birth=item.getString("user_birth");
                                String location=item.getString("user_location");
                                Integer sex=(Integer) item.get("user_gender");
                                String picture=item.getString("user_avatar_url");
                                myapp=(Myapp)getApplication();
                                myapp.setUser_id(id);
                                myapp.setUser_name(name);
                                myapp.setUser_avatar_url(picture);
                                myapp.setUser_birth(birth);
                                myapp.setUser_gender(sex);
                                myapp.setUser_location(location);
                                myapp.setUser_email(email);
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
