package com.easy.api.util;

import com.easy.api.domain.enumx.FailureEnum;
import com.easy.api.exception.ServiceException;
import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;

/**
 * @desc 命令行 调用帮助类
 * @author liuph
 * @date 2020/03/06 15:24
 */
public class CommandUtil {

    /**
     * @desc 执行命令行命令
     * @author liuph
     * @date  2020/03/06 15:44
     * @param cmdLine 命令行参数
     * @param timeout 超时时间（单位秒）
     */
    public static void execCmdWithoutResult(CommandLine cmdLine,long timeout){
        DefaultExecutor executor = new DefaultExecutor();
        // 定时成功执行的退出值，未返回抛异常
        executor.setExitValue(0);

        // 设置超时时间
        ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
        executor.setWatchdog(watchdog);
        DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();

        try {
            // 执行命令
            executor.execute(cmdLine, handler);
            // 命令执行返回前一直阻塞
            handler.waitFor();
        } catch (Exception e) {
           throw new ServiceException(FailureEnum.GIT_FETCH_EXCEPTION);
        }
    }



    /**
     * @desc 执行命令行命令
     * @author liuph
     * @date  2020/03/06 15:44
     * @param cmdLine 命令行参数
     * @param timeout 超时时间（单位秒）
     */
    public static CommandResultBo execCmdWithResult(CommandLine cmdLine, long timeout) throws Exception{
        DefaultExecutor executor = new DefaultExecutor();
        // 定时成功执行的退出值，未返回抛异常
        executor.setExitValue(0);

        // 设置超时时间
        ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
        executor.setWatchdog(watchdog);

        try(     // 接收正常结果流
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 // 接收异常结果流
                ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
                ){

            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream,errorStream);
            executor.setStreamHandler(streamHandler);
            executor.execute(cmdLine);

            CommandResultBo resultBo = new CommandResultBo();
            resultBo.setExecError(errorStream.toString("UTF-8"));
            resultBo.setExecResult(outputStream.toString("UTF-8"));

            return resultBo;
        }catch (Exception e){
            throw e;
        }
    }

}
