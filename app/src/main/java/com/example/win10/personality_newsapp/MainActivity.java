package com.example.win10.personality_newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.win10.personality_newsapp.user.Myapp;
import com.example.win10.personality_newsapp.user.ViewpagerAdapter;
import com.example.win10.personality_newsapp.user.homeFragment;
import com.example.win10.personality_newsapp.user.loginedFragment;
import com.example.win10.personality_newsapp.user.userFragment;
import com.example.win10.personality_newsapp.user.videoFragment;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MenuItem menuItem;
    private  Boolean back1=false;
    private  Boolean exit_return=false;
    Myapp myapp;
    long exitTime=0;
    Boolean loginStatus;
    Boolean isFirstLogin;
    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref=getSharedPreferences("userData", Context.MODE_PRIVATE);
        loginStatus=pref.getBoolean("loginStatus",false);
        isFirstLogin=pref.getBoolean("isFirstLogin",false);
        Integer id= pref.getInt("user_id",0);
        if(loginStatus){
        myapp=(Myapp)getApplication();
        myapp.setUser_id(id);
        }
        viewPager = findViewById(R.id.viewpager);
        navigationView = findViewById(R.id.bottom_menu);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            /**
             * 侧滑监听
             * @param i
             */
            @Override
            public void onPageSelected(int i) {
               if(menuItem!=null){
                   menuItem.setChecked(false);
               }else{
                   if(myapp.getUser_id()!=null){
                       navigationView.getMenu().getItem(2).setChecked(false);
                   }else{
                   navigationView.getMenu().getItem(0).setChecked(false);
                   }
               }
               menuItem=navigationView.getMenu().getItem(i);
               menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setupViewPager(viewPager);

    }

    /**
     * 底部导航栏点击切换
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.item_bottom_1:
                    JCVideoPlayerStandard.releaseAllVideos();
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.item_bottom_2:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.item_bottom_3:
                    JCVideoPlayerStandard.releaseAllVideos();
                       viewPager.setCurrentItem(2);

                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    private void setupViewPager(ViewPager viewPager){
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new homeFragment());
        adapter.addFragment(new videoFragment());
        userFragment userfragment=new userFragment();
        adapter.addFragment(userfragment);
         myapp=(Myapp)getApplication();
        if(loginStatus){
        adapter.addFragment(new loginedFragment());
            adapter.delFragment(userfragment);

        }
        viewPager.setAdapter(adapter);
        if(isFirstLogin){
        viewPager.setCurrentItem(2);
        SharedPreferences.Editor editor=getSharedPreferences("userData",MODE_PRIVATE).edit();
        editor.remove("isFirstLogin");
        editor.apply();
        }
        if(back1=getIntent().getBooleanExtra("back",false)){
            viewPager.setCurrentItem(2);
        }
        if(exit_return=getIntent().getBooleanExtra("exit",false)){
            viewPager.setCurrentItem(2);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK  || event.getAction() == KeyEvent.ACTION_DOWN)
        {

            if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(MainActivity.this, "再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onBackPressed() {


            if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(MainActivity.this, "再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }


        }

}
