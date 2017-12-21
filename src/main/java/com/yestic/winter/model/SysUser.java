package com.yestic.winter.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 登录名/账号
     */
    @Id
    @Column(name = "LOGIN_NAME")
    private String loginName;

    /**
     * 密码
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 用户名
     */
    @Column(name = "USER_NAME")
    private String userName;

    /**
     * 用户年龄
     */
    @Column(name = "USER_AGE")
    private Integer userAge;

    /**
     * 性别：0-女，1-男
     */
    @Column(name = "SEX")
    private Integer sex;

    /**
     * 手机
     */
    @Column(name = "CELL_PHONE")
    private String cellPhone;

    /**
     * 插入时间
     */
    @Column(name = "INS_DATE")
    private Date insDate;

    /**
     * 最后修改时间
     */
    @Column(name = "LAST_UPD_DATE")
    private Date lastUpdDate;

    /**
     * 最后操作用户
     */
    @Column(name = "LAST_ACCOUNT_ID")
    private String lastAccountId;

    /**
     * 用户角色：0-管理员，1-普通用户
     */
    @Column(name = "USER_ROLE")
    private Integer userRole;

    /**
     * 获取主键ID
     *
     * @return ID - 主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取登录名/账号
     *
     * @return LOGIN_NAME - 登录名/账号
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录名/账号
     *
     * @param loginName 登录名/账号
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取密码
     *
     * @return PASSWORD - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户名
     *
     * @return USER_NAME - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户年龄
     *
     * @return USER_AGE - 用户年龄
     */
    public Integer getUserAge() {
        return userAge;
    }

    /**
     * 设置用户年龄
     *
     * @param userAge 用户年龄
     */
    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    /**
     * 获取性别：0-女，1-男
     *
     * @return SEX - 性别：0-女，1-男
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别：0-女，1-男
     *
     * @param sex 性别：0-女，1-男
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取手机
     *
     * @return CELL_PHONE - 手机
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * 设置手机
     *
     * @param cellPhone 手机
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /**
     * 获取插入时间
     *
     * @return INS_DATE - 插入时间
     */
    public Date getInsDate() {
        return insDate;
    }

    /**
     * 设置插入时间
     *
     * @param insDate 插入时间
     */
    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    /**
     * 获取最后修改时间
     *
     * @return LAST_UPD_DATE - 最后修改时间
     */
    public Date getLastUpdDate() {
        return lastUpdDate;
    }

    /**
     * 设置最后修改时间
     *
     * @param lastUpdDate 最后修改时间
     */
    public void setLastUpdDate(Date lastUpdDate) {
        this.lastUpdDate = lastUpdDate;
    }

    /**
     * 获取最后操作用户
     *
     * @return LAST_ACCOUNT_ID - 最后操作用户
     */
    public String getLastAccountId() {
        return lastAccountId;
    }

    /**
     * 设置最后操作用户
     *
     * @param lastAccountId 最后操作用户
     */
    public void setLastAccountId(String lastAccountId) {
        this.lastAccountId = lastAccountId;
    }

    /**
     * 获取用户角色：0-管理员，1-普通用户
     *
     * @return USER_ROLE - 用户角色：0-管理员，1-普通用户
     */
    public Integer getUserRole() {
        return userRole;
    }

    /**
     * 设置用户角色：0-管理员，1-普通用户
     *
     * @param userRole 用户角色：0-管理员，1-普通用户
     */
    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }
}