# Commands for talk

# To start beer-api
docker-compose -f src/main/docker/app.yml up -d

# To start food-api
./mvnw quarkus:dev

# To start tavern testing tests
docker-compose up