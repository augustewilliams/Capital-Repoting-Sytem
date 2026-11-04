FROM amazoncorretto:17
COPY ./target/CapReport.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "CapReport.jar"]