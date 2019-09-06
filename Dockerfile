FROM openjdk:8
MAINTAINER sathish vasu 
VOLUME /tmp
EXPOSE 8098 8099

ADD ./target/studentservice-0.0.1-SNAPSHOT.jar studentservice-0.0.1-SNAPSHOT.jar 

ENTRYPOINT ["java","-jar","studentservice-0.0.1-SNAPSHOT.jar"]

