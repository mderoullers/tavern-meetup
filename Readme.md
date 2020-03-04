# Commands for talk

## To start beer-api
docker-compose -f src/main/docker/app.yml up -d

### To run App Engine localy
./mvnw package appengine:run -DskipTests

### To deploy App Engine
./mvnw package appengine:deploy -DskipTests -Pgae,prod-gae

## To start food-api
./mvnw quarkus:dev

## To start tavern testing tests
docker-compose up