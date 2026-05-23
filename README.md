# MyHomeWeb

Dashboard personal con acceso rápido a enlaces, widgets y aplicaciones self-hosted.

Spring Boot 3.4 + Maven + Thymeleaf + SQLite + vanilla JS/CSS.

## Arranque

```bash
./mvnw spring-boot:run
# → http://localhost:8080
```

```bash
./mvnw clean package -DskipTests
java -jar target/myhomeweb-1.0.0.jar
```

## API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/categories` | Lista categorías con sus links |
| POST | `/api/v1/categories` | Crea categoría |
| DELETE | `/api/v1/categories/{id}` | Elimina categoría custom |
| POST | `/api/v1/links` | Crea link |
| DELETE | `/api/v1/links/{id}` | Elimina link custom |
| GET | `/api/v1/export` | Exporta datos custom como JSON |
| POST | `/api/v1/import` | Importa JSON |

## Widgets
- Clima (Open-Meteo, Alcalá de Guadaíra)
- Ancho de banda (Cloudflare speed test)
- Estado de apps self-hosted (Portfolio Tracker, My Calendar, MyNotes)

## Estrategia de ramas (GitFlow)

Este repositorio sigue el modelo [GitFlow](https://nvie.com/posts/a-successful-git-branching-model/):

| Rama | Propósito |
|------|-----------|
| `main` | Código listo para producción. Cada commit aquí es un release. |
| `develop` | Integración de funciones para el próximo release. |
| `feature/*` | Nuevas funcionalidades. Base: `develop`. Destino: `develop`. |
| `release/*` | Preparación de un nuevo release. Base: `develop`. Destino: `main` y `develop`. |
| `hotfix/*` | Correcciones urgentes en producción. Base: `main`. Destino: `main` y `develop`. |

### Flujo de trabajo

1. Crear `feature/<nombre>` desde `develop`
2. Trabajar y commitar en la rama feature
3. Fusionar `feature/<nombre>` de vuelta a `develop` con `--no-ff`
4. Al alcanzar un hito, crear `release/<version>` desde `develop`
5. Ajustes finales en `release/<version>`
6. Fusionar `release/<version>` a `main` (con tag) y a `develop`
7. Bugs en producción: `hotfix/<descripcion>` desde `main`, fusionar a `main` y `develop`

### Convención de nombres

- `feature/issue-123-login` — nueva funcionalidad
- `release/1.2.0` — preparación de versión
- `hotfix/critical-security-patch` — parche urgente
