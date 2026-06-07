# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

**First run:** create the `data/` directory (gitignored, not in repo — SQLite needs it before creating the DB file):

```bash
mkdir -p data
```

```bash
# Build the project (skip tests)
./mvnw clean package -DskipTests

# Run in development mode (hot-reload with Thymeleaf cache off)
./mvnw spring-boot:run
# → http://localhost:8080

# Run production JAR
java -jar target/myhomeweb-1.0.0.jar
```

## Project Architecture

**Stack:** Spring Boot 3.4.4 / Java 21 / Maven / Thymeleaf / SQLite (via Hibernate community dialect) / vanilla JS + CSS.

No tests exist yet (`src/test/` is empty).

### Package structure (`com.myhomeweb`)

| Package | Purpose |
|---|---|
| `model/` | JPA entities: `Category` (UUID PK, isBuiltin flag, OneToMany→Link), `Link` (UUID PK, ManyToOne→Category). Both use `displayOrder` for manual ordering |
| `dto/` | `CategoryDTO`, `LinkDTO`, `ExportDTO` — mapping from entities for API responses |
| `repository/` | Spring Data JPA interfaces: `CategoryRepository`, `LinkRepository` |
| `service/` | `CategoryService`, `LinkService` — transactional business logic. Built-in entities (isBuiltin=true) cannot be deleted |
| `controller/` | REST + one MVC controller |
| `config/` | `WebConfig` (empty WebMvcConfigurer placeholder) |

### Controllers

| Endpoint | Controller | Method |
|---|---|---|
| `GET /` | `HomeController` (MVC) | Renders `dashboard.html` with all categories+links |
| `GET /api/v1/categories` | `CategoryApiController` | List all categories |
| `POST /api/v1/categories` | `CategoryApiController` | Create custom category |
| `DELETE /api/v1/categories/{id}` | `CategoryApiController` | Delete custom category |
| `POST /api/v1/links` | `LinkApiController` | Create custom link |
| `DELETE /api/v1/links/{id}` | `LinkApiController` | Delete custom link |
| `GET /api/v1/export` | `ExportImportController` | JSON export of custom data |
| `POST /api/v1/import` | `ExportImportController` | JSON import with dedup |

### Key configuration (`application.properties`)

- **Database:** SQLite file at `data/myhomeweb.db` with `ddl-auto=update`
- **Seed data:** `src/main/resources/data.sql` runs on every startup (`spring.sql.init.mode=always`) — uses `INSERT OR IGNORE` so data persists across restarts. Contains ~9 built-in categories and ~43 built-in links
- **JPA:** `open-in-view=false` — lazy init is forced manually in `HomeController.dashboard()` via `c.getLinks().size()`
- **Thymeleaf cache:** disabled for development

### Frontend

- **Template:** Single-page `dashboard.html` — Thymeleaf-rendered grid of category cards, each containing link items with favicons from Google's favicon API
- **CSS:** `dashboard.css` — dark theme with backdrop-filter glassmorphism, Inter + Orbitron fonts, responsive (sidebar hidden at ≤1100px, single-column grid at ≤640px)
- **JS:** `dashboard.js` — vanilla JS with:
  - Google search bar in topbar
  - Self-hosted app status checker (Portfolio Tracker, My Calendar, MyNotes) with 30s interval
  - Weather widget via Open-Meteo API (Alcalá de Guadaíra)
  - Bandwidth widget via Cloudflare speed test API (25MB download)
  - CRUD modals for categories/links with add/delete
  - JSON export/import

### API client helper

`dashboard.js` provides `apiFetch(method, path, body)` — all CRUD calls use this. Creates/deletes reload the page on success.

### Git workflow

Follows GitFlow: `main` (production releases) → `develop` (integration) → `feature/*`, `release/*`, `hotfix/*`. See README.md for full convention.

### Dependency notes

- **SQLite + Hibernate:** requires `hibernate-community-dialects` for `SQLiteDialect`
- **SQLite UUID limitation:** SQLite stores UUIDs as TEXT. The JPA `@PrePersist` pattern is NOT used — UUIDs are generated in entity constructors via `UUID.randomUUID()`
