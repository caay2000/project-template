package com.github.caay2000.common.test.json

import com.github.caay2000.common.jsonapi.JsonApiResourceAttributes
import com.github.caay2000.common.serialization.LocalDateSerializer
import com.github.caay2000.common.serialization.LocalDateTimeSerializer
import com.github.caay2000.common.serialization.UUIDSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.serializersModuleOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

val testJsonMapper =
    Json {
        @OptIn(ExperimentalSerializationApi::class)
        explicitNulls = false
        encodeDefaults = true
        classDiscriminator = "serializationType"
        prettyPrint = true
        isLenient = true
        val module =
            SerializersModule {
                polymorphic(JsonApiResourceAttributes::class) {
                }
                serializersModuleOf(UUID::class, UUIDSerializer)
                serializersModuleOf(LocalDate::class, LocalDateSerializer)
                serializersModuleOf(LocalDateTime::class, LocalDateTimeSerializer)
            }
        serializersModule = module
    }
