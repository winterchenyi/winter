package com.yestic.winter.util;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 获取用户ip
 * Created by chenyi on 2017/12/19
 */
public class HttpUtil {
    public static final String CONTENT_TYPE_JSON_CHARSET = "application/json;charset=utf-8";
    public static final String CONTENT_CHARSET = "UTF-8";

    public HttpUtil() {
    }

    public static final String getIpAddress(HttpServletRequest request) throws IOException {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.length() > 15) {
                String[] ips = ip.split(",");

                for(int index = 0; index < ips.length; ++index) {
                    String strIp = ips[index];
                    if (!"unknown".equalsIgnoreCase(strIp)) {
                        ip = strIp;
                        break;
                    }
                }
            }
        } else {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        }

        return ip;
    }

    public static void ResponseWriter(ServletResponse response, String msg) {
        PrintWriter printWriter = null;

        try {
            printWriter = response.getWriter();
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            printWriter.write(msg);
        } catch (IOException var7) {
            var7.printStackTrace();
        } finally {
            printWriter.flush();
            printWriter.close();
        }

    }
}
