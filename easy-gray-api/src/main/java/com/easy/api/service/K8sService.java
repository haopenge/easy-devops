package com.easy.api.service;

import com.google.protobuf.Message;
import io.kubernetes.client.ProtoClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Status;
import io.kubernetes.client.proto.Meta;
import io.kubernetes.client.proto.V1;
import io.kubernetes.client.util.Yaml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class K8sService {

    @Autowired
    private AppsV1Api appsV1Api;

    @Autowired
    private ProtoClient protoClient;

    @Value("${k8s.path:/api/v1/namespaces/}")
    private String path;

    public  void createDeployment(String namespace,String yamlFilePath) throws IOException, ApiException {
        // 2、根据资源对象文件创建deployment，创建多个副本，并指定自定义调度器
        V1Deployment body = (V1Deployment) Yaml.load(new File(yamlFilePath));
        V1Deployment result = appsV1Api.createNamespacedDeployment(namespace,body,"true",null,null);
    }

    public void deleteDeployment(String name,String namespace) throws ApiException {
        // 传入deployment的名字，命名空间，就可以删除deployment以及所有的pod了
        V1Status v1Status = appsV1Api.deleteNamespacedDeployment(name,namespace,Boolean.TRUE.toString(),null,null,null,null,null);
    }

    public void createNamespace(String name) throws ApiException, IOException {
        V1.Namespace namespace = V1.Namespace.newBuilder().setMetadata(Meta.ObjectMeta.newBuilder().setName(name).build()).build();
        ProtoClient.ObjectOrStatus<V1.Namespace> namespaceObjectOrStatus = protoClient.create(namespace, path, "v1", "Namespace");
    }

    public void deleteNamespace(String namespace) throws ApiException, IOException {
        ProtoClient.ObjectOrStatus<Message> objectOrStatus = protoClient.delete(V1.Namespace.newBuilder(), path + namespace);
    }

    public void createIngress(String namespace) throws ApiException, IOException {
    }

    public void deleteIngress(String namespace) throws ApiException, IOException {
    }
}
