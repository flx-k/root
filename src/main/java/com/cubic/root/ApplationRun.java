package com.cubic.root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
@EnableAutoConfiguration
@RestController
@MapperScan(basePackages = "com.cubic.root.dao")
public class ApplationRun {
    Logger logger= LogManager.getLogger(ApplationRun.class);
    private static String[] args;
    private static ConfigurableApplicationContext context;
    public static void main(String[] args) {
        ApplationRun.args=args;
        ApplationRun.context= SpringApplication.run(ApplationRun.class, args);
    }
    @GetMapping("/refresh")
    public String restart(){
        logger.info("开始重启.....");
        ExecutorService threadPool = new ThreadPoolExecutor(1,1,0, TimeUnit.SECONDS,new ArrayBlockingQueue<>( 1 ),new ThreadPoolExecutor.DiscardOldestPolicy ());
        threadPool.execute (()->{
            ApplationRun.context.close ();
            ApplationRun.context = SpringApplication.run ( ApplationRun.class,ApplationRun.args );
        } );
        threadPool.shutdown ();
        return "重启完成";
    }
}
