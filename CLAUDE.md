# CLAUDE.md

Este proyecto sigue **Domain-Driven Design** organizado por bounded contexts (`context/<nombre>/...`). Respeta siempre estas capas y normas.

**Regla rápida:** antes de añadir código nuevo, decide primero en qué capa vive — si es una decisión de negocio (aunque necesite consultar el repositorio para tomarla), va en `domain/`; si es orquestación (persistir, publicar eventos, una lectura sin regla de negocio), va en `application/`.

## Capas

### `domain/`
- Entidades, value objects, agregados, **domain services** y las **interfaces de `Repository`** (el repositorio es un patrón táctico de dominio, DDD clásico — el puerto vive aquí, su implementación en `secondaryadapter/`).
- Lógica de negocio: invariantes, validaciones, decisiones, cálculos. Un domain service puede depender de una interfaz `Repository` definida en `domain/` para consultar los datos que necesita para decidir (p.ej. comprobar unicidad) — es el propio domain service quien pregunta lo que necesita, cuando lo necesita, en vez de que `application/` se lo precargue y pase por parámetro.
- **Regla más importante: el dominio nunca persiste ni publica, solo decide.** `Repository` es la única dependencia de I/O permitida en domain, y solo para lectura (`find`/`search`); guardar (`save`) y publicar eventos siguen siendo responsabilidad exclusiva de `application/`. Prohibido cualquier otro I/O: HTTP, ficheros, logging, reloj del sistema, generación de IDs.
- Sin dependencias de frameworks (Ktor, kotlinx.serialization, etc) ni de librerías cuyo nombre delate infraestructura concreta (base de datos, mensajería, etc.) en un import de `domain/`.

### `application/`
- Orquesta casos de uso mediante dos tipos de clase, **siempre separadas**:
  - `CommandHandler`/`QueryHandler`: un traductor delgado por caso de uso. Recibe el `Command`/`Query`, llama al application service correspondiente y devuelve el resultado. No orquesta I/O ni contiene lógica.
  - Application service (`Creator`/`Finder`/`Searcher`/`Logger`...): invoca al domain service/aggregate correspondiente (que puede haber consultado el repositorio por su cuenta para decidir) y luego persiste/publica el resultado (`save`, `eventPublisher.publish`). No precarga datos "por si acaso" el dominio los necesita — eso ya lo resuelve el domain service. No debe contener reglas de negocio propias.
  - No los fusiones en una sola clase: el naming (`Finder` vs `Searcher`, ver abajo) porta significado, y separar el traductor HTTP-independiente de la orquestación mantiene cada pieza testeable por separado.
- Cuando un caso de uso es una lectura pura sin ninguna regla de negocio (p.ej. `AccountFinder`, `AccountLogger`: buscar por id y devolver "not found" si no existe), no hace falta inventar un domain service — el application service llama al repositorio directamente. Un domain service solo aparece cuando hay una decisión de negocio real que tomar.
- **`Find`/`Finder` vs `Search`/`Searcher` (no es cosmético):** `Find`/`Finder` solo cuando el resultado es unario — existe o no existe, como mucho uno (`FindAccountByIdQueryHandler`, `AccountFinder`, lanza "not found" si no aparece). En cuanto el resultado es una lista (0, 1 o N, nunca un 404 por "no encontrado"), es siempre `Search`/`Searcher`.

### `primaryadapter/`
- Adaptadores de entrada: controllers HTTP, subscribers de eventos. Traducen el mundo exterior a comandos/queries de `application/`. Cero lógica de negocio aquí: construyen el `Command`/`Query`, invocan el handler, serializan la respuesta.

### `secondaryadapter/`
- Adaptadores de salida: implementaciones de repositorios y clientes externos, implementando los puertos (interfaces) definidos en `domain/`.

## Errores

- Errores de negocio (invariantes) son `sealed class ... : RuntimeException`, definidos en `domain/` junto al servicio que los lanza. Errores de orquestación (p.ej. "no encontrado") pueden vivir en `application/`.
- Se **lanzan** (`throw`), nunca se envuelven en un tipo de retorno de error. Los mapea a HTTP únicamente el controller (`handleExceptions`), nunca antes.

## Dependency Injection

- Sin framework de DI (nada de anotaciones, classpath scanning ni proxies). El grafo de dependencias se construye a mano en un **composition root**: una clase `Dependencies` con un parámetro con valor por defecto por cada puerto (repositorio, `DateProvider`, `IdGenerator`, `DomainEventPublisher`...), que internamente construye los `CommandHandler`/`QueryHandler` como `private val` y expone los controllers ya montados como `val` públicos.
- `Application.module()` crea **una** instancia de `Dependencies(...)` (solo sobreescribiendo lo que necesita algo real, p.ej. un cliente externo) y la pasa hacia abajo (`configureApplication(dependencies)` → `configureRouting(dependencies)`, que registra las rutas a mano contra `dependencies.xxxController`).
- Los tests construyen su propio `Dependencies(...)` sobreescribiendo por nombre solo los parámetros que les interesan (mocks de tiempo/id, fakes de repositorio), reutilizando el mismo `configureApplication` que producción. Así el grafo entero es legible en un solo fichero y no hay contenedor mágico que depurar.

## Tests

- Nada de god-objects tipo `LibraryClient`/`TestUseCases` que envuelvan todas las llamadas HTTP del proyecto. Cada test usa el `HttpClient` de Ktor **crudo** (`client.get/post/patch(...)`, verbo/ruta/body a la vista).
- El estado previo se siembra **directo en el fake del repositorio** con mothers (`accountRepository.save(AccountMother.random())`), nunca llamando a otro endpoint para prepararlo.
- Mockea tiempo/id (`MockDateProvider`/`MockIdGenerator`) solo donde el endpoint bajo test los genera.
- Estructura por contexto: los tests de caso de uso en la raíz del paquete de test; los **mothers en `mother/`**; los **fakes (dobles del puerto) en `fake/`**.
- Da forma a cada test como Given/When/Then en bloques separados por línea en blanco, sin comentarios marcadores (`// given` etc. sobran si el bloque ya lo dice).
- Asegura la respuesta completa de una vez (`assertResponse(account.toAccountDetailsDocument())`), no campo a campo — el mother ya garantiza consistencia y completitud de lo esperado.

## Otras normas

- Value classes para primitivos con significado de negocio (ver `Account.kt`).
- Los agregados publican domain events con `pushEvent`/`pullEvents`; es el application service quien los publica de verdad con `eventPublisher.publish(...)`.
