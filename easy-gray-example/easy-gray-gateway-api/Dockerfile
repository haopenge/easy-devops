FROM  registry.cn-hangzhou.aliyuncs.com/ranmo/mvn:1.0

EXPOSE 10080
COPY .kube /root/.kube
COPY ./target/*.jar $APP_PATH/app.jar
#COPY run.sh /usr/local/app/run.sh
CMD ["sh","-c","java -jar $APP_PATH/app.jar"]

