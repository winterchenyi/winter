package com.yestic.winter.dto;

import com.alibaba.fastjson.JSONObject;
import com.yestic.winter.constant.ResCode;
import com.yestic.winter.util.WinterUtils;

/**
 * Created by chenyi on 2017/12/19
 */
public class ReturnBody {
    private int status = 200;
    private String code;
    private String msg;
    private Object data;

    public ReturnBody() {
    }

    private ReturnBody(int status, String code, String msg, Object data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ReturnBody(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ReturnBody buildSuccess() {
        return new ReturnBody(ResCode.SUCCESS.getKey(), ResCode.SUCCESS.getValue(), (Object)null);
    }

    public static ReturnBody buildSuccess(Object data) {
        return new ReturnBody(ResCode.SUCCESS.getKey(), ResCode.SUCCESS.getValue(), data);
    }

    public static ReturnBody buildFail() {
        return new ReturnBody(ResCode.FAIL.getKey(), ResCode.FAIL.getValue(), (Object)null);
    }

    public static ReturnBody buildError(int status, String msg, String exmsg) {
        return new ReturnBody(status, ResCode.ERROR.getKey(), msg, exmsg);
    }

    public static ReturnBody buildError(int status, String exmsg) {
        return new ReturnBody(status, ResCode.ERROR.getKey(), ResCode.ERROR.getValue(), exmsg);
    }

    public static ReturnBody build(ResCode resCode) {
        return new ReturnBody(resCode.getKey(), resCode.getValue(), (Object)null);
    }

    public static ReturnBody buildMsg(ResCode resCode, String msg) {
        return new ReturnBody(resCode.getKey(), msg, (Object)null);
    }

    public static ReturnBody buildData(ResCode resCode, Object data) {
        return new ReturnBody(resCode.getKey(), resCode.getValue(), data);
    }

    public static ReturnBody buildData(ResCode resCode, Object data, String msg) {
        return new ReturnBody(resCode.getKey(), msg, data);
    }

    public String toString() {
        String tempdata = WinterUtils.isNotNull(this.data) ? JSONObject.toJSONString(this.data) : "";
        return "ReturnBody{status=" + this.status + ", code='" + this.code + '\'' + ", msg='" + this.msg + '\'' + ", data=" + tempdata + '}';
    }
}
