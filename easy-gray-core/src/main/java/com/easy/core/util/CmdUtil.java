package com.easy.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * java调用命令行工具封装
 * @author liuph
 */
@Slf4j
public class CmdUtil {

    /**
     * 执行cmd 命令
     * @param cmds cmd 命令行
     */
    public static void exec(String... cmds){
        try {
            // 构造 ProcessBuilder 对象，指定要执行的命令
            ProcessBuilder builder = new ProcessBuilder(cmds);
            // 将错误输出合并到标准输出
            builder.redirectErrorStream(true);

            // 启动进程并等待执行完成
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // 实时输出脚本的标准输出
               log.info(line);
            }
            // 等待进程结束并获取退出码
            int exitCode = process.waitFor();
            log.info("cmds exit code =  " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
