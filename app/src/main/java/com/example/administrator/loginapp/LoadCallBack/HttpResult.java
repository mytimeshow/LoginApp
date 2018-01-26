package com.example.administrator.loginapp.LoadCallBack;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class HttpResult {
    public int resultCode;
    public List<BmobObject> data;


    public HttpResult(int resultCode, List<BmobObject> data) {
        this.resultCode = resultCode;
        this.data = data;
    }




    public int getResultCode() {
        return resultCode;
    }

    public List<BmobObject> getData() {
        return data;
    }
}

