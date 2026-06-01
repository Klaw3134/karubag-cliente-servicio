

Microservicio de gestión de clientes para la plataforma Karübag.
Gestiona los clientes del sistema, tanto residenciales como corporativos. Se comunica con usuario-servicio y plan-servicio para validar datos.

- Java 21
- Spring Boot 3.5.14
- Spring Data JPA
- PostgreSQL (Neon)
- WebClient (Spring WebFlux)

`8084`
`karubag_cliente`
- `usuario-servicio` (:8081) — verifica que el usuario existe
- `plan-servicio` (:8082) — verifica que el plan existe

