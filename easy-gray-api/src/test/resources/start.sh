pod_env=$1
application_name=$2

sed -i "" "s/pod_env/${pod_env}/g" /Volumes/DATA/Users/liupenghao/work/idea/mime/easy-gray/easy-gray-api/src/test/resources/deployment.yaml
sed -i "" "s/application_name/${application_name}/g" /Volumes/DATA/Users/liupenghao/work/idea/mime/easy-gray/easy-gray-api/src/test/resources/deployment.yaml