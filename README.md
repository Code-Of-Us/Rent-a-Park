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
* Create, update, delete and list parking spots
* Create, update, delete and list persons
* Make reservation for parking spot on [Reservation Service](https://github.com/Code-Of-Us/Reservation-Service)
* Implemented Redis cache for caching user information, evicting when the update is done
* Used Test Containers in integration tests for setting up Redis, Postgres and Eureka containers

### CI/CD

* GitHub actions for Continuous Integration (CI)
  * Build with CodeCov
  * Code Analysis with SonarCloud
  * Build and push Docker image to GitHub Container Registry
  * Public GitHub package
  * Docker file
  * Docker compose file for setting up external services like Postgres, Redis, Prometheus, Grafana and Eureka


#### Monitoring
* Prometheus for collecting and storing metrics
* Grafana for visualizing and analyzing metrics

### Microservice architecture
* Eureka server - Service Registry and Client-side service discovery using Eureka
* Feign Client

![Architecture](./src/main/resources/static/architecture-overview.png)