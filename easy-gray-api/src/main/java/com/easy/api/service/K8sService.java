package com.easy.api.service;

import io.kubernetes.client.ProtoClient;
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
import io.kubernetes.client.util.Yaml;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class K8sService {

    @Autowired
    private AppsV1Api appsV1Api;

    @Autowired
    private ProtoClient protoClient;

    @Autowired
    private CoreV1Api coreV1Api;

    @Autowired
    private ExtensionsV1beta1Api extensionsV1beta1Api;

    @Value("${k8s.path:/api/v1/namespaces/}")
    private String path;

    public  void createDeployment(String namespace,String yamlFilePath) throws IOException, ApiException {
        V1Deployment body = (V1Deployment) Yaml.load(new File(yamlFilePath));
        appsV1Api.createNamespacedDeployment(namespace,body,"true",null,null);

    }

    public void deleteDeployment(String namespace,String name) throws ApiException {
        // 传入deployment的名字，命名空间，就可以删除deployment以及所有的pod了
        appsV1Api.deleteNamespacedDeployment(name,namespace,Boolean.TRUE.toString(),null,null,null,null,null);
    }

    public void createNamespace(String name) throws ApiException, IOException {
        V1.Namespace namespace = V1.Namespace.newBuilder().setMetadata(Meta.ObjectMeta.newBuilder().setName(name).build()).build();
        protoClient.create(namespace, path, "v1", "Namespace");
    }

    public void deleteNamespace(String namespace) throws ApiException, IOException {
        protoClient.delete(V1.Namespace.newBuilder(), path + namespace);
    }

    public void createService(String namespace,String yamlFilePath) throws IOException, ApiException {
        V1Service body = (V1Service) Yaml.load(new File(yamlFilePath));
        coreV1Api.createNamespacedService(namespace,body,"true",null,null);
    }

    public void deleteService(String namespace,String name) throws ApiException {
        coreV1Api.deleteNamespacedService(name,namespace,"true",null,null,false,null,null);
    }


    public void createIngress(String namespace,String yamlFilePath) throws IOException, ApiException {
        ExtensionsV1beta1Ingress body = (ExtensionsV1beta1Ingress) Yaml.load(new File(yamlFilePath));
        extensionsV1beta1Api.createNamespacedIngress(namespace,body,"true",null,null);
    }

    public void deleteIngress(String namespace,String name) throws ApiException {
        extensionsV1beta1Api.deleteNamespacedIngress(name,namespace,"true",null,null,false,null,null);
    }

    public void createSecrets(String namespace,String yamlFilePath) throws IOException, ApiException {
        V1Secret body = (V1Secret) Yaml.load(new File(yamlFilePath));
        coreV1Api.createNamespacedSecret(namespace,body,"true",null,null);
    }

    public void deleteSecrets(String namespace,String name) throws ApiException {
        coreV1Api.deleteNamespacedSecret(name,namespace,"true",null,null,false,null,null);
    }
}
