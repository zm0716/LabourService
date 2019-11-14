package com.agilefinger.labourservice.utils;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * gson工具类
 * Created by wuqiubin on 2016/10/20.
 */
public class GsonUtils {

    /**
     * 对象转换成json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }


    public static <T> T getBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return t;
    }



    public static <T> List<T> getBeanList(String jsonString, Class<T> cls) {
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(jsonString).getAsJsonArray();
        for(final JsonElement elem : array){
            mList.add(new Gson().fromJson(elem, cls));
        }
        return mList;
    }
}
