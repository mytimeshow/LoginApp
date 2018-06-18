package com.example.administrator.loginapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.loginapp.Dialog.LoadingDialog;
import com.example.administrator.loginapp.LoadCallBack.EmptyCallBack;
import com.example.administrator.loginapp.LoadCallBack.ErrorCallBack;
import com.example.administrator.loginapp.LoadCallBack.LoadingCallBack;
import com.example.administrator.loginapp.LoadCallBack.LoginCallBack;
import com.example.administrator.loginapp.LoadCallBack.PostUtil;
import com.example.administrator.loginapp.R;
import com.example.administrator.loginapp.util.ActivityCollector;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.Convertor;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {
    GestureDetector gestureDetector;
    public LoadService loadService;
    public LoadService loadService1;
    public ProgressDialog mproDialog;
    public static CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private static LoadingDialog mLoadingDialog;
    private volatile int requestLoadingDialogTimes = 0;
    private static Toast mToast;
    private static Context context;


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
        context=getApplicationContext();
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
        mCompositeDisposable.dispose();
    }
    public static CompositeDisposable getmCompositeDisposable(){
        return mCompositeDisposable;
    }
    public static Activity getActivityTop(){
        return ActivityCollector.activities.get(ActivityCollector.activities.size()-1);
    }
    public static LoadingDialog getLoadingDialog(){
        return mLoadingDialog;
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
    private void showLoading(){
       if(mLoadingDialog==null){
           mLoadingDialog=LoadingDialog.newInstance();
       }
       if(requestLoadingDialogTimes==0){
           mLoadingDialog.show(getSupportFragmentManager(),"loading");
       }
        requestLoadingDialogTimes++;
    }
    public  void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
    private  void showError(Throwable e){
        if(e instanceof UnknownHostException || e instanceof SocketTimeoutException){
                showToast("网络不稳定");
        }else{
            showToast("服务器异常");
        }
    }
    private   void dismissLoading(){
        requestLoadingDialogTimes--;
        if(requestLoadingDialogTimes <= 0 && mLoadingDialog != null&&mLoadingDialog.getFragmentManager()!=null){
            mLoadingDialog.dismiss();
            requestLoadingDialogTimes=0;
            mLoadingDialog=null;
        }

    }

    public abstract class NetObserver<T> implements Observer<T>{
        @Override
        public void onNext(T t) {

        }

        @Override
        public void onComplete() {
                dismissLoading();
        }

        @Override
        public void onError(Throwable e) {
            showError(e);
            dismissLoading();

        }

        @Override
        public void onSubscribe(Disposable d) {
           getmCompositeDisposable().add(d);
            showLoading();

        }
    }

}
