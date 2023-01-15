FROM eclipse-temurin:17-jdk-jammy
RUN mkdir -p /software
ADD target/cancun-booking-api-0.0.1.jar /software/cancun-booking-api-0.0.1.jar
#COPY target/cancun-booking-api-0.0.1-SNAPSHOT.jar cancun-booking-api-0.0.1-SNAPSHOT.jar
CMD java -Dserver.port=$PORT $JAVA_OPTS -jar /software/cancun-booking-api-0.0.1.jar