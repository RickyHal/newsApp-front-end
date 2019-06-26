package com.example.win10.personality_newsapp.user;

import android.Manifest;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.PropertyUri;
import com.example.win10.personality_newsapp.R;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModifyUserinfoActivity extends AppCompatActivity implements View.OnClickListener{
    File outputImage;
    Myapp myapp;
    String imagePath;
    private boolean isClickCamera;
    CircleImageView circleImageView;
    ImageView back;
    LinearLayout modify_pic,modify_nickname,modify_introduce,modify_sex,modify_birth,modify_area;
    TextView textView1,textView2,textView3,textView4,textView5;
    RequestQueue requestQueue;
    private Uri imageUri;
    Uri outputUri;
    Uri uri;
    Integer pic_code;   //上传图片返回结果码
    String user_avatar_url; //上传返回图片uri地址
    String msg;          //上传返回信息
    Integer return_status;   //请求返回code
    Boolean result_modify=true;
    int checkedItem=0;
    private String[] sexSelect=new String[]{"男","女"};
    public static final int TOAST_FLAG=0;
    private static final int TAKE_PICTURE = 1;
    private static final int CHOOSE_PICTURE = 0;
    private static final int CROP_SMALL_PICTURE = 2;
    private  static final int MALE=0;
    private  static final int FEMALE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_userinfo);
        requestQueue= Volley.newRequestQueue(this);
        myapp=(Myapp)getApplication();
        SharedPreferences pref=getSharedPreferences("userData", Context.MODE_PRIVATE);
        Integer id= pref.getInt("user_id",0);
        String name=pref.getString("user_name","");
        String email=pref.getString("user_email","");
        String birth=pref.getString("user_birth","");
        String location=pref.getString("user_location","");
        Integer sex= pref.getInt("user_gender",0);
        String picture=pref.getString("user_avatar_url","");
        String introduce=pref.getString("user_introduce","");
        myapp.setUser_id(id);
        myapp.setUser_name(name);
        myapp.setUser_avatar_url(picture);
        myapp.setUser_birth(birth);
        myapp.setUser_gender(sex);
        myapp.setUser_location(location);
        myapp.setUser_email(email);
        myapp.setUser_introduce(introduce);
        circleImageView=(CircleImageView) findViewById(R.id.set_1);
        textView1=(TextView) findViewById(R.id.set_2);
        textView2=(TextView) findViewById(R.id.set_3);
        textView3=(TextView) findViewById(R.id.set_4);
        textView4=(TextView) findViewById(R.id.set_5);
        textView5=(TextView) findViewById(R.id.set_6);
        modify_pic=(LinearLayout)findViewById(R.id.modify_pic);
        modify_nickname=(LinearLayout)findViewById(R.id.modify_nickname);
        modify_introduce=(LinearLayout)findViewById(R.id.introduce);
        modify_sex=(LinearLayout)findViewById(R.id.modify_sex);
        modify_birth=(LinearLayout)findViewById(R.id.modify_birth);
        modify_area=(LinearLayout)findViewById(R.id.modify_area);
        back=(ImageView)findViewById(R.id.back) ;
        back.setOnClickListener(this);
        final ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(circleImageView,
                R.drawable.ic_launcher,R.drawable.chahao);
        imageLoader.get(myapp.getUser_avatar_url(), listener);
        textView1.setText(myapp.getUser_name());
        String introduce_text=myapp.getUser_introduce();
        if(myapp.getUser_introduce()!=null || !introduce_text.equals("")) {
            textView2.setText(myapp.getUser_introduce());
        }
        if(myapp.getUser_gender()==0){
            textView3.setText("男");
        }
        if(myapp.getUser_gender()==1){
            textView3.setText("女");
        }
        textView4.setText(myapp.getUser_birth());
        textView5.setText(myapp.getUser_location());
        modify_nickname.setOnClickListener(this);
        modify_introduce.setOnClickListener(this);
        modify_sex.setOnClickListener(this);
        modify_birth.setOnClickListener(this);
        modify_area.setOnClickListener(this);

        modify_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ModifyUserinfoActivity.this);
                String [] items={"从相册选择照片","拍照"};
                builder.setNegativeButton("取消", null);
                builder.setItems(items,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case CHOOSE_PICTURE:
                                if(ContextCompat.checkSelfPermission(ModifyUserinfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions(ModifyUserinfoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                                }else{
                                Intent openIntent=new Intent(Intent.ACTION_PICK,null);
                                    openIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                //用startActivityForResult方法，重写onActivityResult()方法，拿到图片进行裁剪操作
                                startActivityForResult(openIntent, CHOOSE_PICTURE);
                                    isClickCamera = false;
                                }

                                break;
                            case TAKE_PICTURE:
                                File file = new FileStorage().createIconFile();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    imageUri = FileProvider.getUriForFile(ModifyUserinfoActivity.this, "com.example.camera.fileprovider", file);//通过FileProvider创建一个content类型的Uri
                                } else {
                                    imageUri = Uri.fromFile(file);
                                }
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                                }
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                                startActivityForResult(intent,TAKE_PICTURE);
                                isClickCamera = true;
                                break;

                            default:
                                break;
                        }
                    }
                });

                builder.show();
            }
        });
    }

    /**
     * 运行时权限
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Intent openIntent=new Intent(Intent.ACTION_PICK);
                    openIntent.setType("image/*");
                    //用startActivityForResult方法，重写onActivityResult()方法，拿到图片进行裁剪操作
                    startActivityForResult(openIntent, CHOOSE_PICTURE);
                }else{
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    String res;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHOOSE_PICTURE:
                if (Build.VERSION.SDK_INT >= 19) {
                    handleImageOnKitKat(data);
                } else {
                    handleImageBeforeKitKat(data);
                }
                break;
            case  TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    startPhotoZoom();
                }
                break;
            case CROP_SMALL_PICTURE:
                Bitmap bitmap = null;
                try {
                    if (isClickCamera) {

                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(outputUri));
                    } else {
                        bitmap = BitmapFactory.decodeFile(imagePath);
                    }
                    circleImageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("user_id", myapp.getUser_id());
                post_file(new PropertyUri().host+"app/modifyLogo/",params, new FileStorage().out_Dir,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                       res = response.body().string();
                        //Log.i("lfq", res );
                                try {
                                    JSONObject jsonObject = new JSONObject(res);
                                    pic_code = (Integer) jsonObject.get("code");
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    user_avatar_url = data.getString("url");
                                    msg = data.getString("msg");
                                    if (pic_code == 0) {
                                                Message message=new Message();
                                                message.what=TOAST_FLAG;
                                                Bundle bundle=new Bundle();
                                                bundle.putString("user_avatar_url",user_avatar_url);
                                                bundle.putString("msg",msg);
                                                message.setData(bundle);
                                                handler.sendMessage(message);
                                            }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("lfq" ,"onFailure");
                        pic_code=1;
                        Toast.makeText(ModifyUserinfoActivity.this,call.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
               /* if(post_file("http://www.newsapp.club/app/modifyLogo/",params, new FileStorage().out_Dir)==0){
                   BToast.showText(ModifyUserinfoActivity.this,"头像修改成功",true);
                }else{
                    BToast.showText(ModifyUserinfoActivity.this,"头像修改失败",false);
                }*/
                break;
        }
    }

    /**
     * 裁剪图片方法实现
     * @param
     */
    public void startPhotoZoom() {
        File file = new FileStorage().createCropFile();
        outputUri = Uri.fromFile(file);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(imageUri, "image/*");
        //crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
    intent.putExtra("crop", true);
    // aspectX aspectY 是宽高的比例
    intent.putExtra("aspectX", 1);
    intent.putExtra("aspectY", 1);
    // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputFormat", "JPEG");
    intent.putExtra("outputX", 300);
    intent.putExtra("outputY", 300);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("noFaceDetection", true);
            intent.putExtra("return-data", false);
        startActivityForResult(intent,  CROP_SMALL_PICTURE);
}
    protected void post_file(final String url, final Map<String, Object> map, File file,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(file != null){
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("user_logo", file.getName(), body);
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(url).post(requestBody.build()).tag(ModifyUserinfoActivity.this).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(callback);
    }

    private String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void handleImageOnKitKat(Intent data){
        imagePath=null;
        imageUri = data.getData();
            if (DocumentsContract.isDocumentUri(this, imageUri)) {
                //如果是document类型的Uri,通过document id处理
                String docId = DocumentsContract.getDocumentId(imageUri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];  //解析出数字格式的id
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(imageUri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }

            } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
                //如果是content类型的Uri，直接处理
                imagePath = getImagePath(imageUri, null);
            } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
                imagePath = imageUri.getPath();
            }
                startPhotoZoom();

    }


    private void handleImageBeforeKitKat(Intent intent) {
        imageUri = intent.getData();
        imagePath = getImagePath(imageUri, null);
        startPhotoZoom();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.modify_nickname:
                final EditText inputServer = new EditText(this);
                      inputServer.setFocusable(true);
                      inputServer.setText(textView1.getText().toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("请输入用户名").setView(inputServer).setNegativeButton("取消", null);
                     builder.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {

                               public void onClick(DialogInterface dialog, int which) {
                                        String inputName = inputServer.getText().toString();
                                        Log.d("name",inputName);
                                       if((inputServer.getText().toString()).equals(textView1.getText().toString())){
                                           BToast.showText(ModifyUserinfoActivity.this,"与原来用户名相同",false);
                                       }else {
                                           modifyUserInfo(myapp.getUser_id().toString(), myapp.getUser_birth(), myapp.getUser_location(), myapp.getUser_gender().toString(), inputName, myapp.getUser_introduce(), new ModifyUserinfoActivity.VolleyCallback() {
                                               @Override
                                               public void onSuccess(Integer result) {
                                                   return_status=result;
                                                   Log.d("result",result.toString());
                                                   Handler handler=new Handler();
                                                   handler.postDelayed(new Runnable() {
                                                                           @Override
                                                                           public void run() {
                                                                               if (return_status== 0) {
                                                                                   BToast.showText(ModifyUserinfoActivity.this, "修改用户名成功", true);
                                                                               } else {
                                                                                   BToast.showText(ModifyUserinfoActivity.this, "修改用户名失败", false);
                                                                               }
                                                                           }
                                                                       },100);

                                               }

                                           });
                                       }
                               }
                              });
                        builder.show();
                      break;
            case R.id.introduce:
                final EditText inputServer1 = new EditText(this);
                inputServer1.setFocusable(true);
                inputServer1.setText(textView2.getText().toString());
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("请输入介绍").setView(inputServer1).setNegativeButton("取消", null);
                builder1.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                String inputName = inputServer1.getText().toString();
                                if((inputServer1.getText().toString()).equals(textView2.getText().toString())){
                                    BToast.showText(ModifyUserinfoActivity.this,"与原来介绍相同",false);
                                }
                                else {
                                    modifyUserInfo(myapp.getUser_id().toString(), myapp.getUser_birth(), myapp.getUser_location(), myapp.getUser_gender().toString(), myapp.getUser_name(), inputName, new ModifyUserinfoActivity.VolleyCallback() {
                                        @Override
                                        public void onSuccess(Integer result) {
                                            return_status=result;
                                            Handler handler=new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (return_status== 0) {
                                                        BToast.showText(ModifyUserinfoActivity.this,"签名修改成功",true);
                                                    } else {
                                                        BToast.showText(ModifyUserinfoActivity.this,"签名修改失败",false);
                                                    }
                                                }
                                            },100);
                                        }
                                    });
                                }
                            }
                        });
                builder1.show();
                break;
            case R.id.modify_sex:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setNegativeButton("取消", null);
                if(textView3.getText().toString().equals("女")){
                    checkedItem=1;
                }else{
                    checkedItem=0;
                }
                builder2.setSingleChoiceItems(sexSelect,checkedItem,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sex=sexSelect[which];
                        Integer sexFlag;
                        if(sex.equals("男")){
                            sexFlag=0;
                        }else{
                            sexFlag=1;
                        }
                        if(sexFlag!=checkedItem){
                            modifyUserInfo(myapp.getUser_id().toString(), myapp.getUser_birth(), myapp.getUser_location(), sexFlag.toString(), myapp.getUser_name(), myapp.getUser_introduce(),new ModifyUserinfoActivity.VolleyCallback(){
                                @Override
                                public void onSuccess(Integer result) {
                                    return_status=result;
                                    Handler handler=new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (return_status== 0) {
                                                BToast.showText(ModifyUserinfoActivity.this,"修改性别成功",true);
                                            } else {
                                                BToast.showText(ModifyUserinfoActivity.this,"修改性别失败",false);
                                            }
                                        }
                                    },100);
                                }
                            });
                        }
                       dialog.dismiss();
                    }
                });
                builder2.show();
                    break;
            case R.id.modify_birth:
                final Calendar calendar = Calendar.getInstance();
                final SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
                DatePickerDialog dialog = new DatePickerDialog(ModifyUserinfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        if(calendar.getTimeInMillis()-System.currentTimeMillis()>0){
                            BToast.showText(ModifyUserinfoActivity.this,"抱歉，日期选择有误，请重新选择",false);
                        }else if(!(textView4.getText().toString()).equals(simpleDateFormat.format(calendar.getTime()))){
                            modifyUserInfo(myapp.getUser_id().toString(), simpleDateFormat.format(calendar.getTime()), myapp.getUser_location(), myapp.getUser_gender().toString(), myapp.getUser_name(), myapp.getUser_introduce(),new ModifyUserinfoActivity.VolleyCallback(){
                                @Override
                                public void onSuccess(Integer result) {
                                    return_status=result;
                                    Handler handler=new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (return_status== 0) {
                                                BToast.showText(ModifyUserinfoActivity.this,"修改生日成功",true);
                                            } else {
                                                BToast.showText(ModifyUserinfoActivity.this,"修改生日失败",false);
                                            }
                                        }
                                    },100);
                                }
                            });
                        }

                    }
                },calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            case R.id.modify_area:
                showSelectArea();
                break;
            case R.id.back:
                Intent intent=new Intent();
                intent.setClass(ModifyUserinfoActivity.this,SystemSettingActivity.class);
                intent.putExtra("login_setting",1);
                startActivity(intent);
            default:
                break;

        }
    }

    private CityPicker mCityPicker;
    //地址选择
    private   void showSelectArea(){
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
                    if(!(textView5.getText().toString()).equals(province+city)){
                        modifyUserInfo(myapp.getUser_id().toString(), myapp.getUser_birth(), province+city, myapp.getUser_gender().toString(), myapp.getUser_name(), myapp.getUser_introduce(),new ModifyUserinfoActivity.VolleyCallback(){
                            @Override
                            public void onSuccess(Integer result) {
                                return_status=result;
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (return_status== 0) {
                                            BToast.showText(ModifyUserinfoActivity.this,"修改地区成功",true);
                                        } else {
                                            BToast.showText(ModifyUserinfoActivity.this,"修改地区失败",false);
                                        }
                                    }
                                },100);
                            }
                        });
                    }
                }
            });
        }
        mCityPicker.show();
    }
    Integer code;
    public void  modifyUserInfo(final String id, final String birthDate, final String area, final String sexState, final String name,final String introduce,final VolleyCallback callback){
        StringRequest request=new StringRequest(com.android.volley.Request.Method.POST,new PropertyUri().host+"app/modifyUserInfo/", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String data = jsonObject.getString("data");
                    code = (Integer) jsonObject.get("code");
                    callback.onSuccess(code);
                    Toast.makeText(ModifyUserinfoActivity.this,data,Toast.LENGTH_SHORT).show();
                    if(code==0){
                        textView1.setText(name);
                        textView2.setText(introduce);
                        if(sexState.equals("0")){
                            textView3.setText("男");
                        }else{
                            textView3.setText("女");
                        }

                        textView4.setText(birthDate);
                        textView5.setText(area);
                        SharedPreferences.Editor editor=getSharedPreferences("userData",MODE_PRIVATE).edit();
                        editor.putString("user_name",name);
                        editor.putString("user_birth",birthDate);
                        editor.putString("user_location",area);
                        editor.putString("user_introduce",introduce);
                        editor.putInt("user_gender",Integer.parseInt(sexState));
                        editor.apply();
                        myapp.setUser_name(name);
                        myapp.setUser_birth(birthDate);
                        myapp.setUser_gender(Integer.parseInt(sexState));
                        myapp.setUser_location(area);
                        myapp.setUser_introduce(introduce);
                    }else{
                        Toast.makeText(ModifyUserinfoActivity.this, "修改信息失败", Toast.LENGTH_SHORT).show();
                    }


                }catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifyUserinfoActivity.this,"'网络请求失败",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",id);
                params.put("user_name", name);
                params.put("user_gender",sexState);
                params.put("user_birth", birthDate);
                params.put("user_location",area);
                params.put("user_introduce",introduce);
                return params;
            }
        };
        requestQueue.add(request);
    }

     interface VolleyCallback {
        void onSuccess(Integer result);
    }
    private  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        switch (msg.what){
            case TOAST_FLAG:
                String text_msg=msg.getData().getString("msg");
                String pic_uri=msg.getData().getString("user_avatar_url");
                final ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                    }

                    @Override
                    public Bitmap getBitmap(String url) {
                        return null;
                    }
                });
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(circleImageView,
                        R.drawable.ic_launcher, R.drawable.chahao);
                imageLoader.get(user_avatar_url, listener);
                SharedPreferences.Editor editor = getSharedPreferences("userData", MODE_PRIVATE).edit();
                editor.putString("user_avatar_url", user_avatar_url);
                editor.apply();
                Toast.makeText(ModifyUserinfoActivity.this,text_msg, Toast.LENGTH_SHORT).show();
                BToast.showText(ModifyUserinfoActivity.this, "头像修改成功", true);

        }
            //Log.e("123465", "handleMessage: " + datas);
        }
    };
}
