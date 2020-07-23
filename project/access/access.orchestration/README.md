# ACCESS.ORCHESTRATION

###### Description

*Api* responsible for receiving requests from an external base and sending to *Kafka*

<br>

###### PREREQUISITES

- COMMONS.TEST
- COMMONS.KAFKA
- COMMONS.ELASTICSEARCH
- COMMONS.ORCHESTRATION

<br>

     docker-compose.yml : postgres / zookeeper/ kafka / confluent-control-center / elasticsearch / kibana
     start: docker-compose up -d
     stop: docker-compose down

 <br> 
 
---

### INDEX

- [Controller](#Controller)
- [Elasticsearch](#Elasticsearch)
- [GraphQL](#GraphQL)

<br>

#### Controller
**-** The controller contains the routes for insertion, update and deletion. For more information check the swagger

<br>

#### Elasticsearch
**-** Responsible to create a new index and check if elk is up

<br>

#### GraphQL
**-** TODO
