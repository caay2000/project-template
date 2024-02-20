package com.github.caay2000.common

import com.github.caay2000.common.http.ErrorResponseDocument
import com.github.caay2000.common.test.http.HttpDataResponse
import com.github.caay2000.common.test.json.testJsonMapper
import com.github.caay2000.context.domain.AccountId
import com.github.caay2000.context.domain.Birthdate
import com.github.caay2000.context.domain.Email
import com.github.caay2000.context.domain.IdentityNumber
import com.github.caay2000.context.domain.Name
import com.github.caay2000.context.domain.PhoneNumber
import com.github.caay2000.context.domain.PhonePrefix
import com.github.caay2000.context.domain.Surname
import com.github.caay2000.context.primaryadapter.http.serialization.AccountDetailsDocument
import com.github.caay2000.context.primaryadapter.http.serialization.CreateAccountRequestDocument
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.server.testing.ApplicationTestBuilder
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.decodeFromJsonElement
import mu.KLogger
import mu.KotlinLogging

class LibraryClient {
    private val logger: KLogger = KotlinLogging.logger {}

    context(ApplicationTestBuilder)
    fun createAccount(
        identityNumber: IdentityNumber,
        name: Name,
        surname: Surname,
        birthdate: Birthdate,
        email: Email,
        phonePrefix: PhonePrefix,
        phoneNumber: PhoneNumber,
    ): HttpDataResponse<AccountDetailsDocument> =
        runBlocking {
            val request =
                CreateAccountRequestDocument(
                    identityNumber = identityNumber.value,
                    name = name.value,
                    surname = surname.value,
                    birthdate = birthdate.value,
                    email = email.value,
                    phonePrefix = phonePrefix.value,
                    phoneNumber = phoneNumber.value,
                )
            val jsonRequest = testJsonMapper.encodeToString(request)
            logger.debug { "CreateAccount Request: $jsonRequest" }
            client.post("/account") {
                setBody(jsonRequest)
                contentType(ContentType.Application.Json)
            }.toHttpDataResponse()
        }

    context(ApplicationTestBuilder)
    fun findAccount(id: AccountId): HttpDataResponse<AccountDetailsDocument> = runBlocking { client.get("/account/${id.value}").toHttpDataResponse() }

    private suspend inline fun <reified T> HttpResponse.toHttpDataResponse(): HttpDataResponse<T> {
        val body = bodyAsText()

        return HttpDataResponse(
            value = decodeJsonBody<T>(body),
            httpResponse = this,
            error = ErrorResponseDocument(body),
        )
    }

    private inline fun <reified T> decodeJsonBody(body: String): T? =
        try {
            testJsonMapper.decodeFromJsonElement<T>(testJsonMapper.parseToJsonElement(body))
        } catch (e: Exception) {
            null
        }
}
