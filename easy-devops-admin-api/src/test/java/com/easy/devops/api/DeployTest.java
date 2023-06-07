package com.easy.devops.api;

import com.easy.core.util.CmdUtil;
import io.kubernetes.client.openapi.ApiException;

import java.io.IOException;

/**
 * @author liuph
 */
public class DeployTest {

    public static void main(String[] args) throws IOException, ApiException {
       //create();
       delete();
    }

    private static void create() throws IOException, ApiException {
       // String url = "https://gitee.com/xiaoyuxxx/easy-gray.git";
       // String branch = "feature/k8s-client-deploy";
       // String downloadDir = "/Users/liupenghao/Desktop/test/easy-gray";
       // GitTest.download(url,branch,downloadDir);


        String subProjectDir = "/Volumes/DATA/Users/liupenghao/work/idea/mime/easy-gray/easy-gray-example/easy-gray-gateway-api";
        CmdUtil.exec("sh " + subProjectDir + "/deploy.sh");
        // K8sClientTest.createNamespace("qa");

        K8sClientTest.createDeployment();
        K8sClientTest.createService();
    }

    public static void delete() throws ApiException, IOException {
        K8sClientTest.deleteDeployment("qa","easy-gray-gateway-api");
        K8sClientTest.deleteService("qa","easy-gray-gateway-api");
    }
}
