package com.example.administrator.loginapp.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.administrator.loginapp.R;


import butterknife.ButterKnife;


/**
 * Created by wuzihong on 2017/9/18.
 * 加载框
 */

public class LoadingDialog extends BaseDialogFragment {
    private
    ImageView iv_loading;

    public static LoadingDialog newInstance() {
        return new LoadingDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.dialog_loading, container);
            iv_loading= (ImageView) rootView.findViewById(R.id.iv_loading);
            ButterKnife.bind(this, rootView);
            initView();
        }
        return rootView;
    }

    @Override
    protected void initWindow(Window window) {
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void initView() {
        iv_loading.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.anim_loading));
    }
}
