package com.yestic.winter.job;


import com.yestic.winter.config.SpringBeanUtil;
import com.yestic.winter.dto.ScheduleJob;
import com.yestic.winter.util.WinterUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chenyi on 2018/1/3
 */
public class TaskUtils {

    public final static Logger log = Logger.getLogger(TaskUtils.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokMethod(ScheduleJob scheduleJob) {
        Object object = null;
        Class clazz = null;
        // springbean不为空先按springbean查找bean
        if (WinterUtils.isNotNull(scheduleJob.getSpringBean())) {
            object = SpringBeanUtil.getBean(scheduleJob.getSpringBean());
        }
        // 任务执行时调用哪个类的方法 包名+类名 --- 目前没有精确到这一项
//        else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
//            try {
//                clazz = Class.forName(scheduleJob.getBeanClass());
//                object = clazz.newInstance();
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }
        if (object == null) {
            log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查是否配置正确！！！");
            return;
        }
        clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(scheduleJob.getSpringMethod());
        } catch (NoSuchMethodException e) {
            log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！");
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (method != null) {
            try {
                method.invoke(object);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]----------启动成功");
    }

}
