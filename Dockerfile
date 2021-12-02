FROM openjdk:11

WORKDIR /
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

RUN useradd -m myuser
USER myuser

ENTRYPOINT ["java","-jar","/app.jar"]
#CMD java -jar -Dspring.profiles.active=prod app.jar
EXPOSE 8080