package com.yestic.winter.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * MyBatis扫描接口
 * 使用的tk.mybatis.spring.mapper.MapperScannerConfigurer，如果你不使用通用Mapper，可以改为org.xxx...
 * Created by chenyi on 2017/11/20
 */
@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.yestic.winter.mapper");

        //初始化扫描器的相关配置，这里我们要创建一个Mapper的父类
        Properties properties = new Properties();
        properties.setProperty("mappers", "com.yestic.winter.config.BaseMapper");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");

        mapperScannerConfigurer.setProperties(properties);

        return mapperScannerConfigurer;
    }
}
