package com.github.caay2000.context.primaryadapter.http

import com.github.caay2000.common.date.provider.DateProvider
import com.github.caay2000.common.event.DomainEventPublisher
import com.github.caay2000.common.http.Controller
import com.github.caay2000.common.idgenerator.IdGenerator
import com.github.caay2000.common.jsonapi.ServerResponse
import com.github.caay2000.context.application.AccountRepository
import com.github.caay2000.context.application.create.AccountCreatorError
import com.github.caay2000.context.application.create.CreateAccountCommand
import com.github.caay2000.context.application.create.CreateAccountCommandHandler
import com.github.caay2000.context.application.find.FindAccountByIdQuery
import com.github.caay2000.context.application.find.FindAccountByIdQueryHandler
import com.github.caay2000.context.domain.AccountId
import com.github.caay2000.context.primaryadapter.http.serialization.CreateAccountRequestDocument
import com.github.caay2000.context.primaryadapter.http.serialization.toAccountDetailsDocument
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import mu.KLogger
import mu.KotlinLogging
import java.time.LocalDateTime
import java.util.UUID

class CreateAccountController(
    private val idGenerator: IdGenerator,
    private val dateProvider: DateProvider,
    accountRepository: AccountRepository,
    eventPublisher: DomainEventPublisher,
) : Controller {
    override val logger: KLogger = KotlinLogging.logger {}

    private val commandHandler = CreateAccountCommandHandler(accountRepository, eventPublisher)
    private val queryHandler = FindAccountByIdQueryHandler(accountRepository)

    override suspend fun handle(call: ApplicationCall) {
        val request = call.receive<CreateAccountRequestDocument>()
        val accountId = UUID.fromString(idGenerator.generate())
        val registerDate = dateProvider.dateTime()
        commandHandler.invoke(request.toCommand(accountId, registerDate))

        val queryResult = queryHandler.handle(FindAccountByIdQuery(AccountId(accountId)))
        call.respond(HttpStatusCode.Created, queryResult.account.toAccountDetailsDocument())
    }

    override suspend fun handleExceptions(
        call: ApplicationCall,
        e: Exception,
    ) {
        call.serverError {
            when (e) {
                is AccountCreatorError.IdentityNumberAlreadyExists -> ServerResponse(HttpStatusCode.BadRequest, "IdentityNumberAlreadyExists", e.message)
                is AccountCreatorError.EmailAlreadyExists -> ServerResponse(HttpStatusCode.BadRequest, "EmailAlreadyExists", e.message)
                is AccountCreatorError.PhoneAlreadyExists -> ServerResponse(HttpStatusCode.BadRequest, "PhoneAlreadyExists", e.message)
                else -> ServerResponse(HttpStatusCode.InternalServerError, "Unknown Error", e.message)
            }
        }
    }

    private fun CreateAccountRequestDocument.toCommand(
        accountId: UUID,
        registerDate: LocalDateTime,
    ): CreateAccountCommand =
        CreateAccountCommand(
            accountId = accountId,
            identityNumber = identityNumber,
            email = email,
            phoneNumber = phoneNumber,
            phonePrefix = phonePrefix,
            name = name,
            surname = surname,
            birthdate = birthdate,
            registerDate = registerDate,
        )
}
