FROM bellsoft/liberica-openjdk-alpine:11.0.11
#FROM bellsoft/liberica-openjdk-alpine-musl
ARG JAR_FILE=target/*.jar
EXPOSE 8005
#COPY ./target/MoexStockService-0.0.1-SNAPSHOT.jar .
COPY ${JAR_FILE} app.jar
#CMD ["java","-jar","MoexStockService-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "/app.jar"]