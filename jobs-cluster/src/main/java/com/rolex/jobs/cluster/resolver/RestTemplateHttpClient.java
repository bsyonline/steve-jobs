/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.cluster.resolver;

import com.rolex.jobs.cluster.entity.JobInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author rolex
 * @since 2018
 */
public class RestTemplateHttpClient implements JobClient {
    protected final Log logger = LogFactory.getLog(getClass());
    private RestTemplate restTemplate;
    private String serviceUrl;

    public RestTemplateHttpClient(RestTemplate restTemplate, String serviceUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = serviceUrl;
        if (!serviceUrl.endsWith("/")) {
            this.serviceUrl = this.serviceUrl + "/";
        }
    }

    @Override
    public ResponseEntity<String> dispatcher(JobInfo jobInfo) {
        String urlPath = serviceUrl + "jobs/dispatcher";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<String> response = restTemplate.exchange(urlPath, HttpMethod.POST,
                new HttpEntity<>(jobInfo, headers), String.class);
        return response;
    }
}
