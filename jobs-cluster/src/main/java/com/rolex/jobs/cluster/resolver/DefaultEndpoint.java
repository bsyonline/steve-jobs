/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.cluster.resolver;

/**
 * @author rolex
 * @since 2018
 */
public class DefaultEndpoint implements NodeEndpoint {
    protected final String networkAddress;
    protected final int port;
    protected final boolean isSecure;
    protected final String relativeUri;
    protected final String serviceUrl;

    public DefaultEndpoint(String networkAddress, int port, boolean isSecure, String relativeUri) {
        this.networkAddress = networkAddress;
        this.port = port;
        this.isSecure = isSecure;
        this.relativeUri = relativeUri;

        StringBuilder sb = new StringBuilder()
                .append(isSecure ? "https" : "http")
                .append("://")
                .append(networkAddress);
        if (port >= 0) {
            sb.append(':')
                    .append(port);
        }
        if (relativeUri != null) {
            if (!relativeUri.startsWith("/")) {
                sb.append('/');
            }
            sb.append(relativeUri);
        }
        this.serviceUrl = sb.toString();
    }

    @Override
    public String getServiceUrl() {
        return serviceUrl;
    }

    @Override
    public String getNetworkAddress() {
        return networkAddress;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isSecure() {
        return isSecure;
    }

    @Override
    public String getRelativeUri() {
        return relativeUri;
    }
}
