FROM openjdk:8-jdk-alpine

# 1. install common bash tools
RUN apk add --no-cache bash gawk sed grep bc coreutils curl tar libc6-compat

# 2. Install Maven
ENV MAVEN_VERSION 3.5.2
ENV MAVEN_HOME /usr/lib/mvn
ENV PATH $MAVEN_HOME/bin:$PATH

RUN wget http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  mv apache-maven-$MAVEN_VERSION /usr/lib/mvn

ARG USER_HOME_DIR="/root"
ENV MAVEN_HOME /usr/lib/mvn
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
#ENTRYPOINT ["/usr/bin/mvn"]

# 3. Prepare dependencies
WORKDIR /app
# Copy the pom.xml into the image to install all dependencies
COPY pom.xml ./

# Copy the settings.xml into the image
#COPY settings.xml /root/.m2/

# Run install task so all necessary dependencies are downloaded and cached in
# the Docker image. We're running through the whole process but disable
# testing and make sure the command doesn't fail.
RUN export MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"; mvn verify clean --fail-never -B -DfailIfNoTests=false -Dcheckstyle.skip=true
