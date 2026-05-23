# MyHomeWeb
Home web para gestionar todas mis aplicaciones levantadas en local.

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
