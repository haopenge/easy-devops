package com.youlai.system.common.enums;

import com.easy.core.exception.ExceptionEnumInterface;
import lombok.Getter;

/**
 * 请求失败状态信息定义
 *
 * @author liuph
 */
@Getter
public enum AdminApiFailureEnum implements ExceptionEnumInterface {

    // ****************************************** 服务内部错误 100_000 ~ 100_099 *************************************************************
    AUTH_TOKEN_EXPIRED("100_000", "token失效,请重新登录"),

    SYSTEM_INNER_ERROR("100_001", "服务内部错误: "),

    FEIGN_INVOKE_ERROR("100_002", "远程调用失败"),

    FILE_COPY_EXCEPTION("100_003", "文件复制异常"),

    CMD_RUN_EXCEPTION("100_004", "CMD命令执行异常"),

    // ****************************************** 认证错误 100100 ~ 100199 *************************************************************
    AUTH_FAIL("100_100", "access token was invalid"),

    SSO_TOKEN_FEIGN_EXCEPTION("100_101", "sso token 回调接口异常"),

    SSO_REDIRECT_EXCEPTION("100_102", "sso 重定向异常"),

    // 参数问题 100200 ~ 100299
    PARAM_ERROR("100_103", "参数异常"),


    // ****************************************** git 问题 100200 ~ 100299 *************************************************************
    GIT_FETCH_EXCEPTION("100_201", "获取git仓库信息异常"),


    // ****************************************** 业务问题-灰度数据 100300 ~ 100399 *************************************************************
    GRAY_ENV_NOT_EXIST("100_301", "此灰度环境不存在，或已删除"),

    GRAY_ENV_PROJECT_NOT_EXIST("100_301", "此项目还未添加到当前灰度环境或已删除"),

    GRAY_ENV_PROJECT_EXIST("100_302", "此项目已存在，请勿重复添加"),

    // ****************************************** 业务问题-灰度发布 100400 ~ 100499 *************************************************************

    K8S_INIT_ERROR("100_400", "k8s init error"),


    K8S_DEPLOY_DEPLOY_ERROR("100_401", "k8s deploy error"),

    K8S_NAMESPACE_CREATE_ERROR("100_403", "k8s create error"),

    // ****************************************** 凭证问题 100500 ~ 100599 *************************************************************

    CERTIFICATE_NAME_EXISTS("100_500", "凭证名称重复"),

    CERTIFICATE_SSH_PRIVATE_KEY_SAVE_ERROR("100_501", "ssh私钥凭证保存失败"),

    CERTIFICATE_NOT_EXISTS("100_502", "凭证不存在或已删除"),

    CERTIFICATE_NEED_CONFIG_BY_YOURSELF("100_503","windows或者mac环境请手动配置"),

    CERTIFICATE_K8S_CONFIG_SAVE_ERROR("100_504", "k8s配置保存失败"),


    // ****************************************** 仓库问题 100600 ~ 100699 *************************************************************

    REPOSITORY_NAME_EXISTS("100_600", "仓库名称重复"),

    REPOSITORY_SSH_PRIVATE_KEY_SAVE_ERROR("100_601", "仓库保存失败"),

    REPOSITORY_NOT_EXISTS("100_602", "仓库不存在或已删除"),


    // ****************************************** 环境问题 100700 ~ 100799 *************************************************************
    ENV_NOT_EXIST("100_701", "此灰度环境不存在，或已删除"),

    ENV_PROJECT_NOT_EXIST("100_701", "此项目还未添加到当前环境或已删除"),

    ENV_PROJECT_EXIST("100_702", "此项目已存在，请勿重复添加"),

    // ****************************************** 模板问题 100800 ~ 100899 *************************************************************
    TEMPLATE_NOT_EXIST("100_801", "此模板不存在，或已删除"),

    TEMPLATE_NAME_EXIST("100_802", "模板名称重复"),

    TEMPLATE_CONTENT_FILE_SAVE_ERROR("100_803","模板内容文件保存失败")

    ;
    private String code;

    private String message;

    AdminApiFailureEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
