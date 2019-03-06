FROM openjdk:alpine
MAINTAINER sathish vasu 
VOLUME /tmp
EXPOSE 8098
ADD ./target/studentservice-0.0.1-SNAPSHOT.jar studentservice-0.0.1-SNAPSHOT.jar
RUN sh -c 'touch /studentservice-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","studentservice-0.0.1-SNAPSHOT.jar"]
