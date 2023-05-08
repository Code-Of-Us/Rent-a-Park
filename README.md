# Rent-a-Park

![build workflow](https://github.com/Code-Of-Us/Rent-a-Park/actions/workflows/build.yaml/badge.svg)
![codecov.io](https://codecov.io/github/Code-Of-Us/Rent-a-Park/coverage.svg)

![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Code-Of-Us_Rent-a-Park&metric=bugs)
![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Code-Of-Us_Rent-a-Park&metric=vulnerabilities)

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka)
![CodeCov](https://img.shields.io/badge/codecov-%23ff0077.svg?style=for-the-badge&logo=codecov&logoColor=white)
![SonarQube](https://img.shields.io/badge/SonarQube-black?style=for-the-badge&logo=sonarqube&logoColor=4E9BCD)
## Content

### Technologies 

* Java
* Spring Boot, Spring Cloud
* PostgreSQL
* Redis
* Docker
* Kafka

### Features
* The application includes several features that enable users to manage parking spots and persons, such as creating, 
updating, deleting and getting a list of spots and persons. 
* <b>Redis</b> cache is implemented to cache user information,
reducing the need to query the database for the information that does not change frequently. 
Redis cache is set to evict the cached information whenever an update is made, ensuring that the information is always up-to-date.
* Make reservation for parking spot on [Reservation Service](https://github.com/Code-Of-Us/Reservation-Service)
* Integration tests use <b>Test Containers</b> for setting up test environment (Redis, Postgres and Eureka container)

### CI/CD

* The application uses GitHub actions for Continuous Integration (CI), including:
  * Building the application with <b>CodeCov</b>
  * Performing Code Analysis with <b>SonarCloud</b>
  * Building and pushing Docker image to <b>GitHub Container Registry</b> (GHCR)
  * Creating public <b>GitHub package</b> to make the application easily accessible to other developers
* Containerizing the application is achieved using Docker, creating the <b>Docker file</b> and <b>Docker compose</b> file for setting up external services such as Postgres, Redis, Prometheus, Grafana and Eureka.


#### Monitoring
* <b>Prometheus</b> for collecting metrics and storing them in a time-series database
* <b>Grafana</b> - for visualizing and analyzing metrics. It provides user-friendly interface and allows developers to create dashboards with different types of visualizations.
It integrates seamlessly with Prometheus, enabling developers to create custom dashboards that display the metrics collected by Prometheus

### Microservice architecture
* <b>Eureka server</b> is used to facilitate communication between microservices, which serves as service registry and client side service discovery. 
Every microservice can register itself with the Eureka Server and other microservices can discover and communicate with it using the server.
* Another tool used for communication between microservices is the <b>Feign Client</b>, which is a declarative REST client that simplifies the process of making HTTP requests. 
It allows developers to define the API for a microservice in a simple interface, which is then implemented by Feign at runtime.
* <b>Cloud Config Server</b> - stores common properties for all microservices in a centralized location, which can be accessed by all microservices
* <b>Kafka</b> for sending and consuming messages between microservices. It enables microservices to communicate asynchronously.


![Architecture](./src/main/resources/static/architecture-overview.png)