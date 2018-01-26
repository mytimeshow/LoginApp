package com.example.administrator.loginapp.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import static cn.bmob.v3.Bmob.getApplicationContext;
import static com.example.administrator.loginapp.Activity.LoginActivity.mTencent;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class BaseUiListener implements IUiListener {
    private Context mcontext;
    private UserInfo mUserInfo = null;
    public static String nickname;
   public static JSONObject jo;

    public BaseUiListener(Context mcontext){
        this.mcontext=mcontext;

    }
    @Override
    public void onComplete(Object response) {
        Toast.makeText(mcontext, "授权成功", Toast.LENGTH_SHORT).show();
        Log.e("AAA", "response:" + response);
        JSONObject obj = (JSONObject) response;
        try {
            String openID = obj.getString("openid");
            String accessToken = obj.getString("access_token");
            String expires = obj.getString("expires_in");
            mTencent.setOpenId(openID);
            mTencent.setAccessToken(accessToken,expires);
            QQToken qqToken = mTencent.getQQToken();
            mUserInfo = new UserInfo(getApplicationContext(),qqToken);
            mUserInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object response) {
                    jo= (JSONObject) response;
                    Log.e("AAA","登录成功"+response.toString());
                    Toast.makeText(mcontext, "登录成功", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onError(UiError uiError) {
                    Log.e("AAA","登录失败"+uiError.toString());
                }

                @Override
                public void onCancel() {
                    Log.e("AAA","登录取消");

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(mcontext, "授权失败", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onCancel() {
        Toast.makeText(mcontext, "授权取消", Toast.LENGTH_SHORT).show();

    }

}

