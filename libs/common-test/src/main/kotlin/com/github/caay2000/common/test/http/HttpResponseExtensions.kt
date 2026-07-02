package com.github.caay2000.common.test.http

import com.github.caay2000.common.http.ErrorResponseDocument
import com.github.caay2000.common.test.json.testJsonMapper
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.decodeFromJsonElement

suspend inline fun <reified T> HttpResponse.toHttpDataResponse(): HttpDataResponse<T> {
    val body = bodyAsText()
    return HttpDataResponse(
        value = body.decodeJsonBodyOrNull(),
        httpResponse = this,
        error = ErrorResponseDocument(body),
    )
}

inline fun <reified T> String.decodeJsonBodyOrNull(): T? =
    try {
        testJsonMapper.decodeFromJsonElement<T>(testJsonMapper.parseToJsonElement(this))
    } catch (e: Exception) {
        null
    }
