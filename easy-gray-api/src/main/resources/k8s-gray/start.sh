pod_env=$1
application_name=$2
exec_dir=$3
version=$4

echo "<<====================== 0.1 修改 deployment.yaml中的 命名空间、镜像版本号 ===================>>"
echo " pod_env = ${pod_env}"
echo " exec_dir = ${exec_dir}"
echo " application_name = ${application_name}"

echo "<<====================== 0.2 进入执行目录 ===================>>"
cd ${exec_dir} || exit

sed -i "" "s/pod_env/${pod_env}/g" deployment.yaml
sed -i "" "s/application_name/${application_name}/g" deployment.yaml

image_suffix=${application_name}:${pod_env}.${version}
sed -i "" "s/image_suffix/${image_suffix}/g" deployment.yaml


echo "<<====================== 1. 项目打包 ===================>>"
mvn clean package -Dmaven.test.skip=true

echo "<<====================== 2.1 构建镜像版本号 ===================>>"
docker build -t registry.cn-hangzhou.aliyuncs.com/ranmo/${image_suffix} -f Dockerfile .

echo "<<====================== 2.2 登录docker仓库===================>>"
docker login --username=16601114926 --password=270698050qQ registry.cn-hangzhou.aliyuncs.com

echo "<<======================2.3 push docker镜像 ===================>>"
docker push registry.cn-hangzhou.aliyuncs.com/ranmo/image_suffix
