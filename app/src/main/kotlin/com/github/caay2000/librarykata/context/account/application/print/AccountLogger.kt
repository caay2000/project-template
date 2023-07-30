package com.github.caay2000.librarykata.context.account.application.print

import arrow.core.Either
import arrow.core.flatMap
import com.github.caay2000.common.database.RepositoryError
import com.github.caay2000.librarykata.context.account.application.AccountRepository
import com.github.caay2000.librarykata.context.account.application.FindAccountCriteria
import com.github.caay2000.librarykata.context.account.domain.Account
import com.github.caay2000.librarykata.context.account.domain.AccountId
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KLogger
import mu.KotlinLogging

class AccountLogger(private val accountRepository: AccountRepository) {

    private val logger: KLogger = KotlinLogging.logger {}

    fun invoke(accountId: AccountId): Either<AccountLoggerError, Unit> =
        findAccount(accountId)
            .flatMap { account -> account.log() }

    private fun findAccount(accountId: AccountId): Either<AccountLoggerError, Account> =
        accountRepository.findBy(FindAccountCriteria.ById(accountId))
            .mapLeft { error ->
                when (error) {
                    is RepositoryError.NotFoundError -> AccountLoggerError.AccountNotFoundError(accountId)
                    else -> AccountLoggerError.UnknownError(error)
                }
            }

    private fun Account.log(): Either<AccountLoggerError, Unit> =
        Either.catch {
            logger.info { Json.encodeToString(this) }
        }.mapLeft { AccountLoggerError.UnknownError(it) }
}

sealed class AccountLoggerError : RuntimeException {
    constructor(message: String) : super(message)
    constructor(throwable: Throwable) : super(throwable)

    class UnknownError(error: Throwable) : AccountLoggerError(error)
    class AccountNotFoundError(accountId: AccountId) : AccountLoggerError("account ${accountId.value}} not found")
}
