package com.stone.model;

import com.alibaba.fastjson.JSONArray;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/5/5 20 19
 */

public class JsonArrayRequest<T> {
    /*
               本地json规则
               {
               "status":"success/error",
               "data": {jsonObject}
               }
                */
    private String status;
    private JSONArray data;

    public JsonArrayRequest() {
    }

    public JsonArrayRequest(String status, JSONArray data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }
}
