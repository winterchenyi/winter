package com.yestic.winter.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by chenyi on 2018/1/4
 */
@Component
public class Job {

    @Autowired(required = false)
    private SchedulerFactoryBean schedulerFactoryBean;



}
