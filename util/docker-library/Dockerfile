FROM khipu/openjdk17-alpine
WORKDIR /service
COPY target/docker-library-1.0.jar /docker-library.jar
CMD ["java", "-jar", "/docker-library.jar"]