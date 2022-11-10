package com.easy.api.domain.enumx;

import lombok.Getter;

/**
 * 请求失败状态信息定义
 * @author liuph
 */
@Getter
public enum FailureEnum {

    // ****************************************** 服务内部错误 100_000 ~ 100_099 *************************************************************
    AUTH_TOKEN_EXPIRED(100_000,"token失效,请重新登录"),

    SYSTEM_INNER_ERROR(100_001,"服务内部错误: "),

    FEIGN_INVOKE_ERROR(100_002,"远程调用失败"),

    FILE_COPY_EXCEPTION(100_003,"文件复制异常"),

    CMD_RUN_EXCEPTION(100_004,"CMD命令执行异常"),

    // ****************************************** 认证错误 100100 ~ 100199 *************************************************************
    AUTH_FAIL(100100,"access token was invalid"),

    SSO_TOKEN_FEIGN_EXCEPTION(100101,"sso token 回调接口异常"),

    SSO_REDIRECT_EXCEPTION(100102,"sso 重定向异常"),

    // 参数问题 100200 ~ 100299
    PARAM_ERROR(100103,"param error"),


    // ****************************************** git 问题 100200 ~ 100299 *************************************************************
    GIT_FETCH_EXCEPTION(100201,"获取git仓库信息异常"),



    // ****************************************** 业务问题-灰度数据 100300 ~ 100399 *************************************************************
    GRAY_ENV_NOT_EXIST(100301,"此灰度环境不存在，或已删除"),

    GRAY_ENV_PROJECT_NOT_EXIST(100301,"此项目还未添加到当前灰度环境 或 已删除"),


    // ****************************************** 业务问题-灰度发布 100400 ~ 100499 *************************************************************
    K8S_DEPLOY_deployment(100400,"k8s 发布服务异常")
    ;
    private int code;

    private String message;

    FailureEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
