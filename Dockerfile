FROM openjdk:17
RUN mkdir "app"
COPY target/library-management-system-0.0.1-SNAPSHOT.jar /app
WORKDIR /app
EXPOSE 8080
CMD java -jar library-management-system-0.0.1-SNAPSHOT.jar