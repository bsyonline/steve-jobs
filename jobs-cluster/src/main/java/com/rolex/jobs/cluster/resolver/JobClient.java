/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.cluster.resolver;

import com.rolex.jobs.cluster.entity.JobInfo;
import org.springframework.http.ResponseEntity;

/**
 * @author rolex
 * @since 2018
 */
public interface JobClient {

    ResponseEntity<String> dispatcher(JobInfo jobInfo);
}
