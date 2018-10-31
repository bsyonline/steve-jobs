/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.cluster.util;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author rolex
 * @since 2018
 */
public class ZookeeperUtils {
    static CuratorFramework curator;

    public static CuratorFramework curator(String host) {
        if (curator == null) {
            curator = CuratorFrameworkFactory.builder()
                    .connectString(host)
                    .connectionTimeoutMs(10000)
                    .retryPolicy(new RetryNTimes(3, 1000))
                    .sessionTimeoutMs(10000)
                    // .authorization() // auth
                    .build();
        }
        return curator;
    }

    public static void createNode(String parent, String val) throws Exception {

        try {
            Stat stat = curator.checkExists().forPath(parent);
            if (stat != null) {
                // create node
                String path = curator.create()
                        .creatingParentsIfNeeded() // create parent path
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(parent + "/" + val, val.getBytes());
                System.out.println("node created: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<String> getChildren(String path) throws Exception {
        List<String> list = Lists.newArrayList();
        // get child node
        List<String> nodes = curator.getChildren().forPath(path);
        for (String name : nodes) {
            System.out.println("child path: " + name);
            Stat stat = new Stat();
            byte[] u = curator.getData().storingStatIn(stat).forPath(path + "/" + name);
            list.add(new String(u));
        }
        return list;
    }
}
