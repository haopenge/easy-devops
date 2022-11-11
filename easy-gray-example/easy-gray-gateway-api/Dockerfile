FROM registry.cn-hangzhou.aliyuncs.com/ranmo/centos7-jdk8:1.0
MAINTAINER xiaoyuxxx
USER root

EXPOSE 10080
ENV PROJECT_NAME easy-gray-gateway-api
ENV PROJECT_ROOT /usr/local/${PROJECT_NAME}
RUN mkdir -p ${PROJECT_ROOT}

COPY ./target/*.jar ${PROJECT_ROOT}/app.jar