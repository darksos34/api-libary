# Spring Boot - CRUD REST API
<small></small>


<b>Author:</b> <a href="https://github.com/darksos34" target="_blank">Jordy Hamwijk</a><br>
<b>Created:</b> 2024-04-05<br>
<b>Last updated:</b> 2024-04-8

[![](https://img.shields.io/badge/Spring%20Boot-8A2BE2)]() [![](https://img.shields.io/badge/release-Apr%2004,%202024-blue)]() [![](https://img.shields.io/badge/version-3.2.4-blue)]()

## 1. What is a CRUD REST API?
A CRUD REST API allows clients to perform basic operations on resources via HTTP requests. These operations include creating new resources, reading existing ones, updating resource data, and deleting resources. It adheres to the principles of REST, ensuring a predictable and uniform interface for communication between clients and servers.</br>

CRUD stands for Create, Read, Update, and Delete. </br>
It represents the four fundamental operations used to interact with database applications.<br/>
These operations allow developers to manipulate data within a collection or database.
Here’s what each function does:
- Create: Adds new entries to the database.
- Read: Retrieves entries based on specific criteria (such as filtering or searching).
- Update: Modifies specific fields in existing entries.
- Delete: Removes one or more existing entries entirely.
- CRUD (Create, Read, Update, Delete):</br>

#### For example, in SQL (a popular language for interacting with databases), the equivalent functions are Insert, Select, Update, and Delete.

## 2. How a CRUD REST API works in Spring Boot?

Spring Boot simplifies building RESTful APIs by providing a framework for handling HTTP requests and integrating with databases.</br>


### 2.1 Create a Spring Boot Application

Go to [Spring](start.spring.io) and create a new Spring Boot project.

![01-start-spring-io](https://github.com/darksos34/api-library/blob/master/src/main/resources/images/spring-initializr.png))

Add additional dependencies:

- OpenApi webflux: A library that simplifies API documentation for reactive applications.
- OpenApi webmvc - UI: A library that provides a user interface for viewing API documentation.
- OpenApi webmvc - API Docs: A library that provides API documentation for Spring WebMVC applications.
- Springdoc - OpenApi Swagger UI: A library that provides a user interface for viewing API documentation.
````    
        <!-- https://springdoc.org/ -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>${springdoc.openapi-ui}</version>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webflux-api</artifactId>
            <version>${springdoc-openapi-starter-webflux-api}</version>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc-openapi-starter-webmvc-ui}</version>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-api</artifactId>
            <version>${springdoc.openapi.starter.webmvc.api}</version>
        </dependency>
````

### ModelMapper: A library that simplifies object mapping. 

```` 
        <!--    https://modelmapper.org/getting-started/    -->
        <dependency>
            <groupId>org.modelmapper</groupId>
            <artifactId>modelmapper</artifactId>
            <version>${modelmapper.version}</version>
        </dependency>
````



## Let's Stay Connected

Feedback is always welcome. If you have any questions, comments, or suggestions, please do not hesitate to reach out.

- <b>Star</b> the repository to show your support.

- Follow me for more interesting repositories!
