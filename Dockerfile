FROM openjdk:11-jdk-slim

CMD ["./gradlew", "clean", "build"]

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar
# or Maven
# ARG JAR_FILE_PATH=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","/app.jar"]