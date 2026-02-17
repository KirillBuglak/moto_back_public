<h1 align="center">Moto Back</h1>

----
<p align="center">
<img src="imagesForReadme/back.gif"></p>

----
## Stack
Kotlin, Java, Spring Boot, Security, WebFlux, Kafka, PostgreSQL,  
Mongo, Redis, Grafana / Prometheus, Junit / Mockito, Jenkins, Gradle.
____
## Short description
This is a Spring WebFlux-based abstract CRUD application featuring:
<li>Authentication / Authorization</li>
<li>Reactive MongoDB, Redis, and PostgreSQL support</li>
<li>Configurable caching layer</li>
<li>Messaging functionality</li>
<li>Comprehensive monitoring</li>
<li>CI/CD pipeline with Docker Compose and related configurations</li>

____
## Authentication and Authorization
Spring Security helps authenticate users by retrieving their details from MongoDB.  
Authorization is implemented using JWT tokens.  
The resulting token is securely stored in an HTTP-only cookie on the client side.
![img.png](imagesForReadme/img.png)
____
## Cache
Cache is configured via cacheManager in RedisConfig, with configurable TTL (time-to-live) parameters.  
Doing so, different parts of application's cache can be fine-tuned.
![img_1.png](imagesForReadme/img_1.png)
____
## Messaging
![messaging.gif](imagesForReadme/messaging.gif)

Messaging functionality is implemented using the spring-boot-starter-mail dependency within the "messaging" service.
![img_8.png](imagesForReadme/img_8.png)
![img_9.png](imagesForReadme/img_9.png)
____
## Microservices communication
Kafka serves as a communication medium.  
Producer:  
![img_10.png](imagesForReadme/img_10.png)  
Consumer:  
![img_11.png](imagesForReadme/img_11.png)
____
## Test
Testing is done with a help of JUnit / Mockito.
![img_2.png](imagesForReadme/img_2.png)
____
## Monitoring
Grafana / Prometheus are integrated to provide real-time monitoring of application metrics.  
CPU, Memory usage, I/O are among much more metrics.
![img_7.png](imagesForReadme/img_7.png)
____
## CI/CD
![jenkins.gif](imagesForReadme/jenkins.gif)

Jenkins orchestrates a parallelized CI/CD pipeline to automate builds, tests, and deployments.
![img_3.png](imagesForReadme/img_3.png)

Test results are aggregated and stored in a predefined directory for traceability and reporting.
![img_4.png](imagesForReadme/img_4.png)
____
## Gradle
This is a multi-module Gradle project, with a shared "commons" module used by both "core" and "messaging" services.  
![img_5.png](imagesForReadme/img_5.png)  

All dependencies are centrally managed in libs.versions.toml, enabling consistent versioning and easy maintenance.  
![img_6.png](imagesForReadme/img_6.png)
____
## How to build
You would need to:
<li>Create keystore.p12;</li>

![img_12.png](imagesForReadme/img_12.png)  
![img_13.png](imagesForReadme/img_13.png)
<li>Configure Gmail SMTP credentials</li>

![img_14.png](imagesForReadme/img_14.png)
______