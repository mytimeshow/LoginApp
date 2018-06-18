package com.example.administrator.loginapp.Bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/6/9 0009.
 */

public class Response<D> {
    /**
     * hasNext : true
     * retcode : 000000
     * appCode : qihoo
     * dataType : news
     * pageToken : 2
     * data : [{"posterId":null,"content":"最新的剧情中,陆晨曦(","posterScreenName":"腾讯","tags":null,"url":"http://ent.qq.com/a/20170508/023354.htm","publishDateStr":"2017-05-08T03:46:00","title":"白百何陷医患风波 《外科》靳东职业生涯遇危机","publishDate":1494215160,"commentCount":null,"imageUrls":null,"id":"c028fc8126658124bc8f21a13650d51b"}]
     */

    @SerializedName("hasNext")
    public boolean hasNext;
    @SerializedName("retcode")
    public String retcode;
    @SerializedName("appCode")
    public String appCode;
    @SerializedName("dataType")
    public String dataType;
    @SerializedName("pageToken")
    public String pageToken;
    @SerializedName("data")
    public List<DataBean> data;

    public static Response objectFromData(String str) {

        return new Gson().fromJson(str, Response.class);
    }

    public static class DataBean {
        /**
         * posterId : null
         * content : 最新的剧情中,陆晨曦(
         * posterScreenName : 腾讯
         * tags : null
         * url : http://ent.qq.com/a/20170508/023354.htm
         * publishDateStr : 2017-05-08T03:46:00
         * title : 白百何陷医患风波 《外科》靳东职业生涯遇危机
         * publishDate : 1494215160
         * commentCount : null
         * imageUrls : null
         * id : c028fc8126658124bc8f21a13650d51b
         */

        @SerializedName("posterId")
        public Object posterId;
        @SerializedName("content")
        public String content;
        @SerializedName("posterScreenName")
        public String posterScreenName;
        @SerializedName("tags")
        public Object tags;
        @SerializedName("url")
        public String url;
        @SerializedName("publishDateStr")
        public String publishDateStr;
        @SerializedName("title")
        public String title;
        @SerializedName("publishDate")
        public int publishDate;
        @SerializedName("commentCount")
        public Object commentCount;
        @SerializedName("imageUrls")
        public Object imageUrls;
        @SerializedName("id")
        public String id;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }
    }
    /*{
        "hasNext": true,
            "retcode": "000000",
            "appCode": "qihoo",
            "dataType": "news",
            "pageToken": "2",
            "data": [
        {
            "posterId": null,
                "content": "最新的剧情中,陆晨曦(",
                "posterScreenName": "腾讯",
                "tags": null,
                "url": "http:\/\/ent.qq.com\/a\/20170508\/023354.htm",
                "publishDateStr": "2017-05-08T03:46:00",
                "title": "白百何陷医患风波 《外科》靳东职业生涯遇危机",
                "publishDate": 1494215160,
                "commentCount": null,
                "imageUrls": null,
                "id": "c028fc8126658124bc8f21a13650d51b"
        }
    ]
    }*/


}
