package com.hooooong.socar.domain.model;

import java.util.List;

/**
 * Created by Android Hong on 2017-11-22.
 */

public class Zone {
    private String count;
    private List<Data> data;
    private String code;
    private String msg;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess(){
        return "200".equals(code);
    }
}
