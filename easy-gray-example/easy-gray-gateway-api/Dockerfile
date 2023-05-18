FROM registry.cn-hangzhou.aliyuncs.com/ranmo/centos-mvn-kubcel:1.0.0

EXPOSE 10080
RUN mkdir -p /usr/local/app
COPY ./target/*.jar /usr/local/app/app.jar
COPY run.sh /usr/local/app/run.sh
