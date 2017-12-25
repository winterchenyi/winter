package com.yestic.winter.dto;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by chenyi on 2017/12/25
 */
public class UserBean implements Serializable{

    private static final long serialVersionUID = -6245302405319922146L;

    /**
     * 登录名/账号
     */
    @NotEmpty(message = "登录名/账号,不能为空")
    private String loginName;

    /**
     * 密码
     */
//    @NotNull
    private String password;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户年龄
     */
    private String userAge;

    /**
     * 性别：0-女，1-男
     */
    private String sex;

    /**
     * 手机
     */
    private String cellPhone;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
