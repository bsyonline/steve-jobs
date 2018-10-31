package com.rolex.jobs.cluster.resolver;

/**
 * @author rolex
 * @since 2018
 */
public interface NodeEndpoint {

    String getServiceUrl();

    String getNetworkAddress();

    int getPort();

    boolean isSecure();

    String getRelativeUri();
}
