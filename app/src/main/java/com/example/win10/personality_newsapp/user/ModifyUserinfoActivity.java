package com.example.win10.personality_newsapp.user;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyUserinfoActivity extends AppCompatActivity {

    Myapp myapp;
    CircleImageView circleImageView;
    TextView textView1,textView2,textView3,textView4,textView5;
    RequestQueue requestQueue;
    private Uri imageUri;
    private String          picPath;
    private File            mOutImage;
    private static final int TAKE_PICTURE = 1;
    private static final int CHOOSE_PICTURE = 0;
    private static final int CROP_SMALL_PICTURE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_userinfo);
        requestQueue= Volley.newRequestQueue(this);
        myapp=(Myapp)getApplication();
        circleImageView=(CircleImageView) findViewById(R.id.set_1);
        textView1=(TextView) findViewById(R.id.set_2);
        textView2=(TextView) findViewById(R.id.set_3);
        textView3=(TextView) findViewById(R.id.set_4);
        textView4=(TextView) findViewById(R.id.set_5);
        textView5=(TextView) findViewById(R.id.set_6);
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
        textView2.setText(myapp.getUser_introduce());
        if(myapp.getUser_gender()==0){
            textView3.setText("男");
        }
        if(myapp.getUser_gender()==1){
            textView3.setText("女");
        }
        textView4.setText(myapp.getUser_birth());
        textView5.setText(myapp.getUser_location());

        circleImageView.setOnClickListener(new View.OnClickListener() {
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
                                }

                                break;
                            case TAKE_PICTURE:
                                File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                                try{
                                    if(outputImage.exists()){
                                        outputImage.delete();
                                    }
                                    outputImage.createNewFile();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                                if(Build.VERSION.SDK_INT>=24){
                                    imageUri= FileProvider.getUriForFile(ModifyUserinfoActivity.this,"com.example.camera.fileprovider",outputImage);
                                }else{
                                    imageUri=Uri.fromFile(outputImage);
                                }
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                                startActivityForResult(intent,TAKE_PICTURE);
                                /*Intent openCamreaIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                tmpUri=Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"test_image.jpg"));
                                // 将拍照所得的相片保存到SD卡根目录openCamreaIntent.putExtra(MediaStore.EXTRA_OUTPUT, tmpUri);
                                startActivityForResult(openCamreaIntent, TAKE_PICTURE);*/


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHOOSE_PICTURE:
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
            case  TAKE_PICTURE:
                startPhotoZoom(imageUri);
                break;
            case CROP_SMALL_PICTURE:
                // 直接拿到一张图片
                final Bitmap bitmap = data.getParcelableExtra("data");
                File picFile = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".png");
                // 把bitmap放置到文件中
                // format 格式
                // quality 质量
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(
                            picFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                /*// 上传服务器
                HttpUtils httpUtils = new HttpUtils();
                // 必须post get 1k
                // 请求方式 请求地址 请求参数 回调
                RequestParams params = new RequestParams();
                params.addBodyParameter("files", picFile);

                httpUtils
                        .send(HttpMethod.POST,
                                "http://169.254.161.66:8080/imageupload/servlet/UploadServlet",
                                params, new RequestCallBack<String>() {

                                    @Override
                                    public void onFailure(HttpException arg0,
                                                          String arg1) {
                                        Toast.makeText(MainActivity.this, "上传失败", 0)
                                                .show();

                                    }
                                    @Override
                                    public void onSuccess(ResponseInfo<String> arg0) {
                                        Toast.makeText(MainActivity.this, "上传成功", 0)
                                                .show();

                                        imageView.setImageBitmap(bitmap);

                                    }
                                });*/

                break;
        }
    }

    /**
     * 裁剪图片方法实现
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
      //crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
    intent.putExtra("crop", true);
    // aspectX aspectY 是宽高的比例
    intent.putExtra("aspectX", 1);
    intent.putExtra("aspectY", 1);
    // outputX outputY 是裁剪图片宽高
    intent.putExtra("outputX", 300);
    intent.putExtra("outputY", 300);
    intent.putExtra("return-data", true);
    startActivityForResult(intent,  CROP_SMALL_PICTURE);
}

/**
 * 保存裁剪之后的图片数据
 * @param picdata
 */
       /* private void setPicToView(Intent picdata) {
            Bundle extras = picdata.getExtras();
            if (extras != null) {
                // 取得SDCard图片路径做显示
                Bitmap photo = extras.getParcelable(data);
                Drawable drawable = new BitmapDrawable(null, photo);
                urlpath = FileUtil.saveFile(mContext, temphead.jpg, photo);
                avatarImg.setImageDrawable(drawable);


            }
        }*/
}
