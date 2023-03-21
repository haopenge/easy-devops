package com.easy.api;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

/**
 * @author liuph
 * @desc
 * @date 2022/11/14 00:12
 */
public class CmdUtil {
    public static void main(String[] args) throws Exception {
        String executePath = "/Volumes/DATA/Users/liupenghao/work/idea/mime/easy-gray/easy-gray-example/easy-gray-gateway-api";

        // 文件复制 处理
        String startShPath = executePath + File.separator + "deploy.sh";
        // 构建镜像
        String commandLineStr = format("sh %s %s %s %s %s",startShPath,executePath, "","qa-12138","666");
        String returnLogStr = com.easy.core.util.CmdUtil.exec(10, TimeUnit.SECONDS, commandLineStr);
        System.out.println(returnLogStr);
    }
}
