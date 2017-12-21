package com.yestic.winter.controller;

import com.yestic.winter.model.SysUser;
import com.yestic.winter.service.SysAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String  list(Model model){
//        ModelAndView mv = new ModelAndView();
        List<SysUser> list = sysAccountService.getAll();
        model.addAttribute("list",list);
//        mv.addObject("list",list);
//        mv.setViewName("list");
        return "/list";
    }

}
