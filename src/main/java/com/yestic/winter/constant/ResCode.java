package com.yestic.winter.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务返回枚举类
 * Created by chenyi on 2017/12/18
 */
public enum ResCode {
    SUCCESS("0000", "成功"),
    FAIL("1001", "业务失败"),
    PERMISSIONDENIED("1002", "权限不足"),
    INVALIDTOKEN("2000", "无效token"),
    TOKENEXPIRATION("2001", "token过期"),
    INVALIDSIGNATURE("2002", "签名无效"),
    TOKENLOSE("2003", "token丢失"),
    PARAMETERMISSING("3000", "参数丢失"),
    PARAMETERTYPEMISMATCHING("3001", "参数格式错误"),
    ILLEGAL("500", "非法请求"),
    REPEATOPTS("9000", "重复提交"),
    ERROR("9999", "系统错误");

    private String key;
    private String value;
    private static Map<String, ResCode> valueMap = new HashMap();

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    private ResCode(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static boolean isValid(String typeKey) {
        return typeKey == null ? false : valueMap.containsKey(typeKey);
    }

    public static ResCode parseOf(String key) {
        ResCode[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ResCode item = var1[var3];
            if (item.getKey().equals(key)) {
                return item;
            }
        }

        return null;
    }

    static {
        ResCode[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            ResCode _enum = var0[var2];
            valueMap.put(_enum.key, _enum);
        }

    }
}
