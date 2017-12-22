package com.yestic.winter.controller;

import com.alibaba.fastjson.JSONObject;
import com.yestic.winter.dto.ReturnBody;
import com.yestic.winter.model.SysUser;
import com.yestic.winter.service.SysAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by chenyi on 2017/11/20
 */
@Controller
@RequestMapping(value = "acconut")
public class SysAccountController {

    @Autowired
    private SysAccountService sysAccountService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public ReturnBody list()throws Exception{
        List<SysUser> list = sysAccountService.getAll();
        JSONObject jo = new JSONObject();
        jo.put("list",list);
        return ReturnBody.buildSuccess(jo);
    }

}
