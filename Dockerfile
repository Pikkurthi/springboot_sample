FROM eclipse-temurin:17-jre as runtime
WORKDIR /app
COPY target/springboot-crud-*.jar app.jar
ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
