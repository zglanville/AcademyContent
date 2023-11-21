FROM maven:3.8.2-jdk-11-slim
MAINTAINER tech-team <tech-team@wethinkcode.co.za>
RUN apt-get update
RUN mkdir "src"
COPY . /src
ADD /ServerSideApplication/target/ServerSideApplication-1.0-SNAPSHOT.jar /src
ADD /DockerResources/dockerServerShell.sh /src/
RUN chmod +x /src/dockerServerShell.sh

EXPOSE 5000
# EXPOSE 5110
# EXPOSE 5210
# EXPOSE 5220
# EXPOSE 5221

WORKDIR /src
CMD ["/bin/sh", "dockerServerShell.sh"]
