##### Dockerfile #####

## Build
FROM maven:3.9.7-eclipse-temurin-17-alpine as build

WORKDIR /src

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn clean package -DskipTests=true

## Run
FROM eclipse-temurin:17.0.8.1_1-jre-ubi9-minimal
ENV JAR_FILE="housing-service-0.0.1-SNAPSHOT.jar"
ENV JAVA_OPTIONS="-Xmx2048m -Xms256m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:G1HeapRegionSize=16m -XX:InitiatingHeapOccupancyPercent=45"

# Optional environment variables

RUN unlink /etc/localtime;ln -s /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime
COPY --from=build src/target/$JAR_FILE /run/$JAR_FILE

EXPOSE $PORT

ENTRYPOINT java -jar $JAVA_OPTIONS /run/$JAR_FILE