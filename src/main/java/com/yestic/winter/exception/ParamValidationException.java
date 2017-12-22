package com.yestic.winter.exception;

/**
 * 参数验证异常，返回异常消息
 * Created by chenyi on 2017/12/22
 */
public class ParamValidationException extends RuntimeException {
    public ParamValidationException(String msg) {
        super(msg);
    }
}
