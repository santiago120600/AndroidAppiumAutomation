FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

COPY pom.xml /app/pom.xml
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Copy project files
COPY src /app/src
COPY browserstack.yml /app/browserstack.yml

ENV BROWSERSTACK_USERNAME=
ENV BROWSERSTACK_ACCESS_KEY=

# Run Maven tests
ENTRYPOINT ["sh", "-c", "mvn test -DBROWSERSTACK_USERNAME=\"${BROWSERSTACK_USERNAME}\" -DBROWSERSTACK_ACCESS_KEY=\"${BROWSERSTACK_ACCESS_KEY}\" -Dbrowserstack.sdk=true"]