package com.yestic.winter.dto;

import java.io.Serializable;

/**
 * Created by chenyi on 2017/12/19
 */
public class JwtUser implements Serializable {

    private static final long serialVersionUID = -1673610483151012377L;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户角色
     */
    private String userRole;
    /**
     * 登录ip
     */
    private String loginIp;

    public JwtUser() {
    }

    public JwtUser(String userId, String userName, String userRole, String loginIp) {
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
        this.loginIp = loginIp;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return this.userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getLoginIp() {
        return this.loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userRole='" + userRole + '\'' +
                ", loginIp='" + loginIp + '\'' +
                '}';
    }
}
