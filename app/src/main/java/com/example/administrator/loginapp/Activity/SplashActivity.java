package com.example.administrator.loginapp.Activity;

import android.content.Intent;
import android.os.Handler;

import com.example.administrator.loginapp.Bean.MyUser;
import com.example.administrator.loginapp.R;
import com.example.administrator.loginapp.util.SharedPrefUtil;

import cn.bmob.v3.BmobUser;

public class SplashActivity extends BaseActivity {
    private Handler mHandler = new Handler();

    @Override
    protected void initListener() {

    }

    @Override
    protected void init() {
      mHandler.postDelayed(new Runnable() {
          @Override
          public void run() {
              MyUser mu= BmobUser.getCurrentUser(MyUser.class);

              // 读取不到key为firstRun的值，则默认返回true，表示第一次启动应用
              boolean firstRun = SharedPrefUtil.getBoolean(
                      getApplicationContext(), "firstRun", true);
              String nickname=SharedPrefUtil.getMoreValuesBoolean(getApplicationContext());


              if(mu!=null || nickname.equals("夜听海雨")){

                  Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                  startActivity(intent);
                  finish();
              }else{
                  startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                  finish();
              }

          }
      }, 2000);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }
    private void checkLoginState() {
        MyUser mu= BmobUser.getCurrentUser(MyUser.class);
        if(mu!=null){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
