FROM amazoncorretto:17-alpine3.20
WORKDIR /

COPY ./build/libs/demo_parking-0.0.1-SNAPSHOT.jar /app/demo_parking.jar
ENTRYPOINT ["java","-jar","/app/demo_parking.jar"]