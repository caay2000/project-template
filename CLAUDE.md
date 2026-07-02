# CLAUDE.md

Este proyecto sigue **Domain-Driven Design** organizado por bounded contexts (`context/<nombre>/...`). Respeta siempre estas capas y normas.

## Capas

### `domain/`
- Entidades, value objects, agregados y **domain services**.
- Solo lógica de negocio pura: invariantes, validaciones, decisiones, cálculos.
- **Regla más importante: prohibido cualquier I/O en esta capa.** Nada de repositorios, HTTP, ficheros, logging, reloj del sistema ni generación de IDs. Un domain service recibe como parámetros los datos que necesita (ya obtenidos por la capa de aplicación) y devuelve una decisión (normalmente `Either<Error, T>`); nunca va a buscarlos él mismo.
- Sin dependencias de frameworks (Ktor, kotlinx.serialization, etc).

### `application/`
- Orquesta casos de uso: Command/Query Handlers y application services (p.ej. `AccountCreator`).
- Aquí SÍ vive el I/O: llamadas a repositorios (a través de sus interfaces), publicación de eventos, etc.
- Su trabajo es: obtener los datos necesarios (I/O) → delegar la decisión de negocio al domain service correspondiente → persistir/publicar el resultado (I/O). No debe contener reglas de negocio propias.

### `primaryadapter/`
- Adaptadores de entrada: controllers HTTP, subscribers de eventos. Traducen el mundo exterior a comandos/queries de `application/`.

### `secondaryadapter/`
- Adaptadores de salida: implementaciones de repositorios y clientes externos, implementando los puertos (interfaces) definidos en `application/`.

## Otras normas

- Value classes para primitivos con significado de negocio (ver `Account.kt`).
- Los agregados publican domain events con `pushEvent`/`pullEvents`; es el application service quien los publica de verdad con `eventPublisher.publish(...)`.
- Errores de negocio (invariantes) son sealed classes de `RuntimeException` definidas en `domain/`, junto al servicio que las lanza. Errores de orquestación (p.ej. "no encontrado") pueden vivir en `application/`.
- CQRS: separa comandos (mutan estado) de queries (leen estado) usando `CommandHandler`/`QueryHandler`.
- Antes de añadir código nuevo, decide primero en qué capa vive: si necesita I/O, es `application/`; si es una regla de negocio, es `domain/`.
