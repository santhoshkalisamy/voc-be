#Stage 1
FROM openjdk:17 as build
# Work directory for the app. Create app directory and cd to it
WORKDIR /app

# Copy mvn executables
COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw clean install -DskipTests

#----
# Final stage
#----

FROM openjdk:17

WORKDIR /app

COPY --from=build app/target/VoiceOfConsumer-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java","-jar","VoiceOfConsumer-0.0.1-SNAPSHOT.jar"]



