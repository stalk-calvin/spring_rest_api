# Restful API with spring-boot

This is a simple project where it builds a RESTful API with spring-boot where it defaults to json response.

### RUN

1. docker build -t sra .
2. docker run -v ${pwd}:/src -p 8080:8080 -it sra /bin/bash
3. `mvn spring-boot:run` or you can also build a jar and execute it separately

After running either the maven code or jar, accessible APIs are:
- http://localhost:8080/api
- http://localhost:8080/api/greeting(?name=Calvin)
