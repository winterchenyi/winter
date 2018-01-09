package com.yestic.winter.job;

import com.yestic.winter.dto.ScheduleJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**http://blog.csdn.net/u014723529/article/details/51291289
 *
 * 定时任务运行工厂类
 *
 * 这里我们实现的是无状态的Job，如果要实现有状态的Job在以前是实现StatefulJob接口，
 * 在我使用的quartz 2.2.1中，StatefulJob接口已经不推荐使用了，换成了注解的方式，
 * 只需要给你实现的Job类加上注解@DisallowConcurrentExecution即可实现有状态：
 *
 * Created by chenyi on 2017/12/26.
 */
//@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob)jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");

    }
}
