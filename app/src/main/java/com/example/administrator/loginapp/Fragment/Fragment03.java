package com.example.administrator.loginapp.Fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.administrator.loginapp.Activity.ByteRecordActivity;
import com.example.administrator.loginapp.Activity.FileRecordActivity;
import com.example.administrator.loginapp.R;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class Fragment03 extends BaceFragment implements View.OnClickListener{
    private Button btn_file;
    private Button btn_byte;

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        btn_file= (Button) rootView.findViewById(R.id.btn_file);
        btn_byte= (Button) rootView.findViewById(R.id.btn_byte);
        btn_file.setOnClickListener(this);
        btn_byte.setOnClickListener(this);

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_03;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_file:
                startActivity(new Intent(getContext(), FileRecordActivity.class));
                break;
            case R.id.btn_byte:
                startActivity(new Intent(getActivity(), ByteRecordActivity.class));
                default:
                    break;
        }

    }
}
