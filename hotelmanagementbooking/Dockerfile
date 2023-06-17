# Docker Build Maven Stage
FROM adoptopenjdk/openjdk11
VOLUME /tmp
ADD target/hotelbooking.jar hotelbooking.jar

ENTRYPOINT ["java","-jar","/hotelbooking.jar"]
