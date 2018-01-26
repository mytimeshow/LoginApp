package com.example.administrator.loginapp.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.loginapp.Bean.MyUser;
import com.example.administrator.loginapp.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {
private TextView edt_username;
    private TextView edt_passWord;
    private TextView edt_email;
    private Button btn_sign;
    @Override
    protected void initListener() {
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=edt_username.getText().toString();
                String passWord=edt_passWord.getText().toString();
                String email=edt_email.getText().toString();
                if(!username.equals("")){
                    MyUser user=new MyUser();
                    user.setAge(18);
                    user.setNick("夜听海雨");
                    user.setSex(false);
                    user.setUsername(username);
                    user.setPassword(passWord);
                    user.setEmail(email);
                    user.signUp(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if(e==null){
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finish();

                            }else{
                                Log.e("AAA",e.toString()+e.getMessage());
                                Toast.makeText(RegisterActivity.this, "fail", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }else{

                    Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    edt_passWord.setText("");
                    edt_email.setText("");
                }
            }
        });
    }

    @Override
    protected void init() {
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true);


        }
        initViews();

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

    private void initViews() {
        edt_username= (TextView) findViewById(R.id.edt_userName);
        edt_passWord= (TextView) findViewById(R.id.edt_passWord);
        edt_email= (TextView) findViewById(R.id.edt_confire_passWord);
        btn_sign= (Button) findViewById(R.id.btn_sign);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_register;

    }
}
