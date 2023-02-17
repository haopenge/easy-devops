package com.easy.api;

import io.kubernetes.client.ProtoClient;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.openapi.models.ExtensionsV1beta1Ingress;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Secret;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.proto.Meta;
import io.kubernetes.client.proto.V1;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.Yaml;
import io.kubernetes.client.util.credentials.KubeconfigAuthentication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class K8sClientTest {

    static String namespace = "qa";

    public static void main(String[] args) throws IOException, ApiException
    {

        createNamespace("qa");
        // 传入deployment的名字，命名空间，就可以删除deployment以及所有的pod了
        //AppsV1Api api = getCoreV1Api();
        //V1Status v1Status = api.deleteNamespacedDeployment("shiqi-deploy","qa",null,null,null,null,null,null);
        //System.out.println(v1Status.getCode()+"删除完毕");

        //createSecrets();
        deleteNamespace();

        //deleteService("easy-12138","easy-gray-gateway-api");
        //deleteIngress("easy-12138","easy-gray-gateway-api");
        //deleteDeployment("easy-12138","easy-gray-gateway-api");

      //  createDeployment();
      //  createService();
      //  createIngress();

    }

    public static void deleteDeployment(String namespace,String name) throws ApiException {
        // 传入deployment的名字，命名空间，就可以删除deployment以及所有的pod了
        AppsV1Api appsV1Api = new AppsV1Api(getApiClient());
        appsV1Api.deleteNamespacedDeployment(name,namespace,Boolean.TRUE.toString(),null,null,null,null,null);
    }

    public static void deleteNamespace() throws ApiException, IOException {
        ProtoClient protoClient = getProtoClient();
        protoClient.delete(V1.Namespace.newBuilder(),"/api/v1/namespaces/"+ "easy-12138");
    }

    public static void deleteService(String namespace,String name) throws ApiException, IOException {
        CoreV1Api coreV1Api = new CoreV1Api(getApiClient());
        coreV1Api.deleteNamespacedService(name,namespace,"true",null,null,false,null,null);
    }


    public static void deleteIngress(String namespace,String name) throws ApiException, IOException {
        ExtensionsV1beta1Api extensionsV1beta1Api = new ExtensionsV1beta1Api(getApiClient());

        extensionsV1beta1Api.deleteNamespacedIngress(name,namespace,"true",null,null,false,null,null);
    }

    public static void createService() throws IOException, ApiException {
        CoreV1Api coreV1Api = new CoreV1Api(getApiClient());
        String filePath = "/Volumes/DATA/Users/liupenghao/work/idea/mime/easy-gray/easy-gray-example/easy-gray-gateway-api" + "/service.yaml";

        V1Service body = (V1Service) Yaml.load(new File(filePath));
        coreV1Api.createNamespacedService(namespace,body,"true",null,null);
    }

    private static void createIngress() throws IOException, ApiException {
        String filePath = K8sClientTest.class.getResource("/k8s/ingress.yaml").getPath();
        ExtensionsV1beta1Ingress body = (ExtensionsV1beta1Ingress) Yaml.load(new File(filePath));

        ExtensionsV1beta1Api extensionsV1beta1Api = new ExtensionsV1beta1Api(getApiClient());

        extensionsV1beta1Api.createNamespacedIngress(namespace,body,"true",null,null);
    }

    private static void createSecrets() throws IOException, ApiException {
        String filePath = K8sClientTest.class.getResource("/k8s/ali-docker-auth.yaml").getPath();
        V1Secret body = (V1Secret) Yaml.load(new File(filePath));

        CoreV1Api coreV1Api = new CoreV1Api(getApiClient());


        coreV1Api.createNamespacedSecret(namespace,body,"true",null,null);
    }


    public static void createDeployment() throws IOException {
        AppsV1Api api = getAppsV1Api();

        // 2、根据资源对象文件创建deployment，创建多个副本，并指定自定义调度器
        String filePath = "/Volumes/DATA/Users/liupenghao/work/idea/mime/easy-gray/easy-gray-example/easy-gray-gateway-api" + "/deployment.yaml";
        V1Deployment body = (V1Deployment) Yaml.load(new File(filePath));
        try {
            V1Deployment result = api.createNamespacedDeployment(namespace,body,null,null,null);
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

    public static ProtoClient.ObjectOrStatus<V1.Namespace> createNamespace(String namespace) throws ApiException, IOException {
        ProtoClient protoClient = getProtoClient();
        V1.Namespace namespaceObj = V1.Namespace.newBuilder().setMetadata(Meta.ObjectMeta.newBuilder().setName(namespace).build()).build();
        return protoClient.create(namespaceObj, "/api/v1/namespaces", "v1", "Namespace");
    }

    /**
     * 获取k8s 操作api
     */
    private static ApiClient getApiClient() {
        KubeconfigAuthentication authentication;
        String path = K8sClientTest.class.getResource("/kube.yaml").getPath();
        try (FileReader fr = new FileReader(path);){
            KubeConfig kubeConfig = KubeConfig.loadKubeConfig(fr);
            authentication = new KubeconfigAuthentication(kubeConfig);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ClientBuilder()
                .setBasePath("https://lb.kubesphere.local:6443")
                .setAuthentication(authentication)
                .setVerifyingSsl(false)
                .build();
    }

    private static AppsV1Api getAppsV1Api() {
        return new AppsV1Api(getApiClient());
    }

    private static ProtoClient getProtoClient() {
        return new ProtoClient(getApiClient());
    }


   static String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyLXRva2VuLWd2Z2NjIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiI1ZTI2M2YzZC01ZGU5LTExZWQtYjZjNi0wMDBjMjljYjZlOWYiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZXJuZXRlcy1kYXNoYm9hcmQ6YWRtaW4tdXNlciJ9.putawCLnK4oevkmgsiYzqMcBaFUH5GprLu-MAllIRiH_FuVXWM_pTmo7zLNmBUjFHuGZjsHhWEJDLBS5CV3MmvvxGfXunTpqSLmlSov_k3L5bPHLd285AhrKgR_xbcZITNx2jZgbLZdb-3SDEGWXHSazDFJzO7K5kvFwmeUTQachpp_EreCZlLP8PMINcdIVtKm_BU6rbHUGFjFnwyOdMQSHOTqnP6NSVAKTsfvkbeXBo7G9FP4oRa2vcWpHssk75VTJuIj57MTGTYkPApzwb32bk3mqNYJV0NTGGVuOz3UNFJdoadOPGVeBgrjp-MPIDjec8v7HULyJJtL6gJICTQ";
}
