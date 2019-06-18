package com.example.win10.personality_newsapp.user;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.MainActivity;
import com.example.win10.personality_newsapp.R;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity  {
    private EditText editText;
    private ImageButton click_getcode;
    private ImageButton register_commit;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private TextView re_getcode;
    private RadioGroup sex_group;
    private RadioButton sex;
    private EditText nickname;
    private EditText identifying_code,pwd_input,pwd_input_again,se_date,se_area;
    private TimeCount time;
    private Myapp myapp;
    public static Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        View view1=getLayoutInflater().inflate(R.layout.activity_registerend,null);
        imageButton2 = (ImageButton) view1.findViewById(R.id.click_in);
        click_getcode=(ImageButton)findViewById(R.id.click_in) ;
        editText=(EditText) findViewById(R.id.et_username);
        requestQueue = Volley.newRequestQueue(this);
        click_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail_text=editText.getText().toString().trim();
                if (isEmail(mail_text)&& mail_text.length()<=31){
                    Toast.makeText(RegisterActivity.this,"邮箱验证成功",Toast.LENGTH_SHORT).show();
                    getCheckcode(mail_text);
                    goLayout1();
                }else {
                    Toast.makeText(RegisterActivity.this,"邮箱格式错误",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void getCheckcode(final String email){
        try {
            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,"http://www.newsapp.club/app/sendCheckCode/",new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String data = jsonObject.getString("data");
                        Integer code = (Integer) jsonObject.get("code");

                            Toast.makeText(RegisterActivity.this,"验证码"+data,Toast.LENGTH_SHORT).show();



                    }catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this,"'网络请求失败",Toast.LENGTH_SHORT).show();
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
                    new ConnectViaSession(RegisterActivity.this).getSession(response);
                    return super.parseNetworkResponse(response);
                }
            };
            requestQueue.add(jsonObjectRequest);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRegisterCode(final String email, final String code, final String md5pwd, final String birthDate, final String area, final String sexState, final String name){
        time.start();
        try {
            String url= "http://120.77.144.237/app/signUp/";
            Log.d("url",url);
            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String data = jsonObject.getString("data");
                        Integer code = (Integer) jsonObject.get("code");
                        Toast.makeText(RegisterActivity.this,data,Toast.LENGTH_SHORT).show();
                        if(code==0){
                           getLoginInfo(email,md5pwd);
                        }else{
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                        }


                    }catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this,"'网络请求失败",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_email", email);
                    params.put("user_passwd",md5pwd);
                    params.put("user_name", name);
                    params.put("user_gender",sexState);
                    params.put("user_birth", birthDate);
                    params.put("user_location",area);
                    params.put("check_code",code);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String sesseionid = new ConnectViaSession(RegisterActivity.this).GetSession();
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

    public void getLoginInfo(String username,String pwd){
        try {
            final String username1=username;
            final String pwd1=pwd;
            /**
             * Post请求，验证账号密码，正确则返回用户数据
             */
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
                            AlertDialog.Builder dialog=new AlertDialog.Builder(RegisterActivity.this);
                            dialog.setMessage("注册成功");
                            dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                }
                            });
                            dialog.create();
                            dialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void goLayout1(){
        setContentView(R.layout.activity_registerend);
        time = new TimeCount(6000,1000);
        time.start();
        re_getcode=(TextView)findViewById(R.id.re_getcode);
        identifying_code=(EditText)findViewById(R.id.identifying_code) ;
        pwd_input=(EditText)findViewById(R.id.pwd_input) ;
        pwd_input_again=(EditText)findViewById(R.id.pwd_input_again);
        nickname=(EditText) findViewById(R.id.nickname) ;
        se_date = (EditText) findViewById(R.id.se_date);
        register_commit=(ImageButton) findViewById(R.id.click_in) ;
        sex_group= (RadioGroup) findViewById(R.id.rg_sex) ;
        sex = (RadioButton)findViewById(sex_group.getCheckedRadioButtonId());
        se_date.setFocusable(false);
        se_date.setFocusableInTouchMode(false);
        se_area = (EditText)  findViewById(R.id.se_area);
        se_area.setFocusable(false);
        se_area.setFocusableInTouchMode(false);

        /**
         * 重新获取验证码
         */
        re_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.start();
                String mail_text=editText.getText().toString().trim();
                //Log.d("test",mail_text);+
                getCheckcode(mail_text);
            }
        });

        sex_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               sex=(RadioButton)findViewById(checkedId);
            }
        });

        pwd_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        pwd_input_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /**
         * 生日日期点击监听
         */
        se_date.setOnClickListener(new View.OnClickListener() {
            /**
             * 设置生日
             * @param v
             */
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        if(calendar.getTimeInMillis()-System.currentTimeMillis()>0){
                            BToast.showText(RegisterActivity.this,"抱歉，日期选择有误，请重新选择",false);
                        }else{
                            se_date.setText(simpleDateFormat.format(calendar.getTime()));
                            BToast.showText(RegisterActivity.this,"生日设置成功",true);
                        }

                    }
                },calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();

            }
        });
        /**
         * 地区组件的点击监听
         */
        se_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectArea();
                BToast.showText(RegisterActivity.this,"地址设置成功",true);
            }
        });

        /**
         * 注册页面点击提交按钮验证数据规范
         */
        register_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=editText.getText().toString().trim();
                String code=identifying_code.getText().toString().trim();
                String pwd=pwd_input.getText().toString().trim();
                String pwd_again=pwd_input_again.getText().toString().trim();
                String saltPwd = pwd+"i^(ur6dmfe2m!jfmmbi79um5p7=h$(#q9uq18_&+z31g3t+-05";
                String md5pwd=MD5Utils.parseStrToMd532(saltPwd);
                String name=nickname.getText().toString().trim();
                String birthDate=se_date.getText().toString().trim();
                String area= se_area.getText().toString().trim();
                String sex_text=sex.getText().toString();
                String sexState;
                Log.d("test",sex_text);
               if(!pwd.equals(pwd_again)){
                    BToast.showText(RegisterActivity.this,"两次密码输入不一致，请重新检查提交");
                }
                //判断数据是否为空
                else if(TextUtils.isEmpty(code) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwd_again) || TextUtils.isEmpty(birthDate) || TextUtils.isEmpty(area) || TextUtils.isEmpty(name)){
                    BToast.showText(RegisterActivity.this,"有输入项数据为空，请输入后重新提交");
                }else{
                    if(sex_text.equals("男")){
                        sexState="0";
                    }else{
                        sexState="1";
                    }
                    getRegisterCode(email,code,md5pwd,birthDate,area,sexState,name);
                }


                //Log.d("test",pwd);

            }
        });
    }

    class TimeCount extends CountDownTimer{

        public TimeCount(long millisInFuture,long countDownInterval){
            super(millisInFuture,countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            re_getcode.setClickable(false);
            re_getcode.setText(millisUntilFinished/1000+"秒");
        }

        @Override
        public void onFinish() {
            re_getcode.setClickable(true);
            re_getcode.setText("重新获取");
        }
    }

    //验证邮箱格式
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private CityPicker mCityPicker;
    //地址选择
    private void showSelectArea(){
        if(mCityPicker==null){
            mCityPicker = new CityPicker.Builder(this)
                    .title("请选择您的地址")  //标题
                    .textSize(14)            //字体大小
                    .titleBackgroundColor("#0388fd")  //标题背景颜色
                    .onlyShowProvinceAndCity(true)
                    .cancelTextColor("#ffffff")      //取消按钮颜色
                    .confirTextColor("#ffffff")       //确定按钮颜色
                    .province("广东省")             //默认省市县设置
                    .city("深圳市")
                    .district("无")
                    .textColor(Color.parseColor("#ff0000"))
                    .provinceCyclic(false)
                    .cityCyclic(false)
                    .districtCyclic(false)
                    .itemPadding(10)    //条目间距
                    .visibleItemsCount(7)
                    .build();
            mCityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                @Override
                public void onSelected(String... citySelected) {
                    String province=citySelected[0];  //省
                    String city=citySelected[1];     //市
                    String  distric=citySelected[2];  //区县

                    se_area.setText(province+city);
                }
            });
        }
        mCityPicker.show();
    }

}
