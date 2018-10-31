/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.cluster.controller;

import com.rolex.jobs.cluster.conf.Configure;
import com.rolex.jobs.cluster.entity.JobInfo;
import com.rolex.jobs.cluster.resolver.RestTemplateHttpClientFactory;
import com.rolex.jobs.cluster.util.ZookeeperUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.List;

/**
 * @author rolex
 * @since 2018
 */
@RestController
public class JobController {

    @PostMapping("/createJob")
    public String job(JobInfo jobInfo) throws Exception {
        // check weather master node or not
        if (Configure.getAsString("master").equals(Configure.getAsString("host"))) {
            List<String> jobNodes = ZookeeperUtils.getChildren(Configure.getAsString("root"));
            for (String jobNode : jobNodes) {
                ResponseEntity<String> info = new RestTemplateHttpClientFactory()
                        .createRestTemplateHttpClient(new URL("http://" + jobNode))
                        .dispatcher(jobInfo);
                System.out.println(String.format("job %s dispatcher to node %s", new String[]{jobInfo.getJobName(), jobNode}));

            }
        }
        return "success";
    }

    @PostMapping("/jobs/dispatcher")
    public String waitForJob() {
        System.out.println("receive job...");
        return "123";
    }
}
