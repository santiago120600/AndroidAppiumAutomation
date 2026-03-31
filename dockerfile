FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

COPY pom.xml /app/pom.xml
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Copy project files
COPY src /app/src

ENV PROFILE=
ENV APP_PATH=

# Run Maven tests
ENTRYPOINT ["sh", "-c", "mvn test -Dprofile=${PROFILE} -Dapp.path=${APP_PATH}"]