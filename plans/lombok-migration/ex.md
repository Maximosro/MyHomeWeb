# Research: lombok-migration

## Problema
El proyecto ya tiene Lombok como dependencia en `pom.xml` pero no se usa en ninguna clase. Todas las entidades, DTOs, servicios y controladores tienen getters, setters y constructores escritos manualmente. Se quiere aplicar anotaciones Lombok para eliminar boilerplate.

## Código / assets relevantes

### Clases a modificar (por orden de impacto)

| Archivo | Cambio | Líneas eliminadas |
|---|---|---|
| `model/Category.java` | `@Getter @Setter @NoArgsConstructor` | ~15 líneas de getters/setters + constructor vacío |
| `model/Link.java` | `@Getter @Setter @NoArgsConstructor` | ~15 líneas de getters/setters + constructor vacío |
| `dto/CategoryDTO.java` | `@Getter @Setter @NoArgsConstructor` | ~14 líneas de getters/setters |
| `dto/LinkDTO.java` | `@Getter @Setter @NoArgsConstructor` | ~14 líneas de getters/setters |
| `dto/ExportDTO.java` | `@Getter @Setter @NoArgsConstructor` en clase principal + 2 inner classes | ~15 líneas de getters/setters |
| `service/CategoryService.java` | `@RequiredArgsConstructor` | 3 líneas de constructor |
| `service/LinkService.java` | `@RequiredArgsConstructor` | 4 líneas de constructor |
| `controller/HomeController.java` | `@RequiredArgsConstructor` | 3 líneas de constructor |
| `controller/CategoryApiController.java` | `@RequiredArgsConstructor` | 3 líneas de constructor |
| `controller/LinkApiController.java` | `@RequiredArgsConstructor` | 3 líneas de constructor |
| `controller/ExportImportController.java` | `@RequiredArgsConstructor` | 6 líneas de constructor |

### Clases sin cambios necesarios
- `config/WebConfig.java` — vacía, no se beneficia
- `MyHomeWebApplication.java` — solo método main, no se beneficia
- `repository/*.java` — son interfaces, Lombok no aplica

## Flujo actual

### Modelos (JPA Entities)
- `Category` y `Link` usan `private String id` generado con `UUID.randomUUID().toString()` en constructores
- Dos constructores cada uno: uno genera UUID, otro recibe todos los campos (usado en imports)
- Getters/setters manuales para todos los campos
- `Link` tiene un getter computado `getDomain()` que extrae el host de la URL

### DTOs
- `CategoryDTO.fromEntity(Category)` y `LinkDTO.fromEntity(Link)` son factory methods estáticos
- Campos `boolean builtin` (no `isBuiltin`) → getter `isBuiltin()` → Lombok es compatible
- `ExportDTO` tiene inner static classes `CategoryExport` y `LinkExport` con getters/setters manuales

### Servicios y Controladores
- Inyección por constructor con campos `private final`
- Solo un constructor por clase → `@RequiredArgsConstructor` los reemplaza directamente

## Patrones existentes
- Todas las clases siguen el patrón estándar de Spring Boot
- Inyección por constructor (no `@Autowired` en campos)
- Los DTOs usan factory methods estáticos `fromEntity()` en vez de mappers separados

## Detalles de compatibilidad verificados

### `boolean builtin` → `isBuiltin()`
En `CategoryDTO` y `LinkDTO`, el campo se llama `builtin` (sin prefijo `is`). Lombok genera `isBuiltin()` para `boolean builtin`. Coincide exactamente con el getter actual.

### `Boolean isBuiltin` → `getIsBuiltin()`
En `Category` y `Link` (entidades JPA), el campo se llama `isBuiltin`. Lombok genera `getIsBuiltin()` para `Boolean isBuiltin`. Coincide exactamente con el getter actual.

### `Link.getDomain()`
Es un getter computado (no es un campo). Lombok con `@Getter` no entra en conflicto porque `domain` no es un campo de la clase.

### Constructores personalizados
Los constructores que generan UUID se mantienen tal cual. `@NoArgsConstructor` de Lombok reemplaza al constructor vacío manual, pero Lombok genera `public` por defecto, que es lo mismo que hay ahora.

### Anotación `@NoArgsConstructor` en entidades JPA
JPA requiere un constructor sin argumentos. Hibernate puede usar `protected` (vía reflection), pero actualmente es `public`. Dejarlo `public` con Lombok mantiene compatibilidad.

## Preguntas / Decisiones

- **¿Añadir `@AllArgsConstructor` a los DTOs?** → No necesario. Los DTOs se crean con `new DTO()` y se pueblan campo a campo en `fromEntity()`. Añadir `@AllArgsConstructor` sería ruido que no se usa.
- **¿Añadir `@Builder` a las entidades?** → No necesario ahora. Los constructores actuales son simples y funcionales. Se puede reevaluar si se añaden más campos en el futuro.
- **¿Quitar setters de entidades JPA?** → No. Aunque algunos consideran mala práctica setters públicos en entidades, el código actual ya los expone. Reducir visibilidad sería un cambio funcional fuera del scope de esta migración.
- **¿Lombok 1.18.24 con Spring Boot 3.4.4?** → Compatible sin problemas conocidos.
