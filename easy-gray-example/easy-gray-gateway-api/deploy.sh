#!/bin/bash
DEPLOY_DIR=$1
ALI_DOCKER_PWD=$2
GRAY_ENV=$3
BUILD_NUMBER=$4
if  [ "$DEPLOY_DIR" = "." ]
    then
    DEPLOY_DIR=`pwd`

else
  cd $DEPLOY_DIR
  echo "输入发布目录: ${DEPLOY_DIR} "
fi

echo "当前脚本执行目录: `pwd` "
echo "<<====================== 1.1 开始打包 ===================>>"
mvn clean package  -Dmaven.test.skip=true

echo "<<====================== 1.2 构建docker镜像 ===================>>"
docker build -t registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-gateway-api:${BUILD_NUMBER} -f Dockerfile .

echo "<<====================== 1.3 登录docker仓库===================>>"
docker login --username=16601114926 --password=${ALI_DOCKER_PWD} registry.cn-hangzhou.aliyuncs.com

#echo "<<====================== 1.4 docker镜像 ===================>>"
docker push registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-gateway-api:${BUILD_NUMBER}

echo "<<====================== 2. k8s.yaml中的镜像版本号 ===================>>"
sed -i "" "s/build_number/${BUILD_NUMBER}/g" deployment.yaml
sed -i "" "s/pod-env/${GRAY_ENV}/g" deployment.yaml
sed -i "" "s/pod-env/${GRAY_ENV}/g" service.yaml



