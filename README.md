# Zadanie domowe
### Stworzyć 2 mikroserwisy, które będą korzystały z Spring Cloud Config Server.
- Stworzyć config w zależności od środowiska (profile)
  - profile PROD -> baza danych MYSQL (pusta baza danych)
  - profile DEV -> lokalna baza danych H2 (wypełniona przykładowymi danymi)

# Rozwiązanie zadania krok po kroku

### 1. Stwórz eureka-server (new module)
- add dependency spring-web
- add dependency eureka-server
- add annotation `@EnableEurekaServer`
- add basic configuration in `application.properties`
```
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.register-fetch-registry=false
```

### 1. Stwórz config-server (new module)
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

### 2. Stwórz ms1 (new module)
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