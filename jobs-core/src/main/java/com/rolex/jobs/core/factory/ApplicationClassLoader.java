/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.jobs.core.factory;

import com.rolex.jobs.core.util.CollectionUtils;
import com.rolex.jobs.core.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rolex
 * @since 2018
 */
public class ApplicationClassLoader extends URLClassLoader {
    
    static Logger logger = LoggerFactory.getLogger(ApplicationClassLoader.class);
    private Map<String, Class<?>> classMap = new HashMap<>();
    private ClassLoader classLoader;
    private boolean entrust;
    
    ApplicationClassLoader(ClassLoader parent, boolean entrust) {
        super(new URL[]{}, parent);
        Assert.notNull(getParent(), "parent can't be null.");
        this.entrust = entrust;
        if (classLoader == null) {
            classLoader = getSystemClassLoader();
            while (classLoader.getParent() != null) {
                classLoader = classLoader.getParent();
            }
        }
    }
    
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }
    
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = classMap.get(name);
        if (clazz != null) {
            return clazz;
        }
        synchronized (getClassLoadingLock(name)) {
            try {
                clazz = findLoadedClass(name);
                if (clazz != null) {
                    if (resolve) {
                        resolveClass(clazz);
                    }
                    return clazz;
                }
            } catch (Throwable e) {
                //ignored
            }
            try {
                clazz = classLoader.loadClass(name);
                if (clazz != null) {
                    if (resolve) {
                        resolveClass(clazz);
                    }
                    return clazz;
                }
            } catch (Throwable e) {
                //ignored
            }
            try {
                if (entrust) {
                    clazz = super.loadClass(name, resolve);
                    if (clazz != null) {
                        if (resolve) {
                            resolveClass(clazz);
                        }
                        return clazz;
                    }
                }
            } catch (Throwable e) {
                //ignored
            }
            try {
                InputStream resource = getResourceAsStream(binaryNameToPath(name, false));
                byte[] bytes = IOUtils.readStreamBytes(resource);
                clazz = defineClass(name, bytes, 0, bytes.length);
                if (clazz != null) {
                    classMap.put(name, clazz);
                    if (resolve) {
                        resolveClass(clazz);
                    }
                    return clazz;
                }
            } catch (Throwable e) {
                //ignored
            }
            try {
                if (!entrust) {
                    clazz = super.loadClass(name, resolve);
                    if (clazz != null) {
                        if (resolve) {
                            resolveClass(clazz);
                        }
                        return clazz;
                    }
                }
            } catch (Throwable e) {
                //ignored
            }
            throw new ClassNotFoundException();
        }
    }
    
    private String binaryNameToPath(String binaryName, boolean withLeadingSlash) {
        // 1 for leading '/', 6 for ".class"
        StringBuilder path = new StringBuilder(7 + binaryName.length());
        if (withLeadingSlash) {
            path.append('/');
        }
        path.append(binaryName.replace('.', '/'));
        path.append(".class");
        return path.toString();
    }
    
    public synchronized void addFiles(Object... filePaths) {
        if (CollectionUtils.isEmpty(filePaths)) {
            return;
        }
        for (Object filePath : filePaths) {
            File file = new File(filePath.toString());
            if (file.exists()) {
                try {
                    addURL(file.toURI().toURL());
                } catch (Throwable e) {
                    logger.warn("jar file [" + filePath + "] can't be add.");
                }
            } else {
                logger.warn("jar file [" + filePath + "] can't be found.");
            }
        }
    }
    
}
