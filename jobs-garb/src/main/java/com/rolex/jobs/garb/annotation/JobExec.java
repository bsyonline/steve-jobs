/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.garb.annotation;

import java.lang.annotation.*;

/**
 * @author rolex
 * @since 2018
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobExec {
}
