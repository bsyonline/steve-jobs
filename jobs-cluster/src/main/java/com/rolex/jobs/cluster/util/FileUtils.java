/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.cluster.util;

import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author rolex
 * @since 2018
 */
public class FileUtils {

    public static List<String> read(String file) {
        List<String> list = Lists.newArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = br.readLine()) != null) {
                if (!s.startsWith("#")) {
                    list.add(s);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
