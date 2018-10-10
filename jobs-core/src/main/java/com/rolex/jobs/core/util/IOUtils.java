/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.core.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author rolex
 * @since 2018
 */
public class IOUtils {
    
    public static byte[] readStreamBytes(InputStream inputStream) throws IOException {
        byte[] cache = new byte[2048];
        int len;
        byte[] bytes = new byte[0];
        while ((len = inputStream.read(cache)) > 0) {
            byte[] temp = bytes;
            bytes = new byte[bytes.length + len];
            System.arraycopy(temp, 0, bytes, 0, temp.length);
            System.arraycopy(cache, 0, bytes, temp.length, len);
        }
        if (bytes.length == 0) {
            return null;
        }
        return bytes;
    }
}
