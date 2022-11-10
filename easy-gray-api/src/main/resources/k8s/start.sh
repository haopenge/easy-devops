pod_env=$1
application_name=$2
exec_dir=$3
version=$4

echo "<<====================== 0. 进入执行目录 ===================>>"
cd ${exec_dir} || exit

echo "<<====================== 1. 项目打包 ===================>>"
mvn clean package -Dmaven.test.skip=true

echo "<<====================== 2. 构建镜像版本号 ===================>>"
image_suffix=${application_name}:${pod_env}.${version}
docker build -t registry.cn-hangzhou.aliyuncs.com/ranmo/${image_suffix} -f Dockerfile .

echo "<<====================== 3. 修改 k8s.yaml中的 命名空间、镜像版本号 ===================>>"
sed -i "" "s/namespace/${pod_env}/g" deployment.yaml
sed -i "" "s/application_name/${application_name}/g" deployment.yaml
sed -i "" "s/image_suffix/${image_suffix}/g" deployment.yaml