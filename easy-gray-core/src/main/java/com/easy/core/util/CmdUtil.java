package com.easy.core.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * java调用命令行工具封装
 * @author liuph
 */
@Slf4j
public class CmdUtil {

    /**
     * 执行cmd 命令
     * @param cmds cmd 命令行
     * @return 控制台日志信息
     */
    public static String exec(long timout,TimeUnit timeUnit,String... cmds){
        Process process = RuntimeUtil.exec(cmds);
        try {
            process.waitFor(timout, timeUnit);
            try(InputStream is = process.getInputStream();){
                return IoUtil.read(is, StandardCharsets.UTF_8);
            }catch (Exception e){
                log.error("read exec result,error , cmds : {}", JSON.toJSONString(cmds),e);
            }
        } catch (InterruptedException e) {
            log.error("exec error , cmds : {}", JSON.toJSONString(cmds),e);
        }
        return null;
    }
}
