echo "<<====================== 2. 构建docker镜像 ===================>>"
docker build -t registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-gateway-api:${BUILD_NUMBER} -f Dockerfile .

echo "<<====================== 1. 登录docker仓库===================>>"
docker login --username=$ALI_DOCKER_USERNAME --password=$ALI_DOCKER_PASSWORD registry.cn-hangzhou.aliyuncs.com

echo "<<======================push docker镜像 ===================>>"
docker push registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-gateway-api:${BUILD_NUMBER}


echo "<<====================== 3. k8s.yaml中的镜像版本号 ===================>>"
sed -i "s/build_number/${BUILD_NUMBER}/g" k8s.yaml

echo "<<====================== 4. 发布服务 ===================>>"
kubectl apply -f k8s.yaml

