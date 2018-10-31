/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.cluster.conf;

import com.google.common.collect.Maps;
import com.rolex.jobs.cluster.util.FileUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author rolex
 * @since 2018
 */
@Component
public class Configure {

    private static final String JOB_HOME = "D:/Dev/IdeaProjects/steve-jobs/jobs-cluster";
    static Map<String, Object> conf = Maps.newHashMap();

    public Configure() {
        String homePath = System.getenv("JOB_HOME") == null ? JOB_HOME : System.getenv("JOB_HOME");
        List<String> list = FileUtils.read(homePath + "/conf/job.conf");
        list.forEach(l -> conf.put(l.split("=")[0], l.split("=")[1]));
    }

    public static String getAsString(String key) {
        return (String) conf.get(key);
    }

    public static Integer getAsIntger(String key) {
        return (Integer) conf.get(key);
    }

}
