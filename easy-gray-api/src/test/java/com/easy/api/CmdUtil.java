package com.easy.api;

import com.easy.api.util.CommandUtil;
import org.apache.commons.exec.CommandLine;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author liuph
 * @desc
 * @date 2022/11/14 00:12
 */
public class CmdUtil {
    public static void main(String[] args) throws Exception {

       // String cmdStr = "/Users/liupenghao/Desktop/test/easy-gray/easy-gray/easy-gray-example/easy-gray-gateway-api/start.sh easy-12138 easy-gray-gateway-api /Users/liupenghao/Desktop/test/easy-gray/easy-gray/easy-gray-example/easy-gray-gateway-api 1116221615";

        String cmdStr = "docker build";
        CommandLine commandLine = CommandLine.parse(cmdStr);
        CommandUtil.execCmdWithoutResult(commandLine,600);

       // docker build -t registry.cn-hangzhou.aliyuncs.com/ranmo/${image_suffix} -f Dockerfile .

    }

    public static String getinfor(String command) {
        StringBuilder strb = new StringBuilder();
        //开始和结束的下标
        int beginIndex = 0;
        int endIndex = 0;
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            //读取屏幕输出
            BufferedReader strCon = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = strCon.readLine()) != null) {
                strb.append(line + "\n");
            }
            //根据自己的业务场景来
            if (strb.toString().contains("$body$")) {
                beginIndex = strb.indexOf("$body$");
                endIndex = strb.lastIndexOf("$body$");
            }
        } catch (Exception e) {
            System.out.println("报错信息为：" + e.toString());
        }
        return strb.substring(beginIndex, endIndex);
    }
}
