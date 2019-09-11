package com.cubic.root.util.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {
    static Logger logger= LogManager.getLogger(ClassUtil.class);

    /**
     * 加载类，并注册到容器
     * @param path
     * @param applicationContext
     * @return
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, String> registerBean(File path, ApplicationContext applicationContext) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        logger.info("jar包："+path.getAbsolutePath()+"--> 开始加载");
        URL url=path.toURI().toURL();
        URLClassLoader classLoader= (URLClassLoader) ClassLoader.getSystemClassLoader();
        Method method=URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(classLoader,url);
        Map<String,String> map=new HashMap<>();
        JarFile jarFile=new JarFile(path);
        Enumeration<JarEntry> entrys=jarFile.entries();
        JarEntry jarEntry;
        String name,classname;
        String[] names;
        while (entrys.hasMoreElements()){
            jarEntry=entrys.nextElement();
            name=jarEntry.getName();
            if(!jarEntry.isDirectory()&&name.endsWith(".class")){
                classname=name.substring(0,name.length()-6).replace("/",".");
                names=classname.split("\\.");
                map.put("SERVER-PLUG-"+names[names.length-1].toUpperCase(),classname);
                Class cla= null;
                try {
                    cla = Class.forName(classname);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(cla);
                DefaultListableBeanFactory defaultListableBeanFactory= (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
                beanDefinitionBuilder.setScope("singleton");
                if(applicationContext.containsBean(classname))
                    defaultListableBeanFactory.removeBeanDefinition(classname);
                defaultListableBeanFactory.registerBeanDefinition(classname,beanDefinitionBuilder.getBeanDefinition());
            }
        }
        logger.info("jar包："+path.getAbsolutePath()+"--> 加载结束");
        return map;
    }


    public static void main(String[] args) throws Exception {
        String a="asdasd";
        System.out.println(a.split("\\.").length);
        return;
    }

}
