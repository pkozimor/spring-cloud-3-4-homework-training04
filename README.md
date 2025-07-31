# Homework - Spring Cloud 3.4 - Training04 - Configuration
### Create 2 services, which will use Spring Cloud Config Server and Eureka Server
- Use two different profiles
  - profile PROD -> db MYSQL (empty db)
  - profile DEV -> local db H2 (fulfilled with sample data)

# Steb by step (solution)

### 1. Create eureka-server (new module)
- add dependency spring-web
- add dependency eureka-server
- add annotation `@EnableEurekaServer`
- add basic configuration in `application.properties`
```
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.register-fetch-registry=false
```

### 2. Create config-server (new module)
- add dependency spring-web
- add dependency spring-cloud-config-server
- add annotation `@EnableConfigServer`
- add basic configuration in `application.properties`
```
spring.application.name=config-server
server.port=8888

spring.cloud.config.server.git.uri=https://github.com/pkozimor/spring-cloud-3-4-homework-training04-configuration
spring.cloud.config.server.git.username=${GIT_USERNAME}
spring.cloud.config.server.git.password=${GIT_TOKEN}
spring.cloud.config.server.git.clone-on-start=true
```

### 3. Create ms1 (new module)
- add dependency spring-web
- add dependency spring-config-client (spring-cloud-starter-config)
- add dependency spring-eureka-client (spring-cloud-starter-netflix-eureka-client)
- optionally add annotation `@EnableDiscoveryClient` (not required since Spring Cloud 1.4.0)
- add basic configuration in `application.properties`
```
spring.application.name=ms1
eureka.instance.appname=${spring.application.name}
eureka.instance.instance-id=${spring.application.name}:${random.uuid}
server.port=0

spring.config.import=configserver:http://localhost:8888
```
.
### 4. Configure PROD, DEV profiles and database connections for ms1 service
- add dependency spring-boot-starter-data-jpa
- add dependency mysql-connector-java
- create dedicated basic configuration file for ms1 service called `ms1.properties` within remote config repository https://github.com/pkozimor/spring-cloud-3-4-homework-training04-configuration
- move basic configuration from `application.properties` to `ms1.properties`
```
eureka.instance.appname=${spring.application.name}
eureka.instance.instance-id=${spring.application.name}:${random.uuid}
server.port=0
```
- create dedicated configuration for PROD profile for ms1 service called `ms1-prod.properties` within remote config repository https://github.com/pkozimor/spring-cloud-3-4-homework-training04-configuration
```
spring.datasource.url=jdbc:mysql://localhost:3306/content_db
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=never
```
- add dependency h2
- create dedicated configuration for DEV profile for ms1 service called `ms1-dev.properties` within remote config repository https://github.com/pkozimor/spring-cloud-3-4-homework-training04-configuration
```aiignore
spring.datasource.url=jdbc:h2:mem:content_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
spring.h2.console.enabled=true
spring.h2.console.settings.web-allow-others=true
```
- add sql file with random data to fill DEV profile `data.sql`
- create sample java code and endpoints to test connection to H2 and MySQL databases `Content, ContentRepository, ContentController`
- create POST request to test adding content to the database

### 5. Create ms2 (new module)
- add dependency spring-web
- add dependency spring-config-client (spring-cloud-starter-config)
- add dependency spring-eureka-client (spring-cloud-starter-netflix-eureka-client)
- add dependency spring-boot-starter-data-jpa
- add dependency mysql-connector-java
- add dependency h2
- optionally add annotation `@EnableDiscoveryClient` (not required since Spring Cloud 1.4.0)
- add basic configuration in `application.properties`
```
spring.application.name=ms1
spring.config.import=optional:configserver:http://localhost:8888
spring.profiles.active=dev
```
- add basic configuration for basic profile within external config repository `ms2.properties`
```
eureka.instance.appname=${spring.application.name}
eureka.instance.instance-id=${spring.application.name}:${random.uuid}
server.port=0
```
- add configuration for PROD profile within external config repository `ms2-prod.properties`
```
spring.datasource.url=jdbc:mysql://localhost:3306/content_db
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=never
```
- add configuration for DEV profile within external config repository `ms2-dev.properties`
```
spring.datasource.url=jdbc:h2:mem:content_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
spring.h2.console.enabled=true
spring.h2.console.settings.web-allow-others=true
```
- 