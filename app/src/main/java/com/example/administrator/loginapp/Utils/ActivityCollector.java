package com.example.administrator.loginapp.Utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/10/14 0014.
 */

public class ActivityCollector {
    public static List<Activity> activities=new ArrayList<>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishActivity(){
       for(Activity activity1:activities){
           if(!activity1.isFinishing()){
               activity1.finish();
           }

       }
        activities.clear();
    }

    private void changePassWord(){
       // 自V3.4.3版本开始，SDK为开发者提供了直接修改当前用户登录密码的方法，只需要传入旧密码和新密码，然后调用BmobUser提供的静态方法updateCurrentUserPassword即可，以下是示例：

        BmobUser.updateCurrentUserPassword("旧密码", "新密码", new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){


                   // toast("密码修改成功，可以用新密码进行登录啦");
                }else{
                 //   toast("失败:" + e.getMessage());
                }
            }

        });
    }

}
