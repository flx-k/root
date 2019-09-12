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
import java.util.Properties;

@Component
public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private static final String LOCATIONS [] = {"D:\\11\\dd\\application.yml"};
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        for(String fileLocation :  LOCATIONS){
            File file = new File(fileLocation);
            if (file.exists()) {
                MutablePropertySources propertySources = environment.getPropertySources();
                YamlPropertiesFactoryBean bean=new YamlPropertiesFactoryBean();
                bean.setResources(new FileSystemResource(file));
                Properties properties = bean.getObject();
                propertySources.addFirst(new PropertiesPropertySource("Config", properties));
                return ;
            }
        }
    }
}
