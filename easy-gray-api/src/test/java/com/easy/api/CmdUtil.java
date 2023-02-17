package com.easy.api;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RuntimeUtil;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author liuph
 * @desc
 * @date 2022/11/14 00:12
 */
public class CmdUtil {
    public static void main(String[] args) throws Exception {

        Process process = RuntimeUtil.exec("sh /Volumes/DATA/Users/liupenghao/work/idea/mime/easy-gray/easy-gray-api/src/test/resources/start.sh");
        process.waitFor(10, TimeUnit.SECONDS);

        try(InputStream is = process.getInputStream();){
            String read = IoUtil.read(is, StandardCharsets.UTF_8);
            System.out.println(read);
        }catch (Exception e){

        }
    }
}
