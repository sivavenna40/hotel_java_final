# Docker Build Maven Stage
FROM adoptopenjdk/openjdk11
VOLUME /tmp
ADD target/hotelroomdetails.jar hotelroomdetails.jar

ENTRYPOINT ["java","-jar","/hotelroomdetails.jar"]
