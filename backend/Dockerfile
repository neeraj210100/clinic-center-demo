FROM azul/zulu-openjdk:17-latest
VOLUME /tmp
COPY target/clinic-center-backend-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]