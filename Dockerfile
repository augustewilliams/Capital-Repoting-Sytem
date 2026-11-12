FROM amazoncorretto:17
COPY ./target/CapReport-0.1.0.6.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "CapReport-0.1.0.6.jar"]