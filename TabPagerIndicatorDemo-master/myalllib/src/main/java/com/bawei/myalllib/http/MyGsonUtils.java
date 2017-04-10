package com.bawei.myalllib.http;

import com.google.gson.Gson;

/**
 * Created by hp on 2017/3/28.
 */

public class MyGsonUtils {

        private String json;
        private Class jsonclass;

    public MyGsonUtils(String json, Class jsonclass) {
        this.json = json;
        this.jsonclass = jsonclass;
    }

    public Object jsonToGson(){
            Gson gson = new Gson();
            return  gson.fromJson(json, jsonclass);
        }

}
