/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.core.util;

import java.util.Collection;

/**
 * @author rolex
 * @since 2018
 */
public class CollectionUtils {
    
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }
}
