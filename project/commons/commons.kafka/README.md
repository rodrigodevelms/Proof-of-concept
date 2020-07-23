# COMMONS.KAFKA

###### Description
This library contains *kafka* library and configurations

<br>

###### PREREQUISITES

- COMMONS.TEST
- COMMONS.PATTERNS

<br>

---

### Index

- [Kafka](#Kafka)
- [Service](#Service)
- [Utils](#Utils)

<br>

#### Kafka
**-** Package contains the configuration to build a consumer and producer.

`createConsumer` It's a function responsible to create a consumer. 

*Have four parameters*.

|  Key               | Type     |  Behavior                                        |
|--------------------|----------|--------------------------------------------------|
|  bootstrapServers  | String          | The kafka broker address and port         |
|  group             | String          | The kafka consumer group                  |
|  autoCommit        | Boolean         | The behavior of auto commit *on/off*      |
|  offsetBehavior   | OffsetBehaviour | Enum with all offset behaviors             | 

<br> 

`createProducer` It's a function responsible to create a producer.

*Have four parameters*.

|  Key              | Type        |  Behavior                                                   |
|-------------------|-------------|-------------------------------------------------------------|
|  bootstrapServers | String      | The kafka broker address and port                           |
|  acks             | Acks        | The enum with all acks behaviors                            |
|  retries          | Int         | Number of time that producer will try produces the message  |  
|  linger           | Int         | The time that the producer will wait to send the message    | 
|  batchSize        | BatchSize   | Enum with all batch sizes                                   |

<br>

`Event KClass<>` any message sent or consumed through Kafka is an Event.

|  Key         | Type        |  Behavior                                                   |
|--------------|-------------|-------------------------------------------------------------|
|  id          | UUID        | The event id. Same as Partition id                          |
|  taskId      | UUID        | The task id randomly generated                              |
|  Information | Information | KClass<> with these data: event type (*action / response*), event status (*open / success / error*) and event method (*insert / update / status /delete*) |
|  request     | Request     | KClass<> with these data: token (*token string*), room (*web socket room*), schema (*database schema name*)   |  
|  message     | T           | The data itself. Can be anything                            |

<br>

#### Service
**-** Package contains the behaviors of consumer and producer.

`produceEvent` It's a extension function from Producer KClass<>, that produces an Event to a specific topic. 

######Producer KClass<>
|  Key                 | Type           |  Behavior                                                                           |
|----------------------|----------------|-------------------------------------------------------------------------------------|
|  topic               | String         | The target topic name                                                               |
|  event               | String         | The Event<T> KClass<> that contains the message                                     |
|  bootstrapServers    | String         | The broker address with port                                                        |
|  retries             | Int            | How many times the produces will try resend the message                             |
|  linger              | Int            | For how long the producer will wait, until news messages is created                 |  
|  batchSize           | BatchSize      | The maximum size, in bytes, of the batch (a batch can contains one or more messages |

<br>

`consumeEvent` It's a extension function from Consumer KClass<>, that consumes an Event from a specific topic and produces a success or error message to a specifc **response**-topic-name".

######Consumer KClass<>

|  Key                 | Type        |  Behavior                                      |
|----------------------|-------------|------------------------------------------------|
|  topic               | String         | The target topic name                       |
|  bootstrapServers    | String         | The broker address with port                |
|  group               | String         | The group name                              |
|  autoCommit          | Boolean        | The behavior of auto commit *true / false*  |  
|  offsetBehaviour     | OffSetBehavior | Enum with all offset behaviors              |
|  pollDuration        | Long           | The time period of each pull                |

<br>

#### Utils

**-** Functions that helps to do:

* `Jackson` serialize and deserialize a message.
* `Event` create a successful or failure response.
* `Constants` constants to use in code.
