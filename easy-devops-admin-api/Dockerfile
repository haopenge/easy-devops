FROM  registry.cn-hangzhou.aliyuncs.com/ranmo/mvn-docker-kubectl:1.6

EXPOSE 10080
COPY soft /root
RUN mkdir -p /root/.m2/repository \
    && mv ./mvn/settings.xml /root/.m2/settings.xml
COPY ./target/*.jar $APP_PATH/app.jar
#COPY run.sh /usr/local/app/run.sh
CMD ["sh","-c","java -jar $APP_PATH/app.jar"]
