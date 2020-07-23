# COMMONS.ORCHESTRATION

###### Description
This library contains all Project orchestration logic:

<br>

---

### INDEX

- [Interface](#Interface)
- [Language](#RLanguage)
- [Model](#Model)
- [Service](#Service)
- [Utils](#Utils)
- [Validation](#Validation)

<br>

### Interface

`IMessage`  **-** Markup interface for responses to requests to API Orchestration

`IModel`    **-** Markup interface for all Models

`IRequest`  **-** Markup interface for all Requests

<br>

### Language

`Language` **-** It's a Enum with all possible languages in this Project.

`Messages` **-** Standard response messages.

<br>

### Model

 **-** This package contains all Models (either called DTO) used in this Project.

<br>

### Service

`ResponseError` **-** It's a KClass<> that that represents a response error message.

|  Key     | Type         |  Behavior                     |
|----------|--------------|-------------------------------|
|  id      | UUID         | It's the message id           |
|  date    | String       | The date the error occurred   |
|  errors  | List<String> | A list of all error messages  |

<br>

`errorMsg` **-** It's a function that create the response error message.

**Have one parameter**

|  Key           | Type          |  Behavior                |
|----------------|---------------|--------------------------|
|  errorMessages | List<String?> | May have a error message |

<br>

### Utils

<br>

### Validations
