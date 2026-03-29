FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

# Install curl + Node + Appium (as before)
# RUN apt-get update && apt-get install -y curl \
#     && curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
#     && apt-get install -y nodejs \
#     && node -v && npm -v \
#     && npm install -g appium@3.2.2 \
#     && appium driver install uiautomator2

# Copy project files
COPY src /app/src
COPY pom.xml /app/pom.xml

ENV PROFILE=
ENV APP_PATH=

# Run Maven tests
ENTRYPOINT ["sh", "-c", "mvn test -Dprofile=${PROFILE} -Dapp.path=${APP_PATH}"]