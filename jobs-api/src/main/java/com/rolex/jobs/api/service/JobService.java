/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.api.service;

import com.rolex.jobs.core.quartz.job.QuartzJobFactory;
import com.rolex.jobs.model.JobBean;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rolex
 * @since 2018
 */
@Service
public class JobService {
    
    @Autowired
    Scheduler scheduler;
    
    public boolean addJob(JobBean job) throws SchedulerException {
        // 任务名称和任务组设置规则：    // 名称：task_1 ..    // 组 ：group_1 ..
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        //是否允许并发执行
        Class<? extends Job> clazz = QuartzJobFactory.class;
        JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
        jobDetail.getJobDataMap().put("scheduleJob", job);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        // 按新的表达式构建一个新的trigger
        trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
        
        return true;
    }
    
}
