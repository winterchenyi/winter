package com.yestic.winter.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.yestic.winter.interceptor.JwtInterceptor;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Properties;

/**
 * Created by chenyi on 2017/12/19
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 文件上传设置
     * @return
     */
    /*@Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(104857600);//100M
        resolver.setResolveLazily(true);
        return resolver;
    }*/

    /*@Bean
    public MultipartProperties multipartProperties(){
        MultipartProperties multipartProperties = new MultipartProperties();
        multipartProperties.setMaxFileSize("104857600");
        multipartProperties.setMaxRequestSize("10");
        multipartProperties.setResolveLazily(true);
        return multipartProperties;
    }*/

    @Bean
    public SpringBeanUtil springBeanUtil(){
        return new SpringBeanUtil();
    }

    /**
     * JWT拦截器
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns(new String[]{"/**"});
        super.addInterceptors(registry);
    }

    /**
     * 响应消息格式bean  全部设置为alibaba json格式
     */
    @Bean
    public HttpMessageConverters httpMessageConverters() {
        FastJsonHttpMessageConverter json = new FastJsonHttpMessageConverter();
        return new HttpMessageConverters(new HttpMessageConverter[]{json});
    }

    /**
     * 设置返回数据的格式为json格式
     */
    @Bean
    public ContentNegotiationManager mvcContentNegotiationManager() {
        ContentNegotiationManagerFactoryBean a = new ContentNegotiationManagerFactoryBean();
        a.setFavorParameter(true);
        a.setFavorPathExtension(true);
        a.setDefaultContentType(MediaType.TEXT_HTML);
        Properties properties = new Properties();
        properties.put("json", MediaType.APPLICATION_JSON_UTF8_VALUE);
        properties.put("xml",MediaType.APPLICATION_XML_VALUE);
        a.setMediaTypes(properties);
        return a.getObject();
    }

    /**
     * 如果请求中找不到Handler,让DispatcherServlet来抛异常,用来处理404.
     */
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet, new String[0]);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return registration;
    }

    /**
     * 静态资源设置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        super.addResourceHandlers(registry);
    }


}
