package com.yestic.winter.exception;

import com.yestic.winter.constant.ResCode;
import com.yestic.winter.dto.ReturnBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 返回异常处理
 * Created by chenyi on 2017/12/19
 */
@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandler {
    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ReturnBody noHandlerFoundException(Exception ex) {
        return ReturnBody.buildError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
    }

    @ExceptionHandler({ResCodeException.class})
    @ResponseStatus(HttpStatus.OK)
    public ReturnBody resCodeException(Exception ex) {
        String key = ex.getMessage();
        if (null != key && !"".equals(key)) {
            if (!ResCode.isValid(key)) {
                return ReturnBody.buildError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ResCodeException message not in ResCode");
            } else {
                ResCode resCode = ResCode.parseOf(key);
                return ReturnBody.build(resCode);
            }
        } else {
            return ReturnBody.buildError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ResCodeException message is null");
        }
    }

//    @ExceptionHandler({ParamValidationException.class})
//    @ResponseStatus(HttpStatus.OK)
//    public ReturnBody paramValidationException(Exception ex) {
//        return ReturnBody.buildData(ResCode.PARAMETERTYPEMISMATCHING, ex.getMessage());
//    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ReturnBody exception(Exception ex) {
        ex.printStackTrace();
        return ReturnBody.buildError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
