/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.core.quartz.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author rolex
 * @since 2018
 */
@Configuration
public class QuartzConfig {
    
    @Autowired
    private SpringJobFactory springJobFactory;
    
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(springJobFactory);
        return schedulerFactoryBean;
    }
    
    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}