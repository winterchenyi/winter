package com.yestic.winter.dto;

import com.alibaba.fastjson.JSONObject;
import com.yestic.winter.util.ReflectUtil;
import com.yestic.winter.util.WinterUtils;

/**
 * Created by chenyi on 2017/12/22
 */
public class ParamsBody {

    /**
     * 访问平台，pc 或 app
     */
    private String appId;
    /**
     * 分页
     */
    private Page page;
    /**
     * 实体参数
     */
    private JSONObject body;

    public Page getPage() {
        return this.page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public JSONObject getBody() {
        return this.body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public <T> T getBody(Class clazz) throws Exception {
        return (T)  ReflectUtil.fillBeanFromJson(this.body, clazz);
    }

    public String toString() {
        String temppage = WinterUtils.isNotNull(this.page) ? JSONObject.toJSONString(this.page) : "";
        String tempbody = WinterUtils.isNotNull(this.body) ? JSONObject.toJSONString(this.body) : "";
        return "ParamsBody{page=" + temppage + ", body=" + tempbody + "}";
    }
}
