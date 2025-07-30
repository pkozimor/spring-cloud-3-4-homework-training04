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