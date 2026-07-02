package com.github.caay2000.context

import com.github.caay2000.common.test.http.assertErrorMessage
import com.github.caay2000.common.test.http.assertResponse
import com.github.caay2000.common.test.http.assertStatus
import com.github.caay2000.common.test.http.toHttpDataResponse
import com.github.caay2000.common.test.json.testJsonMapper
import com.github.caay2000.common.test.mock.MockDateProvider
import com.github.caay2000.common.test.mock.MockIdGenerator
import com.github.caay2000.configuration.Dependencies
import com.github.caay2000.configureApplication
import com.github.caay2000.context.domain.Account
import com.github.caay2000.context.mother.AccountMother
import com.github.caay2000.context.primaryadapter.http.serialization.AccountDetailsDocument
import com.github.caay2000.context.primaryadapter.http.serialization.CreateAccountRequestDocument
import com.github.caay2000.context.primaryadapter.http.serialization.toAccountDetailsDocument
import com.github.caay2000.context.secondaryadapter.database.InMemoryAccountRepository
import com.github.caay2000.memorydb.InMemoryDatasource
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.Test

class CreateAccountControllerTest {
    private val mockIdGenerator = MockIdGenerator()
    private val mockDateProvider = MockDateProvider()
    private val accountRepository = InMemoryAccountRepository(InMemoryDatasource())
    private val dependencies =
        Dependencies(
            idGenerator = mockIdGenerator,
            dateProvider = mockDateProvider,
            accountRepository = accountRepository,
        )

    @Test
    fun `an account can be created`() =
        testApplication {
            application { configureApplication(dependencies) }
            mockIdGenerator.mock(account.id.value)
            mockDateProvider.mock(account.registerDate.value)

            val response =
                client.post("/account") {
                    contentType(ContentType.Application.Json)
                    setBody(testJsonMapper.encodeToString(account.toCreateAccountRequestDocument()))
                }.toHttpDataResponse<AccountDetailsDocument>()

            response
                .assertStatus(HttpStatusCode.Created)
                .assertResponse(account.toAccountDetailsDocument())
        }

    @Test
    fun `an account can be retrieved`() =
        testApplication {
            application { configureApplication(dependencies) }
            accountRepository.save(account)

            val response = client.get("/account/${account.id.value}").toHttpDataResponse<AccountDetailsDocument>()

            response
                .assertStatus(HttpStatusCode.OK)
                .assertResponse(account.toAccountDetailsDocument())
        }

    @Test
    fun `an account with identityNumber repeated cannot be created`() =
        testApplication {
            application { configureApplication(dependencies) }
            accountRepository.save(account)
            mockIdGenerator.mock(sameIdentityNumberAccount.id.value)
            mockDateProvider.mock(sameIdentityNumberAccount.registerDate.value)

            val response =
                client.post("/account") {
                    contentType(ContentType.Application.Json)
                    setBody(testJsonMapper.encodeToString(sameIdentityNumberAccount.toCreateAccountRequestDocument()))
                }.toHttpDataResponse<AccountDetailsDocument>()

            response
                .assertStatus(HttpStatusCode.BadRequest)
                .assertErrorMessage("an account with identity number ${account.identityNumber.value} already exists")
        }

    @Test
    fun `an account with email repeated cannot be created`() =
        testApplication {
            application { configureApplication(dependencies) }
            accountRepository.save(account)
            mockIdGenerator.mock(sameEmailAccount.id.value)
            mockDateProvider.mock(sameEmailAccount.registerDate.value)

            val response =
                client.post("/account") {
                    contentType(ContentType.Application.Json)
                    setBody(testJsonMapper.encodeToString(sameEmailAccount.toCreateAccountRequestDocument()))
                }.toHttpDataResponse<AccountDetailsDocument>()

            response
                .assertStatus(HttpStatusCode.BadRequest)
                .assertErrorMessage("an account with email ${account.email.value} already exists")
        }

    @Test
    fun `an account with phone repeated cannot be created`() =
        testApplication {
            application { configureApplication(dependencies) }
            accountRepository.save(account)
            mockIdGenerator.mock(samePhoneAccount.id.value)
            mockDateProvider.mock(samePhoneAccount.registerDate.value)

            val response =
                client.post("/account") {
                    contentType(ContentType.Application.Json)
                    setBody(testJsonMapper.encodeToString(samePhoneAccount.toCreateAccountRequestDocument()))
                }.toHttpDataResponse<AccountDetailsDocument>()

            response
                .assertStatus(HttpStatusCode.BadRequest)
                .assertErrorMessage("an account with phone ${account.phonePrefix.value} ${account.phoneNumber.value} already exists")
        }

    private fun Account.toCreateAccountRequestDocument() =
        CreateAccountRequestDocument(
            identityNumber = identityNumber.value,
            name = name.value,
            surname = surname.value,
            birthdate = birthdate.value,
            email = email.value,
            phonePrefix = phonePrefix.value,
            phoneNumber = phoneNumber.value,
        )

    private val account = AccountMother.random()
    private val sameIdentityNumberAccount = AccountMother.random().copy(identityNumber = account.identityNumber)
    private val sameEmailAccount = AccountMother.random().copy(email = account.email)
    private val samePhoneAccount = AccountMother.random().copy(phonePrefix = account.phonePrefix, phoneNumber = account.phoneNumber)
}
