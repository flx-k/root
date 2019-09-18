package com.cubic.root;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.*;

@Component
public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private static String[] LOCATIONS ;
    static {
        String dir=System.getenv("ROOT_CONFIG_PATH");
        System.out.println(dir);
        String[] s=dir.split(";");
        LOCATIONS=s;
    }

    public static void main(String[] args) {
        Map<String, String> map=System.getenv();
        for(String key:map.keySet()){
            System.out.println(key+"    ::    "+map.get(key));
        }
    }
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        YamlPropertiesFactoryBean bean=new YamlPropertiesFactoryBean();
        Properties properties;
        for(String fileLocation :  LOCATIONS){
            File file = new File(fileLocation);
            if (file.exists()) {
                bean.setResources(new FileSystemResource(file));
                properties = bean.getObject();
                propertySources.addFirst(new PropertiesPropertySource("Config", properties));
            }
        }
    }
}
