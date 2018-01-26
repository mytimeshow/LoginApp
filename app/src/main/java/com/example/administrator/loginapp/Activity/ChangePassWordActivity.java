package com.example.administrator.loginapp.Activity;

import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.loginapp.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ChangePassWordActivity extends BaseActivity implements View.OnClickListener{
    TextView change_getPhone;
    TextView change_getCode;
    TextView change_getPassWord;
    Button btn_getCode;
    Button change_passWord;

    @Override
    protected void initListener() {

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
    protected void init() {
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true);


        }

        btn_getCode= (Button) findViewById(R.id.change_btn_getCode);
        change_getPhone= (TextView) findViewById(R.id.change_edt_getPhone);
        change_getCode= (TextView) findViewById(R.id.change_edt_getCode);
        change_passWord= (Button) findViewById(R.id.change_btn_changePassWord);
        change_getPassWord= (TextView) findViewById(R.id.change_edt_newPassWord);
        btn_getCode.setOnClickListener(this);
        change_passWord.setOnClickListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_change_pass_word;
    }

    @Override
    public void onClick(View v) {
        String phone=change_getPhone.getText().toString();
        String model="您的验证码是`%smscode%`，有效期为`%ttl%`分钟。您正在使用`%appname%`的验证码";
        String code=change_getCode.getText().toString();
        String newPassWord=change_getPassWord.getText().toString();
        switch (v.getId()){
            case R.id.change_btn_getCode:
                getChangePassWordCode(phone,model);
                Toast.makeText(this, phone, Toast.LENGTH_SHORT).show();
                break;
            case R.id.change_btn_changePassWord:
                setNewPassWord(code,newPassWord);
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                break;
        }

    }
    private void getChangePassWordCode(String phone,String model){
       // 请求重置密码操作的短信验证码：

        BmobSMS.requestSMSCode(phone,model, new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId,BmobException ex) {
                if(ex==null){//验证码发送成功
                    Log.i("smile", "短信id："+smsId);//用于查询本次短信发送详情
                    Toast.makeText(ChangePassWordActivity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ChangePassWordActivity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                    Log.e("AAA", ex.toString()+ex.getMessage());
                }
            }
        });
    }
    private void setNewPassWord(String code,String passWord){
      //  用户收到重置密码的验证码之后，就可以调用resetPasswordBySMSCode方法来实现密码重置:

        BmobUser.resetPasswordBySMSCode(code,passWord, new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if(ex==null){
                    Log.i("smile", "密码重置成功");

                    Toast.makeText(ChangePassWordActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Log.i("smile", "重置失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                }
            }
        });
    }
}
