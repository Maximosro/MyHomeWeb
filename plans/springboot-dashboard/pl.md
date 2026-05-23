# Plan: springboot-dashboard

## Contexto
Convertir un dashboard HTML estático de 1527 líneas en un proyecto Spring Boot completo. El HTML actual usa localStorage para persistencia, no tiene backend, y es difícil de mantener. Se migra a Spring Boot 3.x + Maven + Thymeleaf + SQLite + vanilla JS/CSS, manteniendo exactamente la misma estética (dark theme glassmorphism) y funcionalidad.

## Stack
Spring Boot 3.4.x + Maven + Thymeleaf + SQLite + vanilla JS/CSS · Sin auth · Sin Docker · Java 21
IDs UUID generados por la aplicación (evita problemas con AUTOINCREMENT de SQLite)

## Arquitectura

```
src/main/java/com/myhomeweb/
├── MyHomeWebApplication.java
├── model/
│   ├── Category.java          (id UUID, name, icon, displayOrder, isBuiltin, List<Link>)
│   └── Link.java              (id UUID, name, url, Category, displayOrder, isBuiltin)
├── repository/
│   ├── CategoryRepository.java
│   └── LinkRepository.java
├── service/
│   ├── CategoryService.java
│   └── LinkService.java
├── controller/
│   ├── CategoryApiController.java    (REST CRUD /api/v1/categories)
│   ├── LinkApiController.java        (REST CRUD /api/v1/links)
│   └── ExportImportController.java   (GET /api/v1/export, POST /api/v1/import)
├── dto/
│   ├── CategoryDTO.java
│   ├── LinkDTO.java
│   └── ExportDTO.java
└── config/
    └── WebConfig.java              (view controller: GET / → dashboard)

src/main/resources/
├── application.properties
├── data.sql                    (seed: ~50 links en 9 categorías)
├── templates/
│   └── dashboard.html          (Thymeleaf con th:each)
└── static/
    ├── css/dashboard.css       (extraído del HTML, sin duplicados)
    ├── js/dashboard.js         (fetch API + widgets cliente)
    └── images/back.png         (pendiente de localizar)
```

## API REST

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/categories` | Lista categorías con sus links |
| POST | `/api/v1/categories` | Crea categoría `{name, icon}` |
| DELETE | `/api/v1/categories/{id}` | Elimina categoría (solo custom, 403 si builtin) |
| POST | `/api/v1/links` | Crea link `{name, url, categoryId}` |
| DELETE | `/api/v1/links/{id}` | Elimina link (solo custom, 403 si builtin) |
| GET | `/api/v1/export` | Exporta datos custom como JSON |
| POST | `/api/v1/import` | Importa JSON y mergea sin duplicados |

## Issues

- [ ] **1. Inicializar proyecto** — pom.xml (spring-boot-starter-web, thymeleaf, data-jpa, sqlite-jdbc, hibernate-community-dialects), Maven wrapper, .gitignore
- [ ] **2. Entidades JPA** — Category y Link con JPA annotations, incluyendo `isBuiltin` para proteger seed data
- [ ] **3. Repositorios y servicios** — Spring Data repos + lógica de negocio (CRUD con protección de builtins)
- [ ] **4. Configuración SQLite** — application.properties con dialecto Hibernate 6, ddl-auto=update, data.sql deferred
- [ ] **5. Seed data** — data.sql con las 9 categorías y ~50 enlaces extraídos del HTML, UUIDs fijos para referencias
- [ ] **6. Thymeleaf template** — dashboard.html con th:each para categorías/enlaces, manteniendo estructura HTML original
- [ ] **7. CSS extraído** — dashboard.css (eliminar duplicados, ajustar path de back.png)
- [ ] **8. JavaScript adaptado** — dashboard.js: mantener widgets cliente (clima, bandwidth, status apps), reescribir CRUD con fetch() a la API, adaptar export/import
- [ ] **9. Controladores REST** — CategoryApiController, LinkApiController, ExportImportController + WebConfig con view controller para `/`
- [ ] **10. DTOs** — CategoryDTO, LinkDTO, ExportDTO para desacoplar API de entidades JPA
- [ ] **11. Copiar assets** — back.png a static/images/ (si se localiza), favicons vía Google API
- [ ] **12. Verificación** — compilar, arrancar, probar todos los widgets y CRUD en navegador

## Rama
`feature/springboot-dashboard` desde `develop`

## Riesgos

| Riesgo | Mitigación |
|--------|-----------|
| `back.png` no encontrado en Descargas | CSS tiene fallback `background-color: #0a0a12`, no rompe layout |
| APIs externas (Open-Meteo, Cloudflare) | Van por JS cliente con manejo de errores ya existente |

## Verificación

```bash
./mvnw clean compile && ./mvnw spring-boot:run
# Abrir http://localhost:8080 y comprobar:
# - Grid de 9 categorías con ~50 enlaces
# - Widget clima, bandwidth, status apps funcionando
# - CRUD de enlaces/categorías custom
# - Export/import JSON
# - Persistencia tras reinicio
```
