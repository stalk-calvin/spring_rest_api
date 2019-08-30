# Restful API with spring-boot

This is a simple project showing a use case of RESTful API with spring-boot.

API visibility extends to how CRUD operations are handled RESTful way.

JSON format is used for the response. I have overriden the default port to 9090.

### RUN

1. docker build -t sra .
2. docker run -v ${pwd}:/src -p 9090:9090 -t sra
3. Access using the urls shown below (ex: `http://localhost:9090/surveys/Survey1/questions`).

There are 3 different APIs you can access: Survey API, Task API and Greeting API

## Survey API

There are questions associated to the survey.

Currently there is only one survey (Survey1) statically generated.

You can create/retrieve/update/delete a question from the survey.

**Create**: Request HTTP PUT method:

`http://localhost:9090/surveys/Survey1/questions` and pass in as `application/json`:

```
{
    "description": "Calvin Lee",
    "correctAnswer": "is a badass",
    "options": [
        "India",
        "Russia",
        "United States",
        "China"
    ]
}
```
**Retrieve**: Request HTTP GET method:
`http://localhost:9090/surveys/Survey1/questions` will retrieve all questions within the Survey1.

`http://localhost:9090/surveys/Survey1/questions/Question1` will retrieve specific question (Question1) from the survey (Survey1).

**Update**: Request HTTP POST call:
`http://localhost:9090/surveys/Survey1/questions/Question1` and pass in as `application/json`:

```
{
    "description": "Calvin Lee",
    "correctAnswer": "is a badass",
    "options": [
        "India",
        "Russia",
        "United States",
        "China"
    ]
}
```
to update existing record (Question1) of the survey (Survey1)

**Delete**: Request HTTP DELETE method:

`http://localhost:9090/surveys/Survey1/questions/Question1` will delete Question1 from Survey1.

## Randomized task detail API

Request HTTP POST (update) method: Just to show how API fails 80% of the time. When success, 200 OK is returned

`http://localhost:9090/api/task/details`

## Greeting Service

These various entry points are provided as HTTP GET method cala to showing different versions are handledl.
```
- http://localhost:9090/api
- http://localhost:9090/api/v1/greeting
- http://localhost:9090/api/v1/greeting?name=Calvin
- http://localhost:9090/api/v2/greeting
- http://localhost:9090/api/v2/greeting?name=You
```
