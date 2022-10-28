package com.easy.loadbalancer;


import java.util.HashMap;
import java.util.Map;

/**
 * 环境标识 threadLocal
 */
public class EnvHolder {

    private EnvHolder(){

    }

    private static final ThreadLocal<Map<String, String>> envLocal = ThreadLocal.withInitial(HashMap::new);

    public static void setEnv(String env,String value){
        Map<String, String> map = envLocal.get();
        map.put(env,value);
    }

    public static String getEnv(String env){
        Map<String, String> map = envLocal.get();
        return map.getOrDefault(env,null);
    }

    public static void clear(){
        envLocal.remove();
    }

}
