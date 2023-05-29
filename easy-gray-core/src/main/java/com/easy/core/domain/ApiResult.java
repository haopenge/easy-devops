package com.easy.core.domain;

import com.easy.core.enumx.HttpStatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 接口响应结构
 */
@Getter
@Setter
@SuppressWarnings("all")
public class ApiResult<T> {

    /**
     * 返回状态, true/false
     */
    private boolean success;

    /**
     * 翻译后的消息
     */
    private String message = "";

    /**
     * 业务Code，可作为返回标识
     */
    private String code;

    /**
     * 响应数据
     */
    private T data;


    /**
     * 请求通过正常返回值
     *
     * @return ApiResult
     */
    public static ApiResult<Void> ok() {
        ApiVoidResult result = new ApiVoidResult();
        result.setSuccess(true);
        result.setCode(String.valueOf(HttpStatusEnum.OK.getStatus()));
        return result;
    }

    /**
     * 请求通过正常返回值
     *
     * @param controller 当前controller
     * @return ApiResult
     */
    public static <T> ApiResult<T> ok(T t) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(true);
        result.setCode(String.valueOf(HttpStatusEnum.OK.getStatus()));
        result.setData(t);
        return result;
    }


    /**
     * 失败返回
     *
     * @return ApiResult
     */
    public static ApiResult<Void> error() {
        ApiVoidResult result = new ApiVoidResult();
        result.setSuccess(false);
        return result;
    }

    public static ApiResult<Object> error(String code, String message) {
        ApiObjectResult result = new ApiObjectResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * obj-返回通用对象
     */
    public static class ApiObjectResult extends ApiResult<Object> {

    }

    /**
     * 无返回值-通用对象
     */
    public static class ApiVoidResult extends ApiResult<Void> {

    }
}
