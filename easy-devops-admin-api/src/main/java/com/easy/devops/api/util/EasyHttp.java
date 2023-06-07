package com.easy.devops.api.util;

import com.alibaba.fastjson.JSON;
import com.easy.devops.api.domain.bo.FormDataFileBo;
import com.easy.core.domain.ApiResult;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

/**
 * http 请求封装
 * @author liuph
 */
@Slf4j
public class EasyHttp {

    private static final OkHttpClient client;

    static {
        client = new OkHttpClient
                .Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .build();
    }

    private EasyHttp() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * get 请求
     * @param url   请求url
     * @param headerMap 请求header
     * @param clazz     响应结果类型  not null
     * @param <T>       响应结果泛型
     * @return  响应结果
     */
    public static <T> T httpGet(String url, Map<String,String> headerMap,Class<T> clazz){
        long currentTime = System.currentTimeMillis();
        Headers.Builder headerBuilder = new Headers.Builder();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach(headerBuilder::add);
        }

        Request request = new Request.Builder().get().url(url).headers(headerBuilder.build()).build();
        T t = packagingRequest(request,clazz);

        log.info("EasyHttp.httpGet start ,url : {}, headerMap : {} , timeout : {}",url, JSON.toJSONString(headerMap),System.currentTimeMillis() - currentTime);
        return t;
    }

    /**
     * get 请求
     * @author liupenghao
     * @param url   请求url
     * @param headerMap 请求header
     * @param clazz     响应结果类型  not null
     * @param <T>       响应结果泛型
     * @return  响应结果
     */
    public static <T> List<T> httpGetArray(String url, Map<String,String> headerMap,Class<T> clazz){
        long currentTime = System.currentTimeMillis();
        Headers.Builder headerBuilder = new Headers.Builder();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach(headerBuilder::add);
        }

        Request request = new Request.Builder().get().url(url).headers(headerBuilder.build()).build();
        List<T> t = packagingArrayRequest(request,clazz);

        log.info("EasyHttp.httpGet httpGetArray ,url : {}, headerMap : {} , timeout : {}",url, JSON.toJSONString(headerMap),System.currentTimeMillis() - currentTime);
        return t;
    }


    /**
     * 有返回值 post 请求
     * @author liupenghao
     * @param url   请求url
     * @param headerMap 请求header
     * @param clazz     响应结果类型
     * @param <T>       响应结果泛型
     * @return  响应结果
     */
    public static <T> T httpPost(String url, Map<String,String> headerMap, String requestBodyStr,Class<T> clazz){
        long currentTime = System.currentTimeMillis();
        Headers.Builder headerBuilder = new Headers.Builder();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach(headerBuilder::add);
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON.toString()), requestBodyStr);
        Request request = new Request.Builder().url(url).headers(headerBuilder.build()).post(requestBody).build();

        T t = packagingRequest(request,clazz);
        log.info("EasyHttp.httpPost start ,url : {}, headerMap : {} , timeout : {}",url, JSON.toJSONString(headerMap),System.currentTimeMillis() - currentTime);
        return t;
    }

    /**
     * <pre>
     *     有返回值 post 请求，返回是对象;
     *     注：仅针对返回body格式如下使用，非此格式，参见本类其他方法；
     *     {
     *          "success": false,
     *          "msgCode": "test_db824705f93b",
     *          "message": "test_eb04f0787ead",
     *          "code": 26,
     *          "data": [{},{}]
     *      }
     * </pre>
     *
     * @author liupenghao
     * @param url   请求url
     * @param headerMap 请求header
     * @param timeout   请求超时时间
     * @param clazz     响应结果类型
     * @param <T>       响应结果泛型
     * @return  响应结果
     */
    public static <T> List<T> httpPostForApiResultArray(String url, Map<String,String> headerMap, String requestBodyStr,int timeout,Class<T> clazz){
        ApiResult.ApiObjectResult apiResult = httpPost(url, headerMap, requestBodyStr, ApiResult.ApiObjectResult.class);
        if(Objects.isNull(apiResult) || !apiResult.isSuccess()){
            log.info("EasyHttp.httpPostForApiResultArray request fail,url : {}, headerMap : {} , timeout : {}",url,JSON.toJSONString(headerMap),timeout);
            return Collections.emptyList();
        }
        return JSON.parseArray(apiResult.getData().toString(),clazz);
    }

    /**
     * <pre>
     *     有返回值 post 请求，返回是对象;
     *     注：仅针对返回body格式如下使用，非此格式，参见本类其他方法；
     *     {
     *          "success": false,
     *          "msgCode": "test_db824705f93b",
     *          "message": "test_eb04f0787ead",
     *          "code": 26,
     *          "data": {}
     *      }
     * </pre>
     *
     * @author liupenghao
     * @param url   请求url
     * @param headerMap 请求header
     * @param clazz     响应结果类型
     * @param <T>       响应结果泛型
     * @return  响应结果
     */
    public static <T> T httpPostForApiResultObject(String url, Map<String,String> headerMap, String requestBodyStr,Class<T> clazz){
        ApiResult.ApiObjectResult apiResult = httpPost(url, headerMap, requestBodyStr, ApiResult.ApiObjectResult.class);
        if(Objects.isNull(apiResult) || !apiResult.isSuccess()){
            log.info("EasyHttp.httpPostForApiResultObject request fail,url : {}, headerMap : {} ",url,JSON.toJSONString(headerMap));
            return null;
        }
        return JSON.parseObject(apiResult.getData().toString(),clazz);
    }

    /**
     * 有返回值 post 请求(无返回值)
     * @author liupenghao
     * @param url   请求url
     * @param headerMap 请求header
     */
    public static boolean httpPostNoReturn(String url, Map<String,String> headerMap, String requestBodyStr){
        long currentTime = System.currentTimeMillis();

        Headers.Builder headerBuilder = new Headers.Builder();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach(headerBuilder::add);
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON.toString()), requestBodyStr);
        Request request = new Request
                .Builder()
                .url(url)
                .headers(headerBuilder.build())
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute();){
            ResponseBody body = response.body();
            int code = response.code();
            log.info("EasyHttp.httpPostNoReturn url : {},request : {},timeout: {}",url,requestBodyStr,System.currentTimeMillis() - currentTime);
            if(body == null || !Objects.equals(code,200)){
                log.error("EasyHttp.httpPostNoReturn exception with response  ,url : {}, headerMap : {} ,code : {}",url,JSON.toJSONString(headerMap),code);
                return false;
            }
            return true;
        } catch (IOException e) {
            log.error("EasyHttp.httpPostNoReturn exception ,url : {}, headerMap : {} ,",url,JSON.toJSONString(headerMap),e);
        }
        return false;
    }

    /**
     * 下载
     * @param url 请求url
     * @param filePath 保存路径
     * @param timeout 超时间时间
     * @param override 文件存在,是否复写
     * @return 下载状态
     */
    public static boolean download(String url,Map<String,String> headerMap, String filePath,int timeout,boolean override){
        long currentTime = System.currentTimeMillis();
        File saveFile = new File(filePath);
        if(!override && saveFile.exists()){
            log.info("EasyHttp.download file exists ,url : {}, filePath : {}",url,filePath);
            return true;
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach(headerBuilder::add);
        }

        Request request = new Request
                .Builder()
                .url(url)
                .headers(headerBuilder.build())
                .get()
                .build();

        try (Response response = client.newCall(request).execute()){
            log.info("EasyHttp.httpPostNoReturn url : {},timeout: {}",url,System.currentTimeMillis() - currentTime);

            ResponseBody body = response.body();
            int code = response.code();
            if(body == null || !Objects.equals(code,200)){
                log.error("EasyHttp.download body == null || code != 200,url : {}, filePath : {} , timeout : {},,override: {}",url,filePath,timeout,override);
                return false;
            }
            try(InputStream is = body.byteStream();
                OutputStream os = Files.newOutputStream(Paths.get(filePath));
            ){
                IOUtils.copy(is,os);
            }
            return true;
        } catch (IOException e) {
            log.error("EasyHttp.download exception ,url : {}, filePath : {} , timeout : {},override: {}",url,filePath,timeout,override);
        }
        return false;
    }


    /**
     * 有返回值 form-data post 请求
     * @author liupenghao
     * @param url   请求url
     * @param headerMap 请求header
     * @param clazz     响应结果类型
     * @param <T>       响应结果泛型
     * @return  响应结果
     */
    public static <T> T httpPostFormData(String url, Map<String,String> headerMap, Map<String, Object> formDataMap, List<FormDataFileBo> fileList, Class<T> clazz){
        long currentTime = System.currentTimeMillis();

        Headers.Builder headerBuilder = new Headers.Builder();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach(headerBuilder::add);
        }

        MultipartBody.Builder multiBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (Map.Entry<String, Object> entry : formDataMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            multiBuilder.addFormDataPart(key,value.toString());
        }

        if(!CollectionUtils.isEmpty(fileList)){
            for (FormDataFileBo loopFileBo : fileList) {
                RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_OCTET_STREAM.toString()), new File(loopFileBo.getFilePath()));
                multiBuilder.addFormDataPart(loopFileBo.getName(), loopFileBo.getFileName(),requestBody);
            }
        }

        Request request = new Request.Builder().url(url).headers(headerBuilder.build())
                .post(multiBuilder.build()).build();

        T t = packagingRequest(request,clazz);
        log.info("EasyHttp.httpPostFormData start ,url : {}, headerMap : {} , timeout : {}",url, JSON.toJSONString(headerMap),System.currentTimeMillis() - currentTime);
        return t;
    }

    /**
     * 有返回值 httpPostFormUrlencoded post 请求
     * @author liupenghao
     * @param url   请求url
     * @param headerMap 请求header
     * @param clazz     响应结果类型
     * @param <T>       响应结果泛型
     * @return  响应结果
     */
    public static <T> T httpPostFormUrlencoded(String url, Map<String,String> headerMap, Map<String, String> formDataMap, Class<T> clazz){
        long currentTime = System.currentTimeMillis();

        Headers.Builder headerBuilder = new Headers.Builder();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach(headerBuilder::add);
        }

        FormBody.Builder bodyBuilder = new FormBody.Builder();
        formDataMap.forEach(bodyBuilder::add);
        Request request = new Request.Builder().url(url).headers(headerBuilder.build())
                .post(bodyBuilder.build()).build();

        T t = packagingRequest(request,clazz);
        log.info("EasyHttp.httpPostFormUrlencoded start ,url : {}, headerMap : {} , timeout : {}",url, JSON.toJSONString(headerMap),System.currentTimeMillis() - currentTime);
        return t;
    }

    /**
     * 封装请求返回数据
     * @param request   请求体
     * @param clazz     返回类型
     * @return          t
     * @param <T>       返回类型
     */
    private static <T> T packagingRequest(Request request, Class<T> clazz) {
        try {
            Response response = client.newCall(request).execute();
            int code = response.code();
            ResponseBody body = response.body();
            String bodyStr = Objects.nonNull(body)? body.string() : StringUtils.EMPTY;
            if(!Objects.equals(code,200)){
                log.error("EasyHttp.packagingRequest code exception ,code : {},body : {}", code,bodyStr);
                return null;
            }

            if(StringUtils.isBlank(bodyStr)){
                return null;
            }

            return JSON.parseObject(bodyStr, clazz);
        } catch (IOException e) {
            log.error("EasyHttp.packagingRequest error ,",e);
            return null;
        }
    }

    /**
     * 封装 返回 数组数据
     * @param request   请求体
     * @param clazz     返回类型
     * @return          t
     * @param <T>       返回类型
     */
    private static <T> List<T> packagingArrayRequest(Request request, Class<T> clazz) {
        try {
            Response response = client.newCall(request).execute();
            int code = response.code();
            ResponseBody body = response.body();
            String bodyStr = Objects.nonNull(body)? body.string() : StringUtils.EMPTY;
            if(!Objects.equals(code,200)){
                log.error("EasyHttp.packagingRequest code exception ,code : {},body : {}", code,bodyStr);
                return Collections.emptyList();
            }

            if(StringUtils.isBlank(bodyStr)){
                return Collections.emptyList();
            }

            return JSON.parseArray(bodyStr, clazz);
        } catch (IOException e) {
            log.error("EasyHttp.packagingRequest error ,",e);
            return Collections.emptyList();
        }
    }


    /**
     * <pre>
     *     有返回值 form-data post 请求，返回是对象;
     *     注：仅针对返回body格式如下使用，非此格式，参见本类其他方法；
     *     {
     *          "success": false,
     *          "msgCode": "test_db824705f93b",
     *          "message": "test_eb04f0787ead",
     *          "code": 26,
     *          "data": {}
     *      }
     * </pre>
     *
     * @author liupenghao
     * @param url   请求url
     * @param headerMap 请求header
     * @param clazz     响应结果类型
     * @param <T>       响应结果泛型
     * @return  响应结果
     */
    public static <T> T httpPostFormDataForObject(String url, Map<String,String> headerMap, Map<String, Object> formDataMap, List<FormDataFileBo> fileList, Class<T> clazz){
        ApiResult.ApiObjectResult apiResult = httpPostFormData(url, headerMap, formDataMap, fileList, ApiResult.ApiObjectResult.class);
        if(Objects.isNull(apiResult) || !apiResult.isSuccess()){
            log.info("EasyHttp.httpPostFormDataForObject request fail,url : {}, headerMap : {} ",url,JSON.toJSONString(headerMap));
            return null;
        }
        return JSON.parseObject(apiResult.getData().toString(),clazz);
    }

    /**
     * <pre>
     *     有返回值 form-data post 请求，返回是对象;
     *     注：仅针对返回body格式如下使用，非此格式，参见本类其他方法；
     *     {
     *          "success": false,
     *          "msgCode": "test_db824705f93b",
     *          "message": "test_eb04f0787ead",
     *          "code": 26,
     *          "data": [{},{}]
     *      }
     * </pre>
     *
     * @author liupenghao
     * @param url   请求url
     * @param headerMap 请求header
     * @param timeout   请求超时时间
     * @param clazz     响应结果类型
     * @param <T>       响应结果泛型
     * @return  响应结果
     */
    public static <T> List<T>  httpPostFormDataForArray(String url, Map<String,String> headerMap, Map<String, Object> formDataMap, List<FormDataFileBo> fileList, int timeout, Class<T> clazz){
        ApiResult.ApiObjectResult apiResult = httpPostFormData(url, headerMap, formDataMap, fileList, ApiResult.ApiObjectResult.class);
        if(Objects.isNull(apiResult) || !apiResult.isSuccess()){
            log.info("EasyHttp.httpPostFormDataForArray request fail,url : {}, headerMap : {} , timeout : {}",url,JSON.toJSONString(headerMap),timeout);
            return Collections.emptyList();
        }
        return JSON.parseArray(apiResult.getData().toString(),clazz);
    }

}
