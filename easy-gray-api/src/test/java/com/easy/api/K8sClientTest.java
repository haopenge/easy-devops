package com.easy.api;

import io.kubernetes.client.ProtoClient;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Status;
import io.kubernetes.client.proto.Meta;
import io.kubernetes.client.proto.V1;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Yaml;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;

import java.io.File;
import java.io.IOException;

public class K8sClientTest {

    public static void main(String[] args) throws IOException, ApiException {
        // 传入deployment的名字，命名空间，就可以删除deployment以及所有的pod了
        AppsV1Api api = getCoreV1Api();
        V1Status v1Status = api.deleteNamespacedDeployment("shiqi-deploy","qa",null,null,null,null,null,null);
        System.out.println(v1Status.getCode()+"删除完毕");
    }

    private static void deleteNamespace() throws ApiException, IOException {
        ProtoClient protoClient = getProtoClient();
        protoClient.delete(V1.Namespace.newBuilder(),"/api/v1/namespaces/"+ "easy-12138");
    }

    private static void createDeployment() throws IOException {
        AppsV1Api api = getCoreV1Api();

        // 2、根据资源对象文件创建deployment，创建多个副本，并指定自定义调度器
        String filePath = K8sClientTest.class.getResource("/k8s/deployment.yaml").getPath();
        V1Deployment body = (V1Deployment) Yaml.load(new File(filePath));
        try {
            V1Deployment result = api.createNamespacedDeployment("easy-12138",body,null,null,null);
            System.out.println("success,工作负载创建成功");
        }catch (ApiException e){
            if (e.getCode() == 409) {
                System.out.println("error 工作负载创建已重复！");
            } else if (e.getCode() == 200) {
                System.out.println("success 工作负载创建成功！");
            } else if (e.getCode() == 201) {
                System.out.println("error 工作负载创建已重复！");
            } else if (e.getCode() == 401) {
                System.out.println("error 无权限操作！");
            } else {
                System.out.println("error 工作负载创建失败！");
            }
            System.out.println("Exception when calling AppsV1Api#createNamespacedDeployment");
            System.out.println("Status code: {}"+ e.getCode());
            System.out.println("Reason: {}"+ e.getResponseBody());
            System.out.println("Response headers: {}"+ e.getResponseHeaders());
        }
    }

    private static ProtoClient.ObjectOrStatus<V1.Namespace> createNamespace() throws ApiException, IOException {
        ProtoClient protoClient = getProtoClient();
        V1.Namespace namespace = V1.Namespace.newBuilder().setMetadata(Meta.ObjectMeta.newBuilder().setName("easy-12138").build()).build();
        return protoClient.create(namespace, "/api/v1/namespaces", "v1", "Namespace");
    }

    /**
     * 获取k8s 操作api
     */
    private static ApiClient getApiClient() {
        AccessTokenAuthentication authentication = new AccessTokenAuthentication("token");
        return new ClientBuilder()
                .setBasePath("https://192.168.78.128:6443")
                .setAuthentication(authentication)
                .setVerifyingSsl(false)
                .build();
    }

    private static AppsV1Api getCoreV1Api() {
        return new AppsV1Api(getApiClient());
    }

    private static ProtoClient getProtoClient() {
        return new ProtoClient(getApiClient());
    }
}
