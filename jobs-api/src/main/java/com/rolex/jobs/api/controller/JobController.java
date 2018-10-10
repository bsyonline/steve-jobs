/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.api.controller;

import com.rolex.jobs.api.service.JobService;
import com.rolex.jobs.core.factory.ApplicationClassLoaderFactory;
import com.rolex.jobs.model.JobBean;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rolex
 * @since 2018
 */
@RestController
public class JobController {
    
    @Autowired
    JobService jobService;
    
    @GetMapping("/jobs/1")
    public void jobs1() throws SchedulerException {
        JobBean jobBean = new JobBean();
        jobBean.setCronExpression("0/1 * * * * ?");
        jobBean.setDescription("PrintJobWithAnnotation");
        jobBean.setJobClassName("com.chinadaas.job.PrintJobWithAnnotation");
        jobBean.setJobMethodName("print");
        jobBean.setJobGroup("test");
        jobBean.setJobName("PrintJobWithAnnotation");
        jobService.addJob(jobBean);
    }
    
    @GetMapping("/jobs/2")
    public void jobs2() throws SchedulerException {
        JobBean jobBean = new JobBean();
        jobBean.setCronExpression("0/1 * * * * ?");
        jobBean.setDescription("PrintJobMethodNoAnnotation");
        jobBean.setJobClassName("com.chinadaas.job.PrintJobMethodNoAnnotation");
        jobBean.setJobMethodName("print");
        jobBean.setJobGroup("test");
        jobBean.setJobName("PrintJobMethodNoAnnotation");
        jobService.addJob(jobBean);
    }
    
    @GetMapping("/jobs/3")
    public void jobs3() throws SchedulerException {
        JobBean jobBean = new JobBean();
        jobBean.setCronExpression("0/1 * * * * ?");
        jobBean.setDescription("PrintJob");
        jobBean.setJobClassName("com.chinadaas.job.PrintJob");
        jobBean.setJobMethodName("print");
        jobBean.setJobGroup("test");
        jobBean.setJobName("PrintJob");
        jobService.addJob(jobBean);
    }
    
    @PostMapping("/jobs")
    public void job4(@RequestBody JobBean jobBean) throws SchedulerException {
        jobService.addJob(jobBean);
    }
    
    @GetMapping("/upload")
    public void upload() {
        System.out.println("jar uploaded...");
        ApplicationClassLoaderFactory.reload();
    }
}
