
cd /Volumes/DATA/Users/liupenghao/work/idea/mime/easy-gray/easy-gray-example/easy-gray-gateway-api

mvn clean package  -Dmaven.test.skip=true

echo "<<====================== 1.1 构建docker镜像 ===================>>"
docker build -t registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-gateway-api:12 -f Dockerfile .

echo "<<====================== 1.2 登录docker仓库===================>>"
docker login --username=16601114926 --password=270698050qQ registry.cn-hangzhou.aliyuncs.com

echo "<<====================== 1.3 docker镜像 ===================>>"
docker push registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-gateway-api:12


echo "<<====================== 2. k8s.yaml中的镜像版本号 ===================>>"
#sed -i "s/build_number/${BUILD_NUMBER}/g" k8s.yaml

echo "<<====================== 3. 发布服务 ===================>>"
#kubectl apply -f k8s.yaml

