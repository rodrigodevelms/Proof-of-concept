# POC - CRUD WITH KAFKA, ELASTICSEARCH, GRAPHQL, KOTLIN, KTOR, POSTGRESQL, EXPOSED, HIKARI AND FLYWAY


<br> 

### PREREQUISITES

- gradle 6.5 or superior
- jdk 11

<br>

----

<br>

### STEPS


- Do `gradlew publishToMavenLocal` on each commons lib.
- `docker-compose up -d`
- Open **localhost:9021** and create two topics: `access-domain` and `response-access-domain`
- Start Orchestration API with `gradle run` 
- In Consumer API create 4 environment variables:

        URL : jdbc:postgresql://localhost:5440/access_domain
        DRIVER: org.postgresql.Driver
        PSQL_USER : postgres
        PSQL_PASSWORD: postgres

- Start Consumer API with `gradle run`
- Open *INSOMNIA / POSTMAN* and send a request: see `swagger.yml` in access.orchestration to know how to do.
