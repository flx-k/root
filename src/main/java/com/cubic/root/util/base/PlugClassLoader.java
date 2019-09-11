package com.cubic.root.util.base;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class PlugClassLoader extends URLClassLoader {
    private Map<String, JarURLConnection> urlConnectionMap=new HashMap<>();

    public PlugClassLoader() {
        super(new URL[] {}, ClassLoader.getSystemClassLoader());
    }



    public void addURLFile(URL fileURL){
//        URLConnection uc = null;
//        try {
//            uc = fileURL.openConnection();
//            if (uc instanceof JarURLConnection) {
//                uc.setUseCaches(true);
//                ((JarURLConnection) uc).getManifest();
//                urlConnectionMap.put("aa", (JarURLConnection) uc);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        addURL(fileURL);
    }

    public void removeJar(String name){
        JarURLConnection jarURLConnection=urlConnectionMap.get(name);
        try {
            jarURLConnection.getJarFile().close();
            jarURLConnection=null;
            urlConnectionMap.remove(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
