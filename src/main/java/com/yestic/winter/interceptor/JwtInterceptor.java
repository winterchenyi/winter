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

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        System.out.println(">>>>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
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
        // 只有返回true这次请求才会继续向下执行，返回false取消当前请求
        return true;
    }

    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //这种拦截500，404 会出现一个很大的问题，页面js找到不也会被拦截
//        System.out.println(">>>>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
//        if(httpServletResponse.getStatus()==500){
//            modelAndView.setViewName("/500");
//        }else if(httpServletResponse.getStatus()==404){
//            modelAndView.setViewName("/404");
//        }
//        else if(httpServletResponse.getStatus()==401){
//            modelAndView.setViewName("/401");
//        }
    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println(">>>>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

    /**
     * token验证
     * @param token
     * @param ip
     * @return
     * @throws Exception
     */
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
