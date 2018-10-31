/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.cluster.resolver;

/**
 * @author rolex
 * @since 2018
 */
public class TransportException extends RuntimeException {
    public TransportException(String message) {
        super(message);
    }

    public TransportException(String message, Throwable cause) {
        super(message, cause);
    }
}