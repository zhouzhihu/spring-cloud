#以什么镜像为基
FROM openjdk:8-jre
VOLUME /tmp
COPY target/egrand-wfw-zuul-1.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","/app.jar"]