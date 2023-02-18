#!/bin/bash
DEPLOY_DIR=$0
ALI_DOCKER_PWD=$1
if test -z "$DEPLOY_DIR"
    then
    echo "未输入发布目录"
    return 0
else
  cd $DEPLOY_DIR
  echo "输入发布目录: ${DEPLOY_DIR} "
fi

echo "<<====================== 1.1 开始打包 ===================>>"
mvn clean package  -Dmaven.test.skip=true

echo "<<====================== 1.2 构建docker镜像 ===================>>"
docker build -t registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-gateway-api:13 -f Dockerfile .

echo "<<====================== 1.3 登录docker仓库===================>>"
docker login --username=16601114926 --password=${ALI_DOCKER_PWD} registry.cn-hangzhou.aliyuncs.com

echo "<<====================== 1.4 docker镜像 ===================>>"
docker push registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-gateway-api:13


echo "<<====================== 2. k8s.yaml中的镜像版本号 ===================>>"
#sed -i "s/build_number/${BUILD_NUMBER}/g" k8s.yaml

echo "<<====================== 3. 发布服务 ===================>>"
#kubectl apply -f k8s.yaml

