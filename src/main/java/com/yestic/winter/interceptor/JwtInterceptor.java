package com.yestic.winter.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.yestic.winter.annotation.JWT;
import com.yestic.winter.component.JwtDto;
import com.yestic.winter.component.RedisCache;
import com.yestic.winter.constant.Constants;
import com.yestic.winter.constant.ResCode;
import com.yestic.winter.dto.JwtUser;
import com.yestic.winter.exception.ResCodeException;
import com.yestic.winter.util.HttpUtil;
import com.yestic.winter.util.WinterUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 拦截器，验证用户每次请求的token
 * Created by chenyi on 2017/12/20
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtDto jwtDto;
    @Autowired
    private RedisCache redisCache;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("httpServletRequest = [" + httpServletRequest + "], httpServletResponse = [" + httpServletResponse + "], o = [" + o + "]");
        if (o instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)o;
            //拦截需要验证token的方法
            JWT antoken = (JWT)handlerMethod.getMethodAnnotation(JWT.class);
            if (WinterUtils.isNotNull(antoken)) {
                String headersToken = httpServletRequest.getHeader("token");
                if (null == headersToken || "".equals(headersToken)) {
                    throw new ResCodeException(ResCode.TOKENLOSE);
                }

                WebApplicationContext webApplicationContext;
                if (null == this.jwtDto) {
                    webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
                    this.jwtDto = (JwtDto)webApplicationContext.getBean("jwtDto");
                }

                if (null == this.redisCache) {
                    webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
                    this.redisCache = (RedisCache)webApplicationContext.getBean("redisCache");
                }

                String formIp = HttpUtil.getIpAddress(httpServletRequest);
                ResCode code = this.validToken(headersToken, formIp);

                if (!code.equals(ResCode.SUCCESS)) {
                    throw new ResCodeException(code);
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private ResCode validToken(String token, String ip) throws Exception {
        Claims claims = null;
        //根据token解析出用户信息
        try {
            claims = this.jwtDto.parseJWT(token);
        } catch (ExpiredJwtException var11) {
            claims = var11.getClaims();
            Long effTime = (new Date()).getTime();
            Long exptime = claims.getExpiration().getTime();
            System.out.println("当前 = " + WinterUtils.dateToStr(new Date(effTime.longValue()), "yyyy-MM-dd HH:mm:ss.SSS"));
            System.out.println("Token有效期 = " + WinterUtils.dateToStr(new Date(exptime.longValue()), "yyyy-MM-dd HH:mm:ss.SSS"));
            exptime = exptime.longValue() + Constants.JWT_TTL_DELAY.longValue();
            if (effTime.longValue() > exptime.longValue()) {
                return ResCode.TOKENEXPIRATION;
            }
        } catch (Exception var12) {
            return ResCode.INVALIDTOKEN;
        }
        //判断解析出的token是否与redis中的用户信息一致
        if (null == claims) {
            return ResCode.INVALIDTOKEN;
        } else {
            String json = claims.getSubject();
            JwtUser user = JSONObject.parseObject(json, JwtUser.class);
            String loginIp = user.getLoginIp();
            if (ip != null && !ip.equals("") && !ip.equals("127.0.0.1") && !ip.equals(loginIp)) {
                return ResCode.INVALIDTOKEN;
            } else {
                JwtUser cacheUser = this.redisCache.get("jwtUser_"+user.getUserName(), JwtUser.class);
                return !WinterUtils.isNull(cacheUser) && user.getUserId().equals(cacheUser.getUserId()) ? ResCode.SUCCESS : ResCode.INVALIDTOKEN;
            }
        }
    }

}
