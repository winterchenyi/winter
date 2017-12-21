package com.yestic.winter.exception;

import com.yestic.winter.constant.ResCode;

/**
 * Created by chenyi on 2017/12/19
 */
public class ResCodeException extends RuntimeException {
    public ResCodeException(ResCode code) {
        super(code.getKey());
    }
}
