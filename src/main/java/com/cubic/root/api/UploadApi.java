package com.cubic.root.api;

import com.cubic.root.util.base.ExceptionHandle;
import com.cubic.root.util.base.JARChange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UploadApi {
    Logger logger = LogManager.getLogger(UploadApi.class);
    @GetMapping("/testUploadApi")

    public Map<String, Object> listPlug() throws Exception {
        logger.info("testPlug");
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("status",true);
            map.put("datas","test");
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }
    @PostMapping("/uploadPlug")
    public Map<String, Object> uploadPlug(@RequestParam("plugFile") MultipartFile fileUpload) {
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //指定本地文件夹存储图片
        String dir = System.getenv("ROOT_PLUG_PATH");
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == dir){
                logger.warn("插件地址未配置");
                map.put("status", false);
                map.put("datas", "插件地址未配置");
                return map;
            }
            File f=new File(dir + fileName);
            if(f.exists()){
                logger.warn("插件存在");
                map.put("status", false);
                map.put("datas", "插件存在");
                return map;
            }
            fileUpload.transferTo(f);
            map.put("status", true);
            map.put("datas", JARChange.classMap);
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }
}
