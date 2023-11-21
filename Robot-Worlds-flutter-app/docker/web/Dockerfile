FROM maven:3.8.2-jdk-11-slim
RUN apt-get update
ADD /ServerSideApplication/target/ServerSideApplication-1.0-SNAPSHOT.jar /src/web-api-server.jar
ADD ServerSideApplication/src/main/java/za/co/wethinkcode/robotworlds/Server/config.properties /src/ServerSideApplication/src/main/java/za/co/wethinkcode/robotworlds/Server/config.properties
ADD RobotWorldsDatabase.sqlite /src/RobotWorldsDatabase.sqlite

EXPOSE 5000

WORKDIR /src
CMD ["java", "-jar", "web-api-server.jar"]