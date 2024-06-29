# Image Layer
FROM openjdk:17.0.2 as builder

#Always Uppercase separated with a low colon. ARG(argument) cariable
ARG MSVC_NAME=msvc-catalog

WORKDIR /app/$MSVC_NAME

COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

# Skip the test, the main and the compilations. Also delete target file
# Just download dependencies
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

# Another way to do the above
# RUN ./mvnw dependency:go-offline

# Copy source code
COPY ./$MSVC_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17.0.2

WORKDIR /app

# From an image we create another one. We just copy the JAR
COPY --from=builder /app/msvc-catalog/target/msvc-catalog-0.0.1-SNAPSHOT.jar .

# Internally value that cab ne changed in .env file
ENV PORT 8082

EXPOSE $PORT

# Container Layer
ENTRYPOINT ["java", "-jar", "msvc-catalog-0.0.1-SNAPSHOT.jar"]