package com.stone.model;

import com.stone.model.User;

import java.util.List;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/5/5 20 19
 */

public class JsonAllUserRequest {
    /*
               本地json规则
               {
               "status":"success/error",
               "data": {jsonObject}
               }
                */
    private String status;
    private List<User> data;

    public JsonAllUserRequest() {
    }

    public JsonAllUserRequest(String status, List<User> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
