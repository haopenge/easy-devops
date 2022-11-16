package com.easy.api;

import com.easy.api.util.CommandUtil;
import org.apache.commons.exec.CommandLine;

/**
 * @author liuph
 * @desc
 * @date 2022/11/14 00:12
 */
public class CmdUtil {
    public static void main(String[] args) {
        CommandLine commandLine = CommandLine.parse(String.format("sh %s %s %s","/Volumes/DATA/Users/liupenghao/work/idea/mime/easy-gray/easy-gray-api/src/test/resources/start.sh",
                "easy-12138","easy-gray-gateway-api"));
        CommandUtil.execCmdWithoutResult(commandLine,600);

    }
}
