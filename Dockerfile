FROM openjdk:8u181-jre-alpine
LABEL maintainer="Rafael <rafael.whatsthestory@gmail.com>"

WORKDIR /app
EXPOSE 8090

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Djava.security.egd=file:/dev/./urandom -jar healthcare-management.jar" ]

ADD build/libs/healthcare-management.jar .