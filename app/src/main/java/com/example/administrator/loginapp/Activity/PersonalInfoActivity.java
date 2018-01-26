package com.example.administrator.loginapp.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.loginapp.Bean.MyUser;
import com.example.administrator.loginapp.R;
import com.example.administrator.loginapp.Utils.ActivityCollector;
import com.example.administrator.loginapp.Utils.SharedPrefUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "PersonalInfoActivity";
    TextView getPhone;
    TextView getCode;
    Button btn_getCode;
    Button loginOut;
    Button bindPhone;
    TextView tv_qqlongin;
    LinearLayout isBindPhone;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_personal_info;
    }


    @Override
    protected void init() {
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true);


        }
        initViews();

    }


    private void initViews() {
        isBindPhone= (LinearLayout) findViewById(R.id.isBindPhone);
        loginOut = (Button) findViewById(R.id.btn_loginOut);
        bindPhone = (Button) findViewById(R.id.btn_bindPhone);
        getPhone = (TextView) findViewById(R.id.edt_getPhone);
        btn_getCode = (Button) findViewById(R.id.btn_getCode);
        getCode = (TextView) findViewById(R.id.edt_getCode);
        tv_qqlongin = (TextView) findViewById(R.id.tv_qqlongin);
        tv_qqlongin.setText(LoginActivity.nickname);
        btn_getCode.setOnClickListener(this);
        loginOut.setOnClickListener(this);
        bindPhone.setOnClickListener(this);
    }

    @Override
    protected void initListener() {
        checkBindPhoneState();


    }

    private void checkBindPhoneState() {
        MyUser myUser=BmobUser.getCurrentUser(MyUser.class);
        String phone=null;
        if(myUser!=null){
            phone =myUser.getMobilePhoneNumber();
        }

        if(phone!=null || myUser==null){
            isBindPhone.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                break;


        }
        return true;
    }


    @Override
    public void onClick(View v) {
        String phone = getPhone.getText().toString();
        String model = "您的验证码是`%smscode%`，有效期为`%ttl%`分钟。您正在使用`%appname%`的验证码";
        String code = getCode.getText().toString();
        switch (v.getId()) {
            case R.id.btn_loginOut:
                LoginOut();

                break;
            case R.id.btn_bindPhone:
                bindPhone(phone, code);

                break;
            case R.id.btn_getCode:

                getPhoneCode(phone, model);


                break;

        }
    }
    //输入验证码开始绑定手机号
    private void bindPhone(final String phone, String code) {
        BmobSMS.verifySmsCode(phone, code, new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if (ex == null) {//短信验证码已验证成功
                    Log.i("smile", "验证通过");

                    MyUser user = new MyUser();
                    user.setMobilePhoneNumber(phone);
                    user.setMobilePhoneNumberVerified(true);
                    MyUser cur = BmobUser.getCurrentUser(MyUser.class);
                    user.update(cur.getObjectId(), new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(PersonalInfoActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                            } else {
                                //  toast("失败:" + e.getMessage());
                                Toast.makeText(PersonalInfoActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Log.i("smile", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                }
            }
        });
    }


    //获取绑定手机号的验证码
    private void getPhoneCode(String phone, String model) {
        BmobSMS.requestSMSCode(phone, model, new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId, BmobException ex) {
                if (ex == null) {//验证码发送成功
                    Log.i("smile", "短信id：" + smsId);//用于查询本次短信发送详情
                    Toast.makeText(PersonalInfoActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void LoginOut() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        Log.e(TAG, "LoginOut: 1" );
        if (user != null) {
            Log.e(TAG, "LoginOut: 2" );
            BmobUser.logOut();   //清除缓存用户对象
            // BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
            ActivityCollector.finishActivity();
            Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PersonalInfoActivity.this, LoginActivity.class));
        }else {
            Log.e(TAG, "LoginOut: 3" );
            if(LoginActivity.mTencent==null){
                Log.e(TAG, "LoginOut: 4" );
               SharedPrefUtil.removePassWoldOrUserName(PersonalInfoActivity.this,"nickName");
                ActivityCollector.finishActivity();
                Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PersonalInfoActivity.this, LoginActivity.class));
            }else {
                Log.e(TAG, "LoginOut: 5" );
                            //清空缓存的登录状态并退出登录
                SharedPrefUtil.removePassWoldOrUserName(PersonalInfoActivity.this,"nickName");
            String nickname = SharedPrefUtil.getMoreValuesBoolean(getApplicationContext());
            // Toast.makeText(this, nickname, Toast.LENGTH_SHORT).show();
            if (!nickname.equals("夜听海雨")) {
                Log.e(TAG, "LoginOut:6" );

                if (LoginActivity.mTencent.isSessionValid()) {
                    Log.e(TAG, "LoginOut: 7" );

                    //  Toast.makeText(this, "mTencent", Toast.LENGTH_SHORT).show();
                    LoginActivity.mTencent.logout(PersonalInfoActivity.this);
                }
                Log.e(TAG, "LoginOut:8" );
                ActivityCollector.finishActivity();
                Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PersonalInfoActivity.this, LoginActivity.class));

            } else {
                Log.e(TAG, "LoginOut: 9" );
                Toast.makeText(this, "退出不成功" + nickname, Toast.LENGTH_SHORT).show();
            }

            }


        }

    }


    //同步登录成功后所在本地缓存的用户信息；
    private void fetchUserInfo() {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    // Log.e("Newest UserInfo is " + s);
                } else {
                    //  Log.e(e);
                }
            }
        });
    }

    //很多情况下你可能需要修改用户信息，比如你的应用具备修改个人资料的功能，Bmob提供的用户更新方式有两种写法：
    // 第一种：新建一个用户对象，并调用update(objectId,updateListener)方法来更新（推荐使用），示例：
    private void changeUserData() {
        MyUser myUser = new MyUser();
        myUser.setEmail("");
        //已经登录的状态下，否则会报错-start
        BmobUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
        //-end
        myUser.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //toast("更新用户信息成功");
                } else {
                    // toast("更新用户信息失败:" + e.getMessage());
                }
            }
        });
    }

}
