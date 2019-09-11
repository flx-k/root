package com.cubic.root.util.base;

import com.cubic.root.util.exception.*;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandle {
    public static Map<String,Object> buildExceptMsg(Exception exception){
        Map<String,Object> map=new HashMap<>();
        map.put("status",false);
        if(NoPlugException.class==exception.getClass()){
            map.put("error",exception.getMessage());
        }else if(ExistException.class==exception.getClass()){
            map.put("error",exception.getMessage());
        }else if(NotExistException.class==exception.getClass()){
            map.put("error",exception.getMessage());
        }else if(CantDoException.class==exception.getClass()){
            map.put("error",exception.getMessage());
        }
        else{
            map.put("error","未知问题:"+exception.getMessage());
        }
        return map;
    }
}
