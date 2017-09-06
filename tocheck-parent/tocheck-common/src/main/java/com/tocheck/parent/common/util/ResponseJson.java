package com.tocheck.parent.common.util;

public class ResponseJson {
    private boolean state = false;

    private String message;

    private boolean needrefresh = false;

    private Object param;

    public static ResponseJson body(boolean state) {
        ResponseJson json = new ResponseJson();
        json.setState(state);
        return json;
    }

    public static ResponseJson body(boolean state, String message) {
        ResponseJson json = new ResponseJson();
        json.setState(state);
        json.setMessage(message);
        return json;
    }

    public static ResponseJson body(boolean state, String message, Object param) {
        ResponseJson json = new ResponseJson();
        json.setState(state);
        json.setMessage(message);
        json.setParam(param);
        return json;
    }

    public static ResponseJson body(boolean state, String message, boolean needrefresh) {
        ResponseJson json = new ResponseJson();
        json.setState(state);
        json.setMessage(message);
        json.setNeedrefresh(needrefresh);
        return json;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isNeedrefresh() {
        return needrefresh;
    }

    public void setNeedrefresh(boolean needrefresh) {
        this.needrefresh = needrefresh;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}
