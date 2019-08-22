FROM openjdk:8-jre-alpine
MAINTAINER sathish vasu 
VOLUME /tmp
EXPOSE 8098 8099

ADD ./target/studentservice-0.0.2-SNAPSHOT.jar studentservice-0.0.2-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","studentservice-0.0.2-SNAPSHOT.jar"]


#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app

#ENTRYPOINT ["java","-cp","app:app/lib/*","com.studentservice.StudentServiceApplication"]

#ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions","-XX:+UseCGroupMemoryLimitForHeap","-XX:MaxRAMFraction=1","-XshowSettings:vm","-cp","app:app/lib/*","com.studentservice.StudentServiceApplication"]


