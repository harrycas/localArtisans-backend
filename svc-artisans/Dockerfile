ARG MSVC_NAME=svc-artisans

FROM openjdk:17.0.2 as builder

ARG MSVC_NAME

WORKDIR /app/$MSVC_NAME

COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MSVC_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17.0.2

ARG MSVC_NAME

WORKDIR /app

ARG TARGET_FOLDER=/app/$MSVC_NAME/target

COPY --from=builder $TARGET_FOLDER/svc-artisans-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8080

ENV PORT $PORT_APP

EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "svc-artisans-0.0.1-SNAPSHOT.jar"]