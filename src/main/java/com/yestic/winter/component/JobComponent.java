//package com.yestic.winter.component;
//
//import com.yestic.winter.config.SpringBeanUtil;
//import com.yestic.winter.dto.ScheduleJob;
//import com.yestic.winter.job.QuartzJobFactory;
//import com.yestic.winter.job.QuartzJobStateFactory;
//import com.yestic.winter.util.WinterUtils;
//import org.quartz.*;
//import org.quartz.impl.matchers.GroupMatcher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
///**
// * Created by chenyi on 2018/1/3.
// */
//@Component
//public class JobComponent {
//    TODO SchedulerFactoryBean 注入不进来，具体原因有待排查
//    @Autowired
//    private SchedulerFactoryBean schedulerFactoryBean;
//
//    /**
//     * 添加任务
//     *
//     * @param job
//     * @throws SchedulerException
//     */
//    public void addJob(ScheduleJob job) throws SchedulerException {
//
////        schedulerFactoryBean = new SchedulerFactoryBean();
//
//        if (job == null) {
//            return;
//        }
//
////        schedulerFactoryBean = SpringBeanUtil.getBean("schedulerFactoryBean");
//
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        /**
//         * 在这里我把它设计成一个Job对应一个trigger，两者的分组及名称相同，方便管理，条理也比较清晰，在创建任务时如果不存在,则新建一个，如果已经存在则更新任务
//         */
//        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
//        //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
//        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
//
//        //不存在，创建一个
//        if (null == trigger) {
//            Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getJobStatus()) ? QuartzJobFactory.class : QuartzJobStateFactory.class;
//            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
//            jobDetail.getJobDataMap().put("scheduleJob", job);
//            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
//            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
//            scheduler.scheduleJob(jobDetail, trigger);
//        } else {
//            // Trigger已存在，那么更新相应的定时设置
//            //表达式调度构建器
//            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
//            //按新的cronExpression表达式重新构建trigger
//            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
//                    .withSchedule(scheduleBuilder).build();
//            //按新的trigger重新设置job执行
//            scheduler.rescheduleJob(triggerKey, trigger);
//        }
//
//
//    }
//
//    /**
//     * 系统任务调度配置化，需要在用到时候  在方法中加入@PostConstruct 只定在servlet加载前执行
//     * @throws Exception
//     */
//    @PostConstruct
//    public void init() throws Exception {
//        /**在初始化的时候 当任务是可用的 并且运行状态处于 运行时  才添加进去**/
//		List<ScheduleJob> jobList = new ArrayList<>();
//
//
//        //TODO 项目启动时初始化定时任务，一般从数据库查询需要加入的定时任务
//        //此处模拟查到的数据
//        for (int i = 0; i < 5; i++) {
//            ScheduleJob scheduleJob = new ScheduleJob();
//            scheduleJob.setJobId("10001" + i);
//            scheduleJob.setJobName("jobName" + i);
//            scheduleJob.setJobGroup("jobGroup" + i);
//            scheduleJob.setJobStatus("1");
//            scheduleJob.setCronExpression("0/1 * * * * ?");
//            scheduleJob.setDesc("第" + i + "==============》desc" + i);
//            jobList.add(scheduleJob);
//        }
//
//        if (!WinterUtils.isEmpty(jobList)) {
//            for (ScheduleJob job : jobList) {
//                addJob(job);
//            }
//        }
//    }
//
//    /**
//     * 获取所有计划中的任务列表
//     *
//     * @return
//     * @throws SchedulerException
//     */
//    public List<ScheduleJob> getAllJob() throws SchedulerException {
//
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
//        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
//        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
//        for (JobKey jobKey : jobKeys) {
//            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
//            for (Trigger trigger : triggers) {
//                ScheduleJob job = new ScheduleJob();
//                job.setJobName(jobKey.getName());
//                job.setJobGroup(jobKey.getGroup());
//                job.setDesc("触发器:" + trigger.getKey());
//                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
//                job.setJobStatus(triggerState.name());
//                if (trigger instanceof CronTrigger) {
//                    CronTrigger cronTrigger = (CronTrigger) trigger;
//                    String cronExpression = cronTrigger.getCronExpression();
//                    job.setCronExpression(cronExpression);
//                }
//                jobList.add(job);
//            }
//        }
//        return jobList;
//    }
//
//    /**
//     * 所有正在运行的job
//     *
//     * @return
//     * @throws SchedulerException
//     */
//    public List<ScheduleJob> getRunningJob() throws SchedulerException {
//
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
//        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
//        for (JobExecutionContext executingJob : executingJobs) {
//            ScheduleJob job = new ScheduleJob();
//            JobDetail jobDetail = executingJob.getJobDetail();
//            JobKey jobKey = jobDetail.getKey();
//            Trigger trigger = executingJob.getTrigger();
//            job.setJobName(jobKey.getName());
//            job.setJobGroup(jobKey.getGroup());
//            job.setDesc("触发器:" + trigger.getKey());
//            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
//            job.setJobStatus(triggerState.name());
//            if (trigger instanceof CronTrigger) {
//                CronTrigger cronTrigger = (CronTrigger) trigger;
//                String cronExpression = cronTrigger.getCronExpression();
//                job.setCronExpression(cronExpression);
//            }
//            jobList.add(job);
//        }
//        return jobList;
//    }
//
//    /**
//     * 暂停一个job
//     *
//     * @param scheduleJob
//     * @throws SchedulerException
//     */
//    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
//
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
//        scheduler.pauseJob(jobKey);
//    }
//
//    /**
//     * 恢复一个job
//     *
//     * @param scheduleJob
//     * @throws SchedulerException
//     */
//    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
//
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
//        scheduler.resumeJob(jobKey);
//    }
//
//    /**
//     * 删除一个job
//     *
//     * @param scheduleJob
//     * @throws SchedulerException
//     */
//    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
//
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
//        scheduler.deleteJob(jobKey);
//
//    }
//
//    /**
//     * 立即执行job 只能执行一次！！！
//     *
//     * @param scheduleJob
//     * @throws SchedulerException
//     */
//    public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
//        scheduler.triggerJob(jobKey);
//    }
//
//    /**
//     * 更新job时间表达式
//     *
//     * @param scheduleJob
//     * @throws SchedulerException
//     */
//    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
//
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//
//        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
//
//        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
//
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
//
//        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
//
//        scheduler.rescheduleJob(triggerKey, trigger);
//    }
//
//    /**
//     * 判断传入的jobGroup 和 jobName 是不是在定时任务中存在
//     * @param scheduleJob
//     * @return
//     */
//    public boolean judgeJobGroupAndName(ScheduleJob scheduleJob)throws SchedulerException{
//
//        try{
//            org.springframework.util.Assert.notNull(scheduleJob);
//            String jobGroup = scheduleJob.getJobGroup();
//            String jobName = scheduleJob.getJobName();
//            org.springframework.util.Assert.hasLength(jobGroup);
//            org.springframework.util.Assert.hasLength(jobName);
//            Scheduler scheduler = schedulerFactoryBean.getScheduler();
//            /**获得 这个jobGroup中的所有任务**/
//            GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(scheduleJob.getJobGroup());
//            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
//
//            /**遍历 job名**/
//            for (JobKey jobKey : jobKeys) {
//                String name = jobKey.getName();
//                if (name != null && name.equals(jobName)) {
//                    return true;
//                }
//            }
//        }
//        catch (Exception e){
//            return false;
//        }
//        return false;
//    }
//}
