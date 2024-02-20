package com.github.caay2000.common.http

import com.github.caay2000.common.jsonapi.InvalidJsonApiException
import com.github.caay2000.common.jsonapi.ServerResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import mu.KLogger

interface Controller {
    val logger: KLogger

    suspend operator fun invoke(call: ApplicationCall) {
        try {
            handle(call)
        } catch (e: Exception) {
            logger.error { e.message }
            handleExceptions(call, e)
        }
    }

    suspend fun handle(call: ApplicationCall)

    suspend fun handleExceptions(
        call: ApplicationCall,
        e: Exception,
    ) {
        val error =
            when (e) {
                is InvalidJsonApiException -> ServerResponse(HttpStatusCode.BadRequest, "InvalidJsonApiException", e.message)
                else -> ServerResponse(HttpStatusCode.InternalServerError, e.message ?: "Unknown Error")
            }
        call.respond(
            error.status,
            ErrorResponseDocument(error.title),
        )
    }

    suspend fun ApplicationCall.serverError(block: () -> ServerResponse) {
        val response = block()
        this.respond(response.status, response.detail ?: "Unknown Error")
    }

    suspend fun ApplicationCall.jsonApiError(block: () -> ServerResponse) {
        val response = block()
        this.respond(response.status, response.jsonApiErrorDocument)
    }
}
