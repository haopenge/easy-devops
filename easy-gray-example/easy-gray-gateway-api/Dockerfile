FROM  registry.cn-hangzhou.aliyuncs.com/ranmo/mvn:1.0

EXPOSE 10080
COPY ./target/*.jar $APP_PATH/app.jar
CMD ["sh","-c","java -jar $APP_PATH/app.jar"]

