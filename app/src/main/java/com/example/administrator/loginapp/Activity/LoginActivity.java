package com.example.administrator.loginapp.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.loginapp.Bean.MyUser;
import com.example.administrator.loginapp.R;
import com.example.administrator.loginapp.Utils.SharedPrefUtil;
import com.example.administrator.loginapp.Views.CheckBox;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    EditText mEtPhone;  //手机号
    EditText mEtPassword;  //密码
    CheckBox mIsPasswordMemory;  //是否记住密码
    TextView mTvForgetPassword; //忘记密码
    JSONObject jo;
    TextView mRegisterAccount;  //注册账号

    ImageView mQqLogin;  //qq登录
    ImageView mSinaLogin;  //新浪登录
    ImageView mWxLogin;  //微信登录
    RelativeLayout btn_login;
    private String mUserName;
    private String mPassWord;
    public static String nickname;
    private RelativeLayout mReLogin;
    private String mName;
    private String mPwd;
    public static Tencent mTencent;
    private BaseUiListener mUiListener;
    private static final String APP_ID = "1106405289";//官方获取的APPID
    private UserInfo mUserInfo = null;
    private boolean isChecked=false;//默认没有选中记住密码

    @Override
    protected void initListener() {

    }

    @Override
    protected void init() {
        initViews();
        //记住用户名和密码的自动填写
        autoWritePasAndUser();

    }

    private void autoWritePasAndUser() {
        if(SharedPrefUtil.getPassWoldOrUserName(this,"username")!=null){
            Log.e(TAG, "autoWritePasAndUser: 1" );
            mIsPasswordMemory.setChecked(SharedPrefUtil.getBoolean(this,"isChecked",false));
            mEtPhone.setText(SharedPrefUtil.getPassWoldOrUserName(this,"username"));
            mEtPassword.setText(SharedPrefUtil.getPassWoldOrUserName(this,"passwold"));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void initViews() {
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        // QQ的初始化
        mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());
        // mReLogin = (RelativeLayout) findViewById(re_login);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mIsPasswordMemory = (CheckBox) findViewById(R.id.is_password_memory);
       mIsPasswordMemory.setChecked(isChecked);
        //  boolean isSelect = PreferencesUtils.getBoolean(LoginActivity.this, "is_select");
        //  mIsPasswordMemory.setChecked(isSelect);
        mTvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
        mTvForgetPassword.setOnClickListener(this);
        mRegisterAccount = (TextView) findViewById(R.id.register_account);
        mRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //输入密码并检查通过后登录
        btn_login = (RelativeLayout) findViewById(R.id.re_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showProgressDialog();
                Login(2);
                mName = mEtPhone.getText().toString().trim();
                mPwd = mEtPassword.getText().toString().trim();
                //判断是否点击记住密码功能并实现相应操作
                if(mIsPasswordMemory.isChecked()){
                    Log.e(TAG, "checkbox1 " );
                    isChecked=true;
                    SharedPrefUtil.saveBoolean(LoginActivity.this,"isChecked",isChecked);
                    SharedPrefUtil.savePassWoldOrUserName(LoginActivity.this,"username",mName);
                    SharedPrefUtil.savePassWoldOrUserName(LoginActivity.this,"passwold",mPwd);
                }else {
                    Log.e(TAG, "checkbox2 " );
                    SharedPrefUtil.removePassWoldOrUserName(LoginActivity.this,"isChecked");
                    SharedPrefUtil.removePassWoldOrUserName(LoginActivity.this,"username");
                    SharedPrefUtil.removePassWoldOrUserName(LoginActivity.this,"passwold");
                }

                MyUser myUser = new MyUser();
                myUser.setUsername(mName);
                myUser.setPassword(mPwd);
                myUser.login(new SaveListener<Object>() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if (e == null) {
                            MyUser user = BmobUser.getCurrentUser(MyUser.class);
                            nickname= user.getNick();
                           // Toast.makeText(LoginActivity.this, user.getNick(), Toast.LENGTH_SHORT).show();
                            //mproDialog.dismiss();
                            loadService1.showWithConvertor(1);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                           /// Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        } else {
                            loadService1.showSuccess();
                           // mproDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                            Log.e("BBB", e.toString() + e.getMessage());
                        }
                    }
                });
            }
        });


        mQqLogin = (ImageView) findViewById(R.id.qq_login);
        mQqLogin.setOnClickListener(this);
        mSinaLogin = (ImageView) findViewById(R.id.sina_login);
        mWxLogin = (ImageView) findViewById(R.id.wx_login);
        //  String username = PreferencesUtils.getString(this, "user");
        //  String password = PreferencesUtils.getString(this, "pwd");
//        if (!TextUtils.isEmpty(username)) {
//            mEtPhone.setText(username);
//        }
//        if (!TextUtils.isEmpty(password)) {
//            mEtPassword.setText(password);
//        }

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login2;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_account:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
               // Toast.makeText(this, "chick", Toast.LENGTH_SHORT).show();
                break;

            case R.id.re_login:
                mName = mEtPhone.getText().toString().trim();
                mPwd = mEtPassword.getText().toString().trim();

                break;

            case R.id.tv_forget_password:
                startActivity(new Intent(LoginActivity.this, ChangePassWordActivity.class));
                break;

            case R.id.qq_login:
                //调用QQ登录
                mUiListener = new BaseUiListener();
                //all表示获取所有权限
                if (!mTencent.isSessionValid()) {
                    mTencent.login(this, "all", mUiListener);
                    if (mTencent.isSessionValid()) {

                        Toast.makeText(this, nickname, Toast.LENGTH_SHORT).show();

                    }
                }
                break;


        }
    }

    private void loginByemailAccount() {
        //  新增邮箱+密码登录方式,可以通过loginByAccount方法来操作：
        //邮箱号
        String account = null;
        String password = null;
        BmobUser.loginByAccount(account, password, new LogInListener<MyUser>() {

            @Override
            public void done(MyUser user, BmobException e) {
                if (user != null) {
                    Log.i("smile", "用户登陆成功");
                }
            }
        });
    }

    private void changePassWord() {
        // 邮箱重置密码

        // 开发者只需要求用户输入注册时的电子邮件地址即可：

        final String email = "xxx@163.com";
        BmobUser.resetPasswordByEmail(email, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //toast("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
                } else {
                    // toast("失败:" + e.getMessage());
                }
            }
        });
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            //Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e("AAA", "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(), qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        jo = (JSONObject) response;
                        Log.e("AAA", "登录成功" + response.toString());
                       // Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        try {
                            nickname = jo.getString("nickname");
                            SharedPrefUtil.saveMoreValuesBoolean(LoginActivity.this,
                                    nickname);
                            i.putExtra("nickname", nickname);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e("AAA", "登录失败" + uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e("AAA", "登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mUiListener);
        }
    }
//    public static void qqLoginOut(){
//        if(mTencent.isSessionValid()){
//            mTencent.logout(LoginActivity.this);
//        }
//    }
}
