/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.cluster;

import com.rolex.jobs.cluster.conf.Configure;
import com.rolex.jobs.cluster.util.ZookeeperUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @author rolex
 * @since 2018
 */
@SpringBootApplication
public class JobNode {

    public static void main(String[] args) {
        SpringApplication.run(JobNode.class, args);
    }


    @PostConstruct
    public void init() throws Exception {
        loadConf();
        String root = Configure.getAsString("root");
        CuratorFramework curator = ZookeeperUtils.curator(Configure.getAsString("zookeeper"));
        curator.start();
        Stat stat = curator.checkExists().forPath(root);
        if (stat == null) {
            // create node
            String path = curator.create()
                    .creatingParentsIfNeeded() // create parent path
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(root);
            System.out.println("root node created: " + path);
        }
        ZookeeperUtils.createNode(root, Configure.getAsString("host") + ":" + Configure.getAsString("port"));
    }

    @Bean
    Configure loadConf() {
        return new Configure();
    }


}
