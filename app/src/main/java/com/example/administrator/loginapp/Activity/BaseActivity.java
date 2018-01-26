package com.example.administrator.loginapp.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.loginapp.LoadCallBack.EmptyCallBack;
import com.example.administrator.loginapp.LoadCallBack.ErrorCallBack;
import com.example.administrator.loginapp.LoadCallBack.LoadingCallBack;
import com.example.administrator.loginapp.LoadCallBack.LoginCallBack;
import com.example.administrator.loginapp.LoadCallBack.PostUtil;
import com.example.administrator.loginapp.R;
import com.example.administrator.loginapp.Utils.ActivityCollector;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.Convertor;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;

public abstract class BaseActivity extends AppCompatActivity {
    GestureDetector gestureDetector;
    public LoadService loadService;
    public LoadService loadService1;
    public ProgressDialog mproDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Bmob.initialize(this, "5a718246416220622bae432f00a3b3bb");
        setToolbar();
        init();
        initListener();
        ActivityCollector.addActivity(this);
        gestureDetector=new GestureDetector(BaseActivity.this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
               // Toast.makeText(BaseActivity.this, "单击事件", Toast.LENGTH_SHORT).show();
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
               // Toast.makeText(BaseActivity.this, "双击事件", Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
               // Toast.makeText(BaseActivity.this, "长按事件", Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected abstract void initListener();

    protected abstract void init() ;


    protected abstract int getLayoutRes() ;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return gestureDetector.onTouchEvent(event);
    }

    private void setToolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }



    protected void LoadingPager(final int index){

           LoadSir loadSir = new LoadSir.Builder()
                   .addCallback(new LoadingCallBack())
                   .addCallback(new EmptyCallBack())
                   .addCallback(new ErrorCallBack())
                   .addCallback(new LoginCallBack())
                   .setDefaultCallback(LoadingCallBack.class)
                   .build();
           loadService = loadSir.register(this, new Callback.OnReloadListener() {
               @Override
               public void onReload(View v) {
                   loadService.showCallback(LoadingCallBack.class);

                   PostUtil.postCallbackDelayed(loadService,SuccessCallback.class);
               }
           }, new Convertor<Integer>() {
               @Override
               public Class<? extends Callback> map(Integer index) {
                   Class<? extends Callback> resultCode = SuccessCallback.class;
                   switch (index) {
                       case 1://成功回调
                           resultCode = SuccessCallback.class;
                           break;
                       case 0:
                           resultCode = EmptyCallBack.class;
                           break;
                       case 2:
                           resultCode = LoadingCallBack.class;
                           break;
                       case -1:
                           resultCode = ErrorCallBack.class;
                           break;
                   }
                   return resultCode;
               }
           });

        // loadService.showWithConvertor(mHttpResult);
        //loadService.showSuccess();

        if(index<2){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // do net here...

                    //callback
                    loadService.showWithConvertor(index);

                }
            },500);
        }
    }
    protected void showProgressDialog(){
        mproDialog=new ProgressDialog(this);
        mproDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mproDialog.setMessage("处理中...");
        mproDialog.setCancelable(true);
        mproDialog.setCanceledOnTouchOutside(false);
        mproDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mproDialog.dismiss();
            }
        });
        mproDialog.show();
    }


    protected void Login(final int index){

        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new LoadingCallBack())
                .addCallback(new EmptyCallBack())
                .addCallback(new ErrorCallBack())
                .addCallback(new LoginCallBack())
                .setDefaultCallback(LoginCallBack.class)
                .build();
        loadService1 = loadSir.register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService1.showCallback(LoadingCallBack.class);

                PostUtil.postCallbackDelayed(loadService1,SuccessCallback.class);
            }
        }, new Convertor<Integer>() {
            @Override
            public Class<? extends Callback> map(Integer index) {
                Class<? extends Callback> resultCode = SuccessCallback.class;
                switch (index) {
                    case 1://成功回调
                        resultCode = SuccessCallback.class;
                        break;
                    case 0:
                        resultCode = EmptyCallBack.class;
                        break;
                    case 2:
                        resultCode = LoadingCallBack.class;
                        break;
                    case -1:
                        resultCode = ErrorCallBack.class;
                        break;
                }
                return resultCode;
            }
        });

        // loadService.showWithConvertor(mHttpResult);
        //loadService.showSuccess();

        if(index<2){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // do net here...

                    //callback
                    loadService1.showWithConvertor(index);

                }
            },500);
        }
    }

}
