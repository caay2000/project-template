# CLAUDE.md

Este proyecto sigue **Domain-Driven Design** organizado por bounded contexts (`context/<nombre>/...`). Respeta siempre estas capas y normas.

## Capas

### `domain/`
- Entidades, value objects, agregados y **domain services**.
- Solo lógica de negocio pura: invariantes, validaciones, decisiones, cálculos.
- **Regla más importante: prohibido cualquier I/O en esta capa.** Nada de repositorios, HTTP, ficheros, logging, reloj del sistema ni generación de IDs. Un domain service recibe como parámetros los datos que necesita (ya obtenidos por la capa de aplicación) y **lanza** su sealed exception si detecta un invariante roto; nunca va a buscar datos él mismo ni devuelve un `Either`.
- Sin dependencias de frameworks (Ktor, kotlinx.serialization, etc).

### `application/`
- Orquesta casos de uso mediante dos tipos de clase, **siempre separadas**:
  - `CommandHandler`/`QueryHandler`: un traductor delgado por caso de uso. Recibe el `Command`/`Query`, llama al application service correspondiente y devuelve el resultado. No orquesta I/O ni contiene lógica.
  - Application service (`Creator`/`Finder`/`Searcher`/`Logger`...): aquí SÍ vive el I/O — llamadas a repositorios (a través de sus interfaces), publicación de eventos, etc. Su trabajo es: obtener los datos necesarios (I/O) → delegar la decisión de negocio al domain service correspondiente → persistir/publicar el resultado (I/O). No debe contener reglas de negocio propias.
  - No los fusiones en una sola clase: el naming (`Finder` vs `Searcher`, ver abajo) porta significado, y separar el traductor HTTP-independiente de la orquestación mantiene cada pieza testeable por separado.
- **`Find`/`Finder` vs `Search`/`Searcher` (no es cosmético):** `Find`/`Finder` solo cuando el resultado es unario — existe o no existe, como mucho uno (`FindAccountByIdQueryHandler`, `AccountFinder`, lanza "not found" si no aparece). En cuanto el resultado es una lista (0, 1 o N, nunca un 404 por "no encontrado"), es siempre `Search`/`Searcher`.

### `primaryadapter/`
- Adaptadores de entrada: controllers HTTP, subscribers de eventos. Traducen el mundo exterior a comandos/queries de `application/`. Cero lógica de negocio aquí: construyen el `Command`/`Query`, invocan el handler, serializan la respuesta.

### `secondaryadapter/`
- Adaptadores de salida: implementaciones de repositorios y clientes externos, implementando los puertos (interfaces) definidos en `application/`.

## Errores

- Errores de negocio (invariantes) son `sealed class ... : RuntimeException`, definidos en `domain/` junto al servicio que los lanza. Errores de orquestación (p.ej. "no encontrado") pueden vivir en `application/`.
- Se **lanzan** (`throw`), no se envuelven en `Either`. Los mapea a HTTP únicamente el controller (`handleExceptions`), nunca antes.
- Nada de Arrow/`Either` en este proyecto — ni como dependencia. Un `throw` + `catch` en el borde HTTP es más simple que envolver cada paso en `Either` para desenvolverlo una línea después.

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
- CQRS: separa comandos (mutan estado) de queries (leen estado) usando `CommandHandler`/`QueryHandler`.
- Antes de añadir código nuevo, decide primero en qué capa vive: si necesita I/O, es `application/`; si es una regla de negocio, es `domain/`.
