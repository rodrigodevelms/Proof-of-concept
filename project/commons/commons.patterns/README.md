# COMMONS.PATTERNS

###### Description
This library contains all Aries Project patterns:

<br>

---

### INDEX

- [Status Pattern](#Status-Pattern)
- [Retryable Pattern](#Retryable-Pattern)

<br>

### Status Pattern
`checkStatus` **-** It's a function that check something status. It can check elasticsearch connection satatus for example.

*Have three parameters:* 

|  Key           | Type         |  Behavior                               |
|----------------|--------------|-----------------------------------------|
|  target        | Target       | *What* this function will check         |
|  url           | String       | A url of target that will be tested     |
|  authorization | String       | A Basic or JWT authorization            |

######Target - It's an Enum with all possibles targets

### Retryable Pattern
`retry` **-** It's a function that retryable something over a period of time.

*Have six parameters*

|  Key             | Type         |  Behavior                                                                    |
|------------------|--------------|------------------------------------------------------------------------------|
|  tryDo           | lambda       | The behavior that will try to be executed                                    |
|  onErrorDo       | lambada      | The behavior that will be executed if all attempts fail                      |
|  times \*        |  Int         | How many times the behavior will be attempted                                |
|  initialDelay \* |  Long        | A delay between the attempts                                                 |
|  maxDelay \*     |  Long        | The max delay between the attempts                                           |
|  factor   \*     |  Double      | A value that will added to the delay, increasing the delay with each attempt |


######* have default values
