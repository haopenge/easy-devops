FROM  registry.cn-hangzhou.aliyuncs.com/ranmo/mvn-docker-kubectl:1.1

EXPOSE 10080
COPY .kube /root/.kube
COPY ./target/*.jar $APP_PATH/app.jar
#COPY run.sh /usr/local/app/run.sh
CMD ["sh","-c","java -jar $APP_PATH/app.jar"]

