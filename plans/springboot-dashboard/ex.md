# Research: springboot-dashboard

## Problema
El usuario tiene un dashboard web estático (`myapps.html`) como página de inicio personal con:
- Widgets: estado de apps self-hosted, clima (Open-Meteo), test de ancho de banda (Cloudflare)
- Grid de categorías con ~50 enlaces externos a webs habituales
- Gestor de enlaces/categorías custom (localStorage + export/import JSON)

Al ser un HTML estático tiene limitaciones: persistencia frágil (localStorage), sin backend, difícil de extender. Quiere migrarlo a un proyecto Spring Boot con frontend HTML/CSS/JS manteniendo funcionalidad y estética.

## Código / assets relevantes
- `/home/maximosro/Descargas/myapps.html` (1527 líneas) — HTML único con CSS y JS inline. Es TODO el código actual.
- `back.png` — imagen de fondo referenciada en el CSS (en el mismo directorio que el HTML)
- Proyecto `MyHomeWeb` — repositorio vacío (solo README + .gitignore), usa GitFlow

## Estructura actual del HTML

| Componente | Implementación |
|---|---|
| Topbar | Logo SVG + buscador Google + fecha (JS local) |
| Sidebar izquierda | Widget "Mis Aplicaciones" (4 apps, check status vía fetch) + Widget clima (Open-Meteo) + Widget ancho de banda (Cloudflare speed test) |
| Grid central | ~8 categorías hardcodeadas con ~50 enlaces externos |
| Gestor de enlaces | Añadir/eliminar enlaces y categorías → localStorage |
| Export/Import | JSON export (File System Access API + download fallback), JSON import (file input) |
| Estilo | Dark theme, glassmorphism (backdrop-filter blur), fondos semitransparentes, Inter + Orbitron fonts |

## Funcionalidad a preservar

1. **Widget estado apps**: check HTTP a 3-4 endpoints locales (192.168.1.136:19480-19482) cada 30s
2. **Widget clima**: Open-Meteo API (gratis, sin key), coordenadas Alcalá de Guadaíra, refresh cada 10min
3. **Widget ancho de banda**: Cloudflare speed test (25MB download), manual + auto al cargar
4. **Buscador Google**: form submit a google.com/search
5. **Grid categorías/enlaces**: ~50 enlaces predefinidos en ~8 categorías
6. **CRUD custom**: añadir/eliminar enlaces y categorías (ahora localStorage → pasaría a backend)
7. **Export/Import JSON**: de los datos custom
8. **Diseño visual**: dark theme glassmorphism, responsive, fuentes Google

## Patrones existentes
- No hay código previo en el proyecto. Es un greenfield.
- El HTML actual es el único "patrón" — hay que extraer datos, estructura y estilos de él.
- GitFlow ya está definido en README → el desarrollo irá en ramas `feature/*`.

## Decisiones tomadas

| Decisión | Respuesta |
|---|---|
| Template engine | **Thymeleaf** — server-side rendering, vanilla JS para interactividad |
| Base de datos | **SQLite** — embedded, sin servidor, el fichero vive en el proyecto |
| Persistencia enlaces | **Backend (DB)** — adiós localStorage |
| Widgets clima/bandwidth | **JS cliente** — no necesitan backend, APIs públicas |
| Autenticación | **No** — uso en red local, sin login |
| Dockerizar | **No** |
| Diseño CSS | **Mantener** — extraer a ficheros .css separados |
| Build tool | **Maven** — con Maven wrapper (`mvnw`) incluido |
| Enlaces predefinidos | **Todos en BD** — como datos semilla (data.sql o similar) |
| Nuevas funcionalidades | **Ninguna por ahora** — se itera más adelante |

## Stack final

```
Spring Boot 3.x + Maven + Thymeleaf + SQLite + vanilla JS + CSS
Sin autenticación, sin Docker
```

## Próximos pasos

- Extraer CSS a `dashboard.css`
- Extraer JS a `dashboard.js` 
- Modelar entidades: `Category` y `Link`
- Crear repositorios JPA + data.sql con los ~50 enlaces semilla
- Crear controlador Thymeleaf que sirva el dashboard y API REST para el CRUD
- Copiar `back.png` a `src/main/resources/static/`
