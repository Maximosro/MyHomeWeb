# Support: lombok-migration

## Incidencias
- **Lombok 1.18.24 incompatible con JDK 21**: al compilar saltó `NoSuchFieldError: JCTree$JCImport does not have member field 'qualid'`. Se actualizó a 1.18.38, que es compatible con JDK 21.

## Casos fuera del scope
- No se añadió `@AllArgsConstructor`, `@Builder` ni `@ToString` — el código actual no las necesita.
- No se modificó la visibilidad de setters en entidades JPA.

## Futuros puntos a tener en cuenta
- Si se añaden más campos a las entidades, considerar `@Builder` para simplificar constructores.
- Si se añade logging, usar `@Slf4j` de Lombok.
