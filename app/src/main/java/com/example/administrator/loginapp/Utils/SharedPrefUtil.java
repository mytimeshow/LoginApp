package com.example.administrator.loginapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class SharedPrefUtil {
    private static String FILE = "news";



    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static void saveMoreValuesBoolean(Context context, String key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(FILE, Context.MODE_PRIVATE).edit();
        editor.putString("nickName", key).commit();

    }
    //记住用户名与密码
    public static void savePassWoldOrUserName(Context context, String key,String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(FILE, Context.MODE_PRIVATE).edit();
        editor.putString(key, value).commit();
    }
    //获取密码或用户名
    public static String getPassWoldOrUserName(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        sp.getString(key, "");
        return sp.getString(key,"");
    }
    //清除记住密码和用户名的功能
    public static void removePassWoldOrUserName(Context context, String key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(FILE, Context.MODE_PRIVATE).edit();
        editor.remove(key).commit();
    }


   public static String getMoreValuesBoolean(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        sp.getString("nickName", "");


        return sp.getString("nickName","");

    }
    //移除所有信息
    public static void removeValues(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(FILE, Context.MODE_PRIVATE).edit();
       editor.clear().commit();



    }




    public static boolean getBoolean(Context context, String key, boolean def) {
        SharedPreferences sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return sp.getBoolean(key, def);
    }
    public static void moveBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
}
