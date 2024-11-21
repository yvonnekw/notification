FROM openjdk:23
VOLUME /tmp
COPY target/notification.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]