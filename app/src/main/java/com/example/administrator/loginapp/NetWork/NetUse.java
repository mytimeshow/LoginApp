package com.example.administrator.loginapp.NetWork;


import com.example.administrator.loginapp.util.JsonParse;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class NetUse {
    private OkHttpClient client=new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15,TimeUnit.SECONDS)
            .readTimeout(15,TimeUnit.SECONDS)
            .build();

    private Retrofit retrofit;
    private  NetMethod netMethod;
    private Observable<ResponseBody> observable;
    private static String BaseUrl="http://api01.bitspaceman.com:8000/" ;
            //"apikey=MVOFWSm6npLKitJscKNI6uItrkkPUpDJ0sJc5WBKM5DXQ6s3gvqXxCHY5kVpCuXC";
    private static volatile NetUse mNetUse;


    public static NetUse getInstanse(){
        if(mNetUse==null){
            synchronized (NetUse.class){
                if(mNetUse==null){
                    mNetUse=new NetUse(BaseUrl);
                }
            }
        }
        return mNetUse;
    }


    private NetUse(String baseUrl){
        retrofit=new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        netMethod=retrofit.create(NetMethod.class);


    }
    //无参get请求
    public  Observable<ResponseBody> get(String url){
        return netMethod.get(url).subscribeOn(Schedulers.newThread())
               ;
    }
    //带参get请求
    public  Observable<ResponseBody> get(String url, Map<String,Object> query){
        return netMethod.get(url,query).subscribeOn(Schedulers.newThread() );
    }
    //带参和heade rget请求
    public  Observable<ResponseBody> get(String url, Map<String,Object> query,Map<String,Object> headers){
        return netMethod.get(url,query,headers).subscribeOn(Schedulers.newThread() );
    }


    //无参post请求
    public  Observable<ResponseBody> post(String url){
        return netMethod.post(url).subscribeOn(Schedulers.newThread() );
    }
    //待参post请求
    public  Observable<ResponseBody> post(String url, Map<String,Object> query){
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), JsonParse.toJson(query));
        return netMethod.post(url,body).subscribeOn(Schedulers.newThread() );
    }
    //带参和header post请求
    public  Observable<ResponseBody> post(String url, Map<String,Object> query,Map<String,Object> headers){
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), JsonParse.toJson(query));
        return netMethod.post(url,body,headers).subscribeOn(Schedulers.newThread() );
    }


}
