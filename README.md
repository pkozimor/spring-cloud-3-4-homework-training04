# Zadanie domowe
### Stworzyć 2 mikroserwisy, które będą korzystały z Spring Cloud Config Server.
- Stworzyć config w zależności od środowiska (profile)
  - profile PROD -> baza danych MYSQL (pusta baza danych)
  - profile DEV -> lokalna baza danych H2 (wypełniona przykładowymi danymi)

# Rozwiązanie zadania krok po kroku

### 1. Stwórz eureka-server (new module)
- add dependency spring-web
- add dependency eureka server
- add annotation `@EnableEurekaServer`
- add basic configuration in application.properties
```
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.register-fetch-registry=false
```

