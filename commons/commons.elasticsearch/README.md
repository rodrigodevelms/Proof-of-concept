# COMMONS.ELASTICSEARCH

###### Description
This library contains *elasticsearch* library and configurations

<br>

###### PREREQUISITES

- COMMONS.PATTERNS

<br>

### Configuration
`createClient` **-** It's a extension function that create a client connection to *elasticsearch*.
*Hove no parameters. Extend from Elasticsearch KClass<>*
<br>

### INDEX

- [Model](#Model)
- [Utils](#Utils)

<br>

---

#### Model

######ElasticSearch KClass<>

|  Key               | Type     |  Behavior                                                           |
|--------------------|----------|---------------------------------------------------------------------|
|  host              | String   | The elasticsearch host address                                      |
|  port              | String   | The elasticsearch host port                                         |
|  elkUsername       |  String  | The login username to elasticsearch                                 |
|  elkPassword       |  String  | The login password to elasticsearch                                 |
|  https             |  Boolean | If the connection use https protocol                                |
|  userSniffer       |  Boolean | If the connection use sniffer for load balancing                    |

<br>

#### Utils

`checkElasticStatus` - It's a function that check the status of elasticsearch - *up / down*

*Have three parameters:*

|  Key            | Type     |  Behavior                            |
|-----------------|----------|--------------------------------------|
|  host           | String   | The elasticsearch host address       |
|  port           | String   | The elasticsearch host port          |
|  authorization  | String   | The Basic or JWT authorization       |
|  https          | Boolean  | If the connection use https protocol |
