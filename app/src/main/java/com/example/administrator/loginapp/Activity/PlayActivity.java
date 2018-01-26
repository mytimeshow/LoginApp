package com.example.administrator.loginapp.Activity;

import android.os.Handler;

import com.example.administrator.loginapp.LoadCallBack.HttpResult;
import com.example.administrator.loginapp.R;
import com.kingja.loadsir.core.LoadService;

public class PlayActivity extends BaseActivity {
static int date=2;
static HttpResult httpResult;
    static LoadService loadService1;
    @Override
    protected void initListener() {

    }

    @Override
    protected void init() {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadService.showWithConvertor(1);
                }
            }, 2000);
//        BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
//        bmobQuery.findObjects(new FindListener<MyUser>() {
//            @Override
//            public void done(List<MyUser> list, BmobException e) {
//                if(e==null){
//
//
//                   if(list.size()>0){
//
//                     loadService.showWithConvertor(1);
//                   }else{
//                       loadService.showWithConvertor(0);
//                   }
//                }else{
//
//                    loadService.showWithConvertor(-1);
//                }
//            }
//        });
//
    }

    private void showLoading(int date) {

        LoadingPager(date);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_play;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading(date);
    }
}
