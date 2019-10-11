package com.cubic.root;

import com.cubic.root.util.base.JARChange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 初始化完成后的操作
 */
@Component
public final class AfterRun implements ApplicationRunner {
    Logger logger= LogManager.getLogger(AfterRun.class);
    @Autowired
    public ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("加载插件.........");
        String dir=System.getenv("ROOT_PLUG_PATH");
        if(null==dir)
            logger.warn("插件地址为空");
        if(null!=dir&&!dir.isEmpty())
            JARChange.run(dir,applicationContext);
        logger.info("插件加载完成.........");
    }
}
