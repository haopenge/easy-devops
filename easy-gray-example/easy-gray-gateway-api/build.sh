#!/bin/bash
ALI_DOCKER_USER=$1
ALI_DOCKER_PWD=$2
GRAY_ENV=$3
BUILD_NUMBER=$4

SCRIPT=$(readlink -f "$0")
# bin dir
DEPLOY_DIR=$(dirname "$SCRIPT")
# deploy dir
# shellcheck disable=SC2164
cd "${DEPLOY_DIR}"

echo "发布目录: ${DEPLOY_DIR} "
echo "<<====================== 1.1 开始打包 ===================>>"
mvn clean package  -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -s /root/.m2/settings.xml

echo "<<====================== 1.2 登录docker仓库===================>>"
docker login --username=${ALI_DOCKER_USER} --password=${ALI_DOCKER_PWD} registry.cn-hangzhou.aliyuncs.com

echo "<<====================== 1.3 构建docker镜像 ===================>>"
docker build -t registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-gateway-api:${BUILD_NUMBER} -f Dockerfile .

echo "<<====================== 1.4 docker镜像 ===================>>"
docker push registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-gateway-api:${BUILD_NUMBER}

echo "<<====================== 2. k8s.yaml中的镜像版本号 ===================>>"
sed -i "s/build_number/${BUILD_NUMBER}/g" deployment.yaml
sed -i "s/pod-env/${GRAY_ENV}/g" deployment.yaml


