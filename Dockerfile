FROM openjdk:8-jdk-alpine

WORKDIR /app

#CMD ["./mvnw", "clean", "package"]

VOLUME /tmp

COPY ${JAR_FILE} app.war

EXPOSE 8081

# linux java file 실행문 java -jar 파일명
ENTRYPOINT ["java", "-jar", "app.war"]