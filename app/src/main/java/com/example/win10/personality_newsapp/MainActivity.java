package com.example.win10.personality_newsapp;

import android.app.LocalActivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.win10.personality_newsapp.user.ViewpagerAdapter;
import com.example.win10.personality_newsapp.user.homeFragment;
import com.example.win10.personality_newsapp.user.userActivity;
import com.example.win10.personality_newsapp.user.userFragment;
import com.example.win10.personality_newsapp.user.videoFragment;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                JCVideoPlayerStandard.releaseAllVideos();
                if(menuItem!=null){
                   menuItem.setChecked(false);
               }else{
                   navigationView.getMenu().getItem(0).setChecked(false);
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
        adapter.addFragment(new userFragment());
        viewPager.setAdapter(adapter);
    }

}
