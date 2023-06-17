# Docker Build Maven Stage
FROM adoptopenjdk/openjdk11
VOLUME /tmp
ADD target/hotelreports.jar hotelreports.jar

ENTRYPOINT ["java","-jar","/hotelreports.jar"]
