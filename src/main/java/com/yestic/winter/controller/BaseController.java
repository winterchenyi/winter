package com.yestic.winter.controller;

import com.alibaba.fastjson.JSONObject;
import com.yestic.winter.annotation.JWT;
import com.yestic.winter.component.RedisCache;
import com.yestic.winter.component.ValidateCode;
import com.yestic.winter.constant.Constants;
import com.yestic.winter.constant.ResCode;
import com.yestic.winter.component.JwtDto;
import com.yestic.winter.dto.JwtUser;
import com.yestic.winter.dto.ReturnBody;
import com.yestic.winter.model.SysUser;
import com.yestic.winter.service.SysAccountService;
import com.yestic.winter.util.HttpUtil;
import com.yestic.winter.util.MD5;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by chenyi on 2017/12/18
 */
@Controller
public class BaseController {

    @Autowired
    private SysAccountService sysAccountService;
    @Autowired
    private JwtDto jwtDto;
    @Autowired
    private RedisCache redisCache;

    @RequestMapping(value = "/")
    public String index(){
        return "/index";
    }

    @RequestMapping(value = "/getToken")
    @ResponseBody
    public String getToken()throws Exception{
        String token = jwtDto.createJWT(Constants.JWT_ID,"001",Constants.JWT_TTL);
        return token;
    }

    @JWT
    @RequestMapping(value = {"/test"})
    @ResponseBody
    public ReturnBody test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("this is test");
        return ReturnBody.buildSuccess();
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ReturnBody login(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        SysUser sysUser = sysAccountService.findByLoginName(loginName);
        MD5 md5 = new MD5();
        JSONObject jo = new JSONObject();
        if(sysUser == null || sysUser.getId().equals("")){
            return ReturnBody.buildMsg(ResCode.FAIL,"用户名错误");
        }else{
            if (!sysUser.getPassword().equals(md5.getMD5ofStr(password))){
                return ReturnBody.buildMsg(ResCode.FAIL,"密码错误");
            }else{
                /**
                 * 创建一个token
                 */
                JwtUser users = new JwtUser();
                users.setUserId(sysUser.getLoginName());
                users.setUserName(sysUser.getUserName());
                users.setUserRole(String.valueOf(sysUser.getUserRole()));
                users.setLoginIp(HttpUtil.getIpAddress(request));
                String subject = jwtDto.generalSubject(users);
                String token = jwtDto.createJWT(Constants.JWT_ID, subject, Constants.JWT_TTL);
                System.out.println(token);
                redisCache.put("jwtUser_"+users.getUserName(), users, Constants.JWT_TTL);
                jo.put("token",token);
                jo.put("user",sysUser);
                return ReturnBody.buildSuccess(jo);
            }
        }
    }

    @RequestMapping(value = "/out")
    @ResponseBody
    public ReturnBody out(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String loginName = request.getParameter("loginName");
        JwtUser user = new JwtUser();
        SysUser sysUser = sysAccountService.findByLoginName(loginName);
        user = redisCache.get("jwtUser_" + sysUser.getUserName(),JwtUser.class);
        redisCache.del("jwtUser_" + user.getUserName());
        return ReturnBody.buildSuccess();
    }

    /**
     * 页面路由跳转
     *  /link?link=/user/list
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/link")
    public String link(HttpServletRequest request, HttpServletResponse response){
        String link = request.getParameter("link");
        return link;
    }

    /**
     * 响应验证码页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/validateCode")
    public String validateCode(HttpServletRequest request, HttpServletResponse response)throws Exception{
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession();

        ValidateCode vCode = new ValidateCode(120,40,5,100);
        session.setAttribute("code", vCode.getCode());
        vCode.write(response.getOutputStream());
        return null;
    }

}
