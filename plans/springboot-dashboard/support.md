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

## Estado
Fase de plan completada — listo para `/implement`
