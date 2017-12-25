package com.yestic.winter.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yestic.winter.mapper.SysUserMapper;
import com.yestic.winter.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyi on 2017/11/20
 */
@Service
public class SysAccountService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询所有
     * @return
     */
    public List<SysUser> getAll(){
        return sysUserMapper.selectAll();
    }

    /**
     * 通过登录名查询用户
     * @param loginName
     * @return
     */
    public SysUser findByLoginName(String loginName){
        SysUser sysAccount = new SysUser();
        sysAccount.setLoginName(loginName);
        sysAccount = sysUserMapper.selectOne(sysAccount);
        return sysAccount;
    }


    /**
     * 分页查询详解
     */
    public void getPage(){

        List<SysUser> list1 = new ArrayList<>();
        List<SysUser> list2 = new ArrayList<>();

        // 设置分页查询条件，紧跟着的第一个查询会被分页
        PageHelper.startPage(1,10);
        list1 = sysUserMapper.selectAll();

        // PageInfo 对数据进行了封装，里面有分页信息，有按分页查询要求的数据
        PageInfo page= new PageInfo(list1);

        // 后面的查询不会被分页
        list2 = sysUserMapper.selectAll();

        System.out.println(list1);
        System.out.println(list2);
    }

}
