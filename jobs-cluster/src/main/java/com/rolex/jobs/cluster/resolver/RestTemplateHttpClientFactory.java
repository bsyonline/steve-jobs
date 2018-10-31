/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.cluster.resolver;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author rolex
 * @since 2018
 */
public class RestTemplateHttpClientFactory {

    public RestTemplateHttpClient createRestTemplateHttpClient(URL serviceUrl) {
        boolean isSecure = "https".equalsIgnoreCase(serviceUrl.getProtocol());
        int defaultPort = isSecure ? 443 : 80;
        int port = serviceUrl.getPort() == -1 ? defaultPort : serviceUrl.getPort();
        return newClient(new DefaultEndpoint(serviceUrl.getHost(), port, isSecure, serviceUrl.getPath()));
    }

    public RestTemplateHttpClient newClient(NodeEndpoint serviceUrl) {
        return new RestTemplateHttpClient(restTemplate(serviceUrl.getServiceUrl()),
                serviceUrl.getServiceUrl());
    }

    private RestTemplate restTemplate(String serviceUrl) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            URI serviceURI = new URI(serviceUrl);
            if (serviceURI.getUserInfo() != null) {
                String[] credentials = serviceURI.getUserInfo().split(":");
                if (credentials.length == 2) {
                    restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(
                            credentials[0], credentials[1]));
                }
            }
        } catch (URISyntaxException ignore) {

        }

        return restTemplate;
    }
}
