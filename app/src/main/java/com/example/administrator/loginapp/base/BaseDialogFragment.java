package com.example.administrator.loginapp.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.loginapp.Activity.BaseActivity;

/**
 * Created by wuzihong on 2017/9/11.
 * 自定义对话框基础类
 */

public abstract class BaseDialogFragment extends DialogFragment {
    private final int MARGIN = 40;//对话框左右边距
    protected Context context;
    protected BaseActivity activity;
    protected View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (BaseActivity) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        initWindow(getDialog().getWindow());
    }

    /**
     * 初始化窗口
     */
    protected void initWindow(Window window) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        window.setLayout((int) (dm.widthPixels - 2 * MARGIN * dm.density), WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
