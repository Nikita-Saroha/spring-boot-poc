Spring Boot REST Service using Maven



Spring Boot is compatible with Apache Maven 3.2 or above. If you don�t already have Maven installed you can follow the instructions at maven.apache.org.


On Windows, if you don't already have GIT Bash installed, download [GIT Bash here](https://git-scm.com/downloads)



Clone Code in a working directory using

```
$ ssh://git@coxrepo.corp.cox.com/bmw/${service.name}.git
```


Once you have cloned the code, you can now run the Spring Boot REST service using

```
$ mvn clean spring-boot:run
```


Swagger will provide you with complete documentation of the application which includes all the API calls descriptions with sample request and response. 
Below is the format of swagger URL

```
http://localhost:8080/${server.contextPath}/swagger-ui.html
```