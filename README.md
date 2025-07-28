# Zadanie domowe
### Stworzyć 2 mikroserwisy, które będą korzystały z Spring Cloud Config Server.
- Stworzyć config w zależności od środowiska (profile)
  - profile PROD -> baza danych MYSQL (pusta baza danych)
  - profile DEV -> lokalna baza danych H2 (wypełniona przykładowymi danymi)
#### Potencjalne rozwiązania

# Rozwiązanie zadania krok po kroku

## Stwórz eureka-server (new module)
- dependency spring-web
- dependency eureka server
