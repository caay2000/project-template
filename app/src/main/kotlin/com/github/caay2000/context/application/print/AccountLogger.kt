package com.github.caay2000.context.application.print

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.github.caay2000.configuration.jsonMapper
import com.github.caay2000.context.application.AccountRepository
import com.github.caay2000.context.application.FindAccountCriteria
import com.github.caay2000.context.domain.Account
import com.github.caay2000.context.domain.AccountId
import kotlinx.serialization.encodeToString
import mu.KLogger
import mu.KotlinLogging

class AccountLogger(private val accountRepository: AccountRepository) {
    private val logger: KLogger = KotlinLogging.logger {}

    fun invoke(accountId: AccountId): Either<AccountLoggerError, Unit> =
        findAccount(accountId)
            .map { account -> account.log() }

    private fun findAccount(accountId: AccountId): Either<AccountLoggerError, Account> =
        accountRepository.findBy(FindAccountCriteria.ById(accountId))?.right()
            ?: AccountLoggerError.AccountNotFoundError(accountId).left()

    private fun Account.log() {
        logger.info { jsonMapper.encodeToString(this) }
    }
}

sealed class AccountLoggerError : RuntimeException {
    constructor(message: String) : super(message)

    class AccountNotFoundError(accountId: AccountId) : AccountLoggerError("account ${accountId.value}} not found")
}
