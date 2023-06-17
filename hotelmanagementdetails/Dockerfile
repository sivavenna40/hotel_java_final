# Docker Build Maven Stage
FROM adoptopenjdk/openjdk11
VOLUME /tmp
ADD target/hoteldetails.jar hoteldetails.jar

ENTRYPOINT ["java","-jar","/hoteldetails.jar"]
