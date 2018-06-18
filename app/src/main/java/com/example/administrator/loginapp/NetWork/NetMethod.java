package com.example.administrator.loginapp.NetWork;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public interface NetMethod {
    @GET
    Observable<ResponseBody> get(@Url String url);

    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String,Object> query);

    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String,Object> query, @HeaderMap Map<String,Object> headers);

    @POST
    Observable<ResponseBody> post(@Url String url);

    @POST
    Observable<ResponseBody> post(@Url String url,@Body RequestBody body);

    @POST
    Observable<ResponseBody> post(@Url String url,@Body RequestBody body,@HeaderMap Map<String,Object> headers);

    @Multipart
    @POST
    Observable<ResponseBody> upLoad(@Url String url, @PartMap Map<String,Object> query);

    @Multipart
    @POST
    Observable<ResponseBody> upLoad(@Url String url, @PartMap Map<String,Object> query,@HeaderMap Map<String,Object> headers);

    @Streaming
    @GET
    Observable<ResponseBody> downLaod(@Url String url);
}
