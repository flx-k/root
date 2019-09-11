package com.cubic.root.util.base;

import com.cubic.root.util.exception.NoPlugException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JARChange {
    static Logger logger= LogManager.getLogger(JARChange.class);
    public static Map<String,String> classMap=new HashMap<>();
    public static Map<String, Set> fileClassMap=new HashMap<>();
    public static String getClassName(String key) throws NoPlugException {
        key="server-plug-"+key;
        if(!classMap.containsKey(key.toUpperCase()))
            throw new NoPlugException(key);
        return classMap.get(key.toUpperCase());
    }

    public static void run(String path, ApplicationContext applicationContext) throws Exception {
        FileFilter fileFilter= FileFilterUtils.and(new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                String extension=FilenameUtils.getExtension(file.getAbsolutePath());
                return "jar".equalsIgnoreCase(extension);
            }
            @Override
            public boolean accept(File file, String s) {
                return false;
            }
        });
        FileAlterationObserver fileAlterationObserver=new FileAlterationObserver(path,fileFilter);
        fileAlterationObserver.addListener(new FileAlterationListenerAdaptor(){
            @Override
            public void onDirectoryChange(File directory) {
                logger.info("onDirectoryChange::"+directory.getAbsolutePath());
                super.onDirectoryChange(directory);
            }
            @Override
            public void onFileChange(File file) {
                logger.info("onFileChange::"+file.getAbsolutePath());
                createBean(file,applicationContext);
                super.onFileChange(file);
            }
            @Override
            public void onFileCreate(File file) {
                logger.info("onFileCreate::"+file.getAbsolutePath());
                createBean(file,applicationContext);
                super.onFileCreate(file);
            }
            @Override
            public void onFileDelete(File file) {
                logger.info("onFileDelete::"+file.getAbsolutePath());
                super.onFileDelete(file);
            }
        });
        FileAlterationMonitor filealterationMonitor=new FileAlterationMonitor(1000);
        filealterationMonitor.addObserver(fileAlterationObserver);
        filealterationMonitor.start();

        File f = new File(path);
        if (f.isDirectory()) {
            for (File _f : f.listFiles()) {
                if (_f.isFile()&&_f.getAbsolutePath().endsWith(".jar")) {
                    createBean(_f,applicationContext);
                }
            }
        }
    }
    private static void createBean(File file,ApplicationContext applicationContext){
        Map<String,String> cMap;
        try {
            cMap=ClassUtil.registerBean(file,applicationContext);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return;
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(),e);
            return;
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
            return;
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(),e);
            return;
        } catch (InstantiationException e) {
            logger.error(e.getMessage(),e);
            return;
        }
        if(cMap.size()>0){
            fileClassMap.put(file.getAbsolutePath(),cMap.keySet());
            classMap.putAll(cMap);
        }
    }
}
