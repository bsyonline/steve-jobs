/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.core.quartz.job;

import com.rolex.jobs.core.factory.ApplicationClassLoaderFactory;
import com.rolex.jobs.model.JobBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author rolex
 * @since 2018
 */
public class QuartzJobFactory implements Job {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobBean jobBean = (JobBean) context.getMergedJobDataMap().get("scheduleJob");
        logger.info("运行任务名称 = [" + jobBean.getJobName() + "]");
        try {
            exec(jobBean.getJobClassName(), jobBean.getJobMethodName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    public void exec(String fullClassName, String methodName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ApplicationClassLoaderFactory.scanAndInvoke(fullClassName, methodName);
    }
    
}