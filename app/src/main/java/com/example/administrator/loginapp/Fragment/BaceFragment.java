package com.example.administrator.loginapp.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.loginapp.LoadCallBack.EmptyCallBack;
import com.example.administrator.loginapp.LoadCallBack.ErrorCallBack;
import com.example.administrator.loginapp.LoadCallBack.LoadingCallBack;
import com.example.administrator.loginapp.LoadCallBack.PostUtil;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.Convertor;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public abstract class BaceFragment extends Fragment{
    public LoadService loadService;
    protected View rootView;
    protected Activity mActivity;

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
}
