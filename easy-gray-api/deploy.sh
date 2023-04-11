#!/bin/bash
GRAY_ENV=$1
ALI_DOCKER_USERNAME=$2
ALI_DOCKER_PASSWORD=$3
BUILD_NUMBER=$4
# shellcheck disable=SC2006
echo "当前脚本执行目录: `pwd` "
echo "<<====================== 1.1 开始打包 ===================>>"
mvn clean package  -Dmaven.test.skip=true

echo "<<====================== 1.2 构建docker镜像 ===================>>"
docker build -t registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-api:"${BUILD_NUMBER}" -f Dockerfile .

echo "<<====================== 1.3 登录docker仓库===================>>"
docker login --username="${ALI_DOCKER_USERNAME}" --password="${ALI_DOCKER_PASSWORD}" registry.cn-hangzhou.aliyuncs.com

echo "<<====================== 1.4 docker镜像 ===================>>"
docker push registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-api:"${BUILD_NUMBER}"

echo "<<====================== 2.1 替换k8s.yaml中的镜像版本号 ===================>>"
sed -i "" "s/build_number/${BUILD_NUMBER}/g" deployment.yaml
sed -i "" "s/pod-env/${GRAY_ENV}/g" deployment.yaml

echo "<<====================== 2.2 发布服务 ===================>>"
kubectl apply -f deployment.yaml


