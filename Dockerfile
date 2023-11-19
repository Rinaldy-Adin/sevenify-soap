FROM maven:alpine as build
ENV HOME=/usr/app

RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
ADD . $HOME
RUN mvn package

FROM amazoncorretto:8
COPY --from=build /usr/app /app
WORKDIR /app
EXPOSE 7000
CMD java -jar ./target/SevenifySoap-1.0-SNAPSHOT-jar-with-dependencies.jar