package com.cubic.root.api;
import com.cubic.root.service.PlugService;
import com.cubic.root.util.base.ExceptionHandle;
import com.cubic.root.util.base.JARChange;
import com.cubic.root.util.exception.NoPlugException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PlugApi {
    Logger logger= LogManager.getLogger(PlugApi.class);
    @Autowired
    public ApplicationContext applicationContext;

    private PlugService getPlugService(String plugName) throws NoPlugException {
        String classname;
        try{
            classname = JARChange.getClassName(plugName);
        }catch (Exception e){
            throw new NoPlugException(plugName);
        }
        return (PlugService) applicationContext.getBean(classname);
    }
    private Object executePlugService(String plugName,String methodName,Object...obj) throws NoPlugException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        try{
            String classname = JARChange.getClassName(plugName);
            Object object=applicationContext.getBean(classname);
            Class cla=object.getClass();
            Method method=cla.getDeclaredMethod(methodName,Object.class);
            return method.invoke(object,obj);
        }catch (Exception e){
            throw new NoPlugException(plugName);
        }

    }
    @RequestMapping("/plug/listPlugClass")
    public Map<String, Object> listPlug() throws Exception {
        logger.info("testPlug");
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("status",true);
            map.put("datas",JARChange.classMap);
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }
    @RequestMapping("/plug/{plugName}/create")
    public Map<String, Object> create(@PathVariable("plugName") String plugName, @RequestParam Object obj) throws Exception {
        logger.info("testPlug");
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("status",true);
            map.put("datas", getPlugService(plugName).create(obj));
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }

    @RequestMapping("/plug/{plugName}/delete")
    public Map<String, Object> plugList(@PathVariable("plugName") String plugName, @RequestParam Object obj) throws Exception {
        logger.info("testPlug");
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("status",true);
            map.put("datas", getPlugService(plugName).delete(obj));
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }
    @RequestMapping("/plug/{plugName}/update")
    public Map<String, Object> update(@PathVariable("plugName") String plugName, @RequestParam Object obj) throws Exception {
        logger.info("testPlug");
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("status",true);
            map.put("datas", getPlugService(plugName).update(obj));
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }
    @RequestMapping("/plug/{plugName}/list")
    public Map<String, Object> list(@PathVariable("plugName") String plugName, @RequestParam Object obj) throws Exception {
        logger.info("testPlug");
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("status",true);
            map.put("datas", getPlugService(plugName).list(obj));
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }
    @RequestMapping("/plug/{plugName}/get")
    public Map<String, Object> get(@PathVariable("plugName") String plugName, @RequestParam Object obj) throws Exception {
        logger.info("testPlug");
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("status",true);
            map.put("datas", getPlugService(plugName).get(obj));
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }
    @RequestMapping("/plug/{plugName}/m/{method}")
    public Map<String, Object> method(@PathVariable("plugName") String plugName,@PathVariable("method") String method, @RequestParam Object obj) throws Exception {
        logger.info("testPlug");
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("status",true);
            map.put("datas", executePlugService(plugName,method,obj));
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }
    @RequestMapping("/test/{plugName}")
    public Map<String, Object> testPlug(@PathVariable("plugName") String plugName) {
        logger.info("testPlug");
        Map<String,Object> map=new HashMap<>();
        try {
            String classname;
            try{
                classname = JARChange.getClassName(plugName);
            }catch (Exception e){
                classname = "accountService";
            }
            executePlugService(plugName,"get","ss");
//            PlugService testImp=  applicationContext.getBean(classname);
            map.put("datas", ("admin"));
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }
}
