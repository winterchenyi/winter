package com.yestic.winter.dto;

import java.util.Map;

/**
 * Created by chenyi on 2017/12/22
 */
public class ValidationResult {

    private boolean hasErrors;
    private Map<String, String> errorMsg;
    private String firstErrorMsg;

    public ValidationResult() {
    }

    public boolean isHasErrors() {
        return this.hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(Map<String, String> errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFirstErrorMsg() {
        return this.firstErrorMsg;
    }

    public void setFirstErrorMsg(String firstErrorMsg) {
        this.firstErrorMsg = firstErrorMsg;
    }

    public String toString() {
        return "ValidationResult [hasErrors=" + this.hasErrors + ", errorMsg=" + this.errorMsg + "]";
    }
}
