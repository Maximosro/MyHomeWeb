# Implement: springboot-dashboard

## Rama
`feature/springboot-dashboard`

## Cambios realizados
| Archivo | Acción | Descripción |
|---------|--------|-------------|
| `pom.xml` | CREAR | Spring Boot 3.4.4 + Maven + web, thymeleaf, data-jpa, sqlite-jdbc, hibernate-community-dialects |
| `mvnw`, `.mvn/` | CREAR | Maven wrapper 3.9.9 |
| `.gitignore` | MODIFICAR | Añadido target/, data/, IDE, SQLite |
| `MyHomeWebApplication.java` | CREAR | Clase principal Spring Boot |
| `application.properties` | CREAR | SQLite dialect Hibernate 6, ddl-auto=update, data.sql deferred |
| `model/Category.java` | CREAR | JPA entity, UUID PK, isBuiltin flag |
| `model/Link.java` | CREAR | JPA entity, UUID PK, getDomain() helper |
| `repository/CategoryRepository.java` | CREAR | Spring Data JPA |
| `repository/LinkRepository.java` | CREAR | Spring Data JPA |
| `service/CategoryService.java` | CREAR | CRUD con protección de builtins |
| `service/LinkService.java` | CREAR | CRUD con protección de builtins |
| `dto/CategoryDTO.java` | CREAR | DTO con fromEntity() |
| `dto/LinkDTO.java` | CREAR | DTO con fromEntity() |
| `dto/ExportDTO.java` | CREAR | DTO para export/import JSON |
| `controller/HomeController.java` | CREAR | GET / → dashboard con model |
| `controller/CategoryApiController.java` | CREAR | REST CRUD /api/v1/categories |
| `controller/LinkApiController.java` | CREAR | REST CRUD /api/v1/links |
| `controller/ExportImportController.java` | CREAR | GET /api/v1/export, POST /api/v1/import |
| `config/WebConfig.java` | CREAR | Configuración WebMvc (placeholder) |
| `data.sql` | CREAR | Seed data: 9 categorías, 43 enlaces con UUIDs fijos |
| `templates/dashboard.html` | CREAR | Thymeleaf template con th:each |
| `static/css/dashboard.css` | CREAR | CSS extraído, duplicados eliminados, path back.png ajustado |
| `static/js/dashboard.js` | CREAR | JS adaptado: fetch API + widgets cliente |

## PR
https://github.com/Maximosro/MyHomeWeb/pull/1

## Notas
- `back.png` no encontrado en Descargas — CSS tiene fallback `background-color: #0a0a12`
- IDs UUID en vez de auto-increment para evitar problemas con dialecto SQLite
- `HomeController` reemplaza el view controller original (necesario para pasar model al template)
- El CSS del HTML original tenía un bloque `body` duplicado — corregido
