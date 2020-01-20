FROM registry.cn-hangzhou.aliyuncs.com/shalousun/alpine-oraclejre8:1.1
EXPOSE 8087
ADD enTai.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
