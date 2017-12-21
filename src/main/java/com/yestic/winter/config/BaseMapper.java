package com.yestic.winter.config;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 父类mapper
 * Created by chenyi on 2017/11/21
 */
public interface BaseMapper<T> extends Mapper<T>,MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
