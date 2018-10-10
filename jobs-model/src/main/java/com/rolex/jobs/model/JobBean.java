/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.model;

import java.io.Serializable;

/**
 * @author rolex
 * @since 2018
 */
public class JobBean implements Serializable {
    
    public static final int RUNNING = JOB_STATUS.RUNNING.getStatus();
    public static final int PAUSE = JOB_STATUS.PAUSE.getStatus();
    public static final int CONCURRENT_IS = 1;
    public static final int CONCURRENT_NOT = 0;
    
    public enum JOB_STATUS {
        /**
         * 运行
         */
        RUNNING(1),
        /**
         * 暂停
         */
        PAUSE(2);
        
        JOB_STATUS(Integer status) {
            this.status = status;
        }
        
        private Integer status;
        
        public Integer getStatus() {
            return status;
        }
        
        public void setStatus(Integer status) {
            this.status = status;
        }
    }
    
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 执行类
     */
    private String jobClassName;
    /**
     * 执行方法
     */
    private String jobMethodName;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 触发器状态
     */
    private String triggerState;
    
    public String getJobName() {
        return jobName;
    }
    
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public String getJobGroup() {
        return jobGroup;
    }
    
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getJobClassName() {
        return jobClassName;
    }
    
    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }
    
    public String getCronExpression() {
        return cronExpression;
    }
    
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    
    public String getTriggerState() {
        return triggerState;
    }
    
    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }
    
    public String getJobMethodName() {
        return jobMethodName;
    }
    
    public void setJobMethodName(String jobMethodName) {
        this.jobMethodName = jobMethodName;
    }
}
