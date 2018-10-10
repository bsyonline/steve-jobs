/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.core.factory;


import com.rolex.jobs.garb.annotation.Job;
import com.rolex.jobs.garb.annotation.JobExec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 应用的类加载器工厂,负责管理节点类加载器和jar包类加载器
 * <p>
 *
 * @author rolex
 * @since 2018
 */
public class ApplicationClassLoaderFactory {
    
    static String jarRepository;
    
    static Logger logger = LoggerFactory.getLogger(ApplicationClassLoaderFactory.class);
    private static Map<String, ApplicationClassLoader> jarApplicationClassLoaderCache = new HashMap<>();
    private static String jobDir;
    private static final ClassLoader SYSTEM_CLASSLOADER = ClassLoader.getSystemClassLoader();
    private static ApplicationClassLoader applicationClassLoader;
    
    static {
        ResourceBundle rb = ResourceBundle.getBundle("job");
        jarRepository = rb.getString("job.jar.repository");
        jobDir = jarRepository + "/jobs";
        applicationClassLoader = getApplicationClassLoader();
        loadJars();
    }
    
    /**
     * 初始化，将符合条件的任务jar加载到类加载器
     */
    private static void loadJars() {
        File libFile = new File(jobDir);
        if (!libFile.exists()) {
            throw new IllegalArgumentException("can't find lib path. [" + jobDir + "]");
        }
        java.util.List<String> filePathList = new ArrayList<>();
        
        File[] jarFiles = libFile.listFiles();
        for (File jarFile : jarFiles) {
            if (jarFile.getName().endsWith(".jar")) {
                filePathList.add(jarFile.getAbsolutePath());
            }
        }
        applicationClassLoader.addFiles(filePathList.toArray());
        logger.info("jars loaded");
    }
    
    /**
     * 重新加载jar包到类加载器
     */
    public static void reload() {
        loadJars();
        logger.info("jars upgrade");
    }
    
    /**
     * 获取应用的类加载器
     *
     * @return
     */
    public synchronized static ApplicationClassLoader getApplicationClassLoader() {
        if (applicationClassLoader != null) {
            return applicationClassLoader;
        }
        if (SYSTEM_CLASSLOADER == null) {
            throw new IllegalStateException("can't create classLoader because systemClassLoader is null.");
        }
        applicationClassLoader = new ApplicationClassLoader(SYSTEM_CLASSLOADER, true);
        return applicationClassLoader;
    }
    
    /**
     * 根据包名和方法名反射调用，被标记 @Job
     *
     * @param fullClassName
     * @param methodName
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void scanAndInvoke(String fullClassName, String methodName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class clazz = applicationClassLoader.loadClass(fullClassName);
        Object obj = clazz.newInstance();
        Annotation annotation = clazz.getDeclaredAnnotation(Job.class);
        if (annotation == null) {
            throw new RuntimeException("Can't exec job [" + fullClassName + "] because annotation @Job lost.");
        }
        Method method = clazz.getDeclaredMethod(methodName, new Class[]{});
        if (method.getAnnotation(JobExec.class) == null) {
            throw new RuntimeException("Can't exec job [" + fullClassName + "." + methodName + "] because annotation @JobExec lost.");
        }
        method.invoke(obj, new Object[]{});
    }
}
