package com.yestic.winter.job;

import com.yestic.winter.dto.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 * Created by Administrator on 2017/12/27.
 */
@DisallowConcurrentExecution //不能并发执行同一个Job
public class QuartzJobStateFactory implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //可向数据库中存储定时任务运行记录
        //...

        //获取到定时任务信息
        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokMethod(scheduleJob);
        System.out.println(scheduleJob.toString());
    }

}
