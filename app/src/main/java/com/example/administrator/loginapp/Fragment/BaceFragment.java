package com.example.administrator.loginapp.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.loginapp.Dialog.LoadingDialog;
import com.example.administrator.loginapp.LoadCallBack.EmptyCallBack;
import com.example.administrator.loginapp.LoadCallBack.ErrorCallBack;
import com.example.administrator.loginapp.LoadCallBack.LoadingCallBack;
import com.example.administrator.loginapp.LoadCallBack.PostUtil;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.Convertor;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Li on 2017/10/25 0025.
 */

public abstract class BaceFragment extends Fragment{
    public LoadService loadService;
    protected View rootView;
    protected Activity mActivity;
    public static CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private static LoadingDialog mLoadingDialog;
    private volatile int requestLoadingDialogTimes = 0;
    private static Toast mToast;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=LayoutInflater.from(getActivity()).inflate(getLayout(),container,false);

            initView();
            initListener();
            initData();
        }

        return rootView;
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    protected abstract int getLayout() ;
    protected View LoadingPager(final int index){

        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new LoadingCallBack())
                .addCallback(new EmptyCallBack())
                .addCallback(new ErrorCallBack())
                .setDefaultCallback(LoadingCallBack.class)
                .build();
        loadService = loadSir.register(rootView, new Callback.OnReloadListener() {
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
return loadService.getLoadLayout();
    }
    private void showLoading(){
        if(mLoadingDialog==null){
            mLoadingDialog= LoadingDialog.newInstance();
        }
        if(requestLoadingDialogTimes==0){
            mLoadingDialog.show(getFragmentManager(),"loading");
        }
        requestLoadingDialogTimes++;
    }
    public  void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT);
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

    public   class NetObserver<T> implements Observer<T>{

        @Override
        public void onSubscribe(Disposable d) {
            showLoading();
        }

        @Override
        public void onNext(T t) {

        }

        @Override
        public void onError(Throwable e) {
            showError(e);
            dismissLoading();
            Log.d("d",e.toString());
        }

        @Override
        public void onComplete() {
            dismissLoading();
        }
    }

}
