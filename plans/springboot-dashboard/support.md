# Soporte: springboot-dashboard

## Preguntas sin contestar
- Ninguna — todas las preguntas han sido respondidas

## Cosas no comprobadas
- `back.png` — no se encontró en Descargas. Se usará fallback CSS `background-color: #0a0a12`
- Endpoints de apps self-hosted — asumimos que siguen existiendo en 192.168.1.136:19480-19482

## Casos fuera del scope
- Autenticación — descartada explícitamente, uso solo en red local
- Docker — no se contenedoriza
- Nuevas funcionalidades — solo migración del HTML existente, sin features nuevas

## Futuros puntos a tener en cuenta
- Posible migración a PostgreSQL si en el futuro se necesita multi-usuario
- Añadir más widgets o dashboards personalizables
- Posibilidad de añadir health checks server-side para las apps self-hosted

## Seguimiento de issues

| Issue | Estado | Prueba | Notas |
|-------|--------|--------|-------|
| 1. Inicializar proyecto | ✅ OK | `mvnw compile` OK | — |
| 2. Entidades JPA | ✅ OK | `mvnw compile` OK | UUID PK, isBuiltin flag |
| 3. Repositorios y servicios | ✅ OK | `mvnw compile` OK | — |
| 4. Configuración SQLite | ✅ OK | App arranca con SQLite | — |
| 5. Seed data | ✅ OK | API devuelve 9 cats + 43 links | INSERT OR IGNORE |
| 6. Thymeleaf template | ✅ OK | Dashboard sirve en `/` | th:each funcionando |
| 7. CSS extraído | ✅ OK | — | Duplicados eliminados |
| 8. JavaScript adaptado | ✅ OK | API calls vía fetch | — |
| 9. Controladores REST | ✅ OK | CRUD + export/import testeados | @Transactional añadido |
| 10. DTOs | ✅ OK | — | — |
| 11. Copiar assets | ⚠️ Parcial | back.png no encontrado | Fallback CSS |
| 12. Verificación final | ✅ OK | JAR compila, app arranca, API OK | — |

## Mejoras propuestas
- Añadir Flyway para migraciones en vez de `spring.sql.init.mode=always`
- Considerar `spring.jpa.open-in-view=true` como alternativa a `@Transactional` en cada controller
- Añadir health checks server-side para apps self-hosted en vez de JS cliente (CORS)

## Estado
Fase de implementación completada — PR #1 creada
