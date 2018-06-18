package com.example.administrator.loginapp.Api;

import com.example.administrator.loginapp.Bean.Response;
import com.example.administrator.loginapp.NetWork.NetUse;
import com.example.administrator.loginapp.util.JsonParse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2018/6/9 0009.
 */

public class VideoApi {

    public static Observable<Response<Response.DataBean>> getInfo(String id){
        Map<String,Object> map=new HashMap<>();
        map.put("apikey","MVOFWSm6npLKitJscKNI6uItrkkPUpDJ0sJc5WBKM5DXQ6s3gvqXxCHY5kVpCuXC");
        map.put("kw", id);
        map.put("pageToken",5);
        return NetUse.getInstanse()
                .get("news/qihoo?",map)
                .map(new Function<ResponseBody, Response<Response.DataBean>>() {
                    @Override
                    public Response<Response.DataBean> apply(ResponseBody responseBody) throws Exception {
                        String str=responseBody.string();

                        return JsonParse.fromJson(str, new JsonParse.Type(Response.class,Response.DataBean.class) {
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }


}
