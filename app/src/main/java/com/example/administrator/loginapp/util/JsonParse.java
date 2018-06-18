package com.example.administrator.loginapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

import io.reactivex.annotations.NonNull;

/**
 * Created by wuzihong on 2016/9/11.
 * Json解析类，采用Gson解析
 */

public class JsonParse {
    private static Gson mGson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
//            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .registerTypeAdapter(JSONObject.class, new JSONObjectDeserializer())
            .create();

    //常量字典解析
    public static HashMap<String,String> parseMap(JSONObject values, String key){
        HashMap<String,String> map=new HashMap<>();
        JSONArray jsonArray = values.optJSONArray(key);
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0, size = jsonArray.length(); i < size; i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                map.put(jsonObject.optString("id"), jsonObject.optString("name"));
            }
        }
        return map;
    }

    public static HashMap<String,Double> parseMapDouble(JSONObject values, String key){
        HashMap<String,Double> map=new HashMap<>();
        JSONArray jsonArray = values.optJSONArray(key);
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0, size = jsonArray.length(); i < size; i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                map.put(jsonObject.optString("id"), jsonObject.optDouble("name"));
            }
        }
        return map;
    }

    public static HashMap<String,Integer> parseMapInt(JSONObject values, String key){
        HashMap<String,Integer> map=new HashMap<>();
        JSONArray jsonArray = values.optJSONArray(key);
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0, size = jsonArray.length(); i < size; i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                map.put(jsonObject.optString("id"), jsonObject.optInt("name"));
            }
        }
        return map;
    }

    @NonNull
    public static String getStringField(String json, String key){
        if (json==null||json.equals("")) return "";
        try{
            JSONObject jsonObject=new JSONObject(json);
            return jsonObject.optString(key);
        }catch (Exception e){
          //  LogRui.e("getField####",e);
        }
        return "";
    }

    @NonNull
    public static String getStringField(String json, String key1, String key2){
        if (json==null||json.equals("")) return "";
        try{
            JSONObject jsonObject=new JSONObject(json);
            String value=jsonObject.optString(key1);
            if (value==null||value.equals("")) return "";
            jsonObject=new JSONObject(value);
            return jsonObject.optString(key2);
        }catch (Exception e){
          //  LogRui.printExceptionStack(e);
            //LogRui.e("getField####",e);
        }
        return "";
    }

    public static <T> T fromJsonInValue(JSONObject values, String key, java.lang.reflect.Type typeOfT) {
        if (values==null) return null;
        String json = values.optString(key);
//        if (!CommonUtil.responseIsNull(json)) {
//            return JsonParse.fromJson(json, typeOfT);
//        }
        return null;
    }

    public static <T> T fromJsonInValue(JSONObject values, String key, java.lang.reflect.Type typeOfT, T defaultValue) {
        if (values==null) return null;
        String json = values.optString(key);
//        if (!CommonUtil.responseIsNull(json)) {
//            return JsonParse.fromJson(json, typeOfT);
//        }
        return defaultValue;
    }

    public static <T> T fromJson(String json, java.lang.reflect.Type typeOfT) {
        return mGson.fromJson(json, typeOfT);
    }

    public static String toJson(Object o) {
        return mGson.toJson(o);
    }


    public static String toJsons(Object... objects){
        StringBuilder builder=new StringBuilder();
        for(Object o:objects){
            if(o!=null){
                String s=mGson.toJson(o);
                if (builder.length()>0) {
                    builder.append(s);
                    builder.deleteCharAt(builder.length() - s.length());
                }else {
                    builder.append(s);
                }
                builder.deleteCharAt(builder.length()-1);
                builder.append(",");
            }
        }
        if (builder.length()>0){
            builder.deleteCharAt(builder.length()-1);
            builder.append("}");
        }
        return builder.toString();
    }

    public static class Type implements ParameterizedType {
        private java.lang.reflect.Type raw;
        private java.lang.reflect.Type[] args;

        public Type(java.lang.reflect.Type raw, java.lang.reflect.Type... args) {
            this.raw = raw;
            this.args = args;
        }

        @Override
        public java.lang.reflect.Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public java.lang.reflect.Type getOwnerType() {
            return null;
        }

        @Override
        public java.lang.reflect.Type getRawType() {
            return raw;
        }
    }

    private static class JSONObjectDeserializer implements JsonSerializer<JSONObject>, JsonDeserializer {
        @Override
        public Object deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return new JSONObject(json.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public JsonElement serialize(JSONObject src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            return new JsonParser().parse(src.toString());
        }
    }

//    private static class DateTypeAdapter extends TypeAdapter<Date> {
//        @Override
//        public void write(JsonWriter out, Date value) throws IOException {
//            out.value(DateFormat.format(value));
//        }
//
//        @Override
//        public Date read(JsonReader in) throws IOException {
//            if (in.peek() == JsonToken.NULL) {
//                in.nextNull();
//                return null;
//            }
//            return DateFormat.parse(in.nextString());
//        }
//    }
}
