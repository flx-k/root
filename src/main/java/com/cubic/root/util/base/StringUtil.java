package com.cubic.root.util.base;

import java.util.UUID;

public class StringUtil {
    public static String buildID(){
        return UUID.randomUUID().toString();
    }
    public static String upper2Lower(String str){
        char[] cs=str.toCharArray();
        StringBuffer sb=new StringBuffer();
        int a=0;
        for(char c:cs){
            if(a==0)
                sb.append(Character.toLowerCase(c));
            else if(Character.isUpperCase(c)){
                sb.append("_").append(Character.toLowerCase(c));
            }else sb.append(c);
            a=1;
        }
        return sb.toString();
    }
}
