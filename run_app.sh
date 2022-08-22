#!/bin/bash

# Use when Java 11 is not default, replace with your Java 11 directory
export JAVA_HOME="/opt/java/amazon_jdk/amazon-corretto-11.0.11.9.1-linux-x64"
echo JAVA_HOME=\"$JAVA_HOME\"

# Run application
#mvn spring-boot:run

## Run in container
mvn clean package -DskipTests -Pproduction
docker build -t infobase/companies-employees .
docker-compose up
