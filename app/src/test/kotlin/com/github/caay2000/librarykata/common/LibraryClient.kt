package com.github.caay2000.librarykata.common

import com.github.caay2000.common.http.ErrorResponseDocument
import com.github.caay2000.common.test.http.HttpDataResponse
import com.github.caay2000.librarykata.context.account.domain.AccountId
import com.github.caay2000.librarykata.context.account.domain.Birthdate
import com.github.caay2000.librarykata.context.account.domain.Email
import com.github.caay2000.librarykata.context.account.domain.IdentityNumber
import com.github.caay2000.librarykata.context.account.domain.Name
import com.github.caay2000.librarykata.context.account.domain.PhoneNumber
import com.github.caay2000.librarykata.context.account.domain.PhonePrefix
import com.github.caay2000.librarykata.context.account.domain.Surname
import com.github.caay2000.librarykata.context.account.primaryadapter.http.serialization.AccountDetailsDocument
import com.github.caay2000.librarykata.context.account.primaryadapter.http.serialization.CreateAccountRequestDocument
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
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class LibraryClient {

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
            client.post("/account") {
                setBody(
                    Json.encodeToString(
                        CreateAccountRequestDocument(
                            identityNumber = identityNumber.value,
                            name = name.value,
                            surname = surname.value,
                            birthdate = birthdate.value,
                            email = email.value,
                            phonePrefix = phonePrefix.value,
                            phoneNumber = phoneNumber.value,
                        ),
                    ),
                )
                contentType(ContentType.Application.Json)
            }.toHttpDataResponse()
        }

    context(ApplicationTestBuilder)
    fun findAccount(id: AccountId): HttpDataResponse<AccountDetailsDocument> =
        runBlocking { client.get("/account/${id.value}").toHttpDataResponse() }

    private suspend inline fun <reified T> HttpResponse.toHttpDataResponse(): HttpDataResponse<T> {
        val body = bodyAsText()

        return HttpDataResponse(
            value = decodeJsonBody<T>(body),
            httpResponse = this,
            error = decodeJsonBody<ErrorResponseDocument>(body),
        )
    }

    private inline fun <reified T> decodeJsonBody(body: String): T? =
        try {
            Json.decodeFromJsonElement<T>(Json.parseToJsonElement(body))
        } catch (e: Exception) {
            null
        }
}
