package com.github.caay2000.context.application.print

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

    fun invoke(accountId: AccountId) {
        val account = findAccount(accountId)
        logger.info { jsonMapper.encodeToString(account) }
    }

    private fun findAccount(accountId: AccountId): Account =
        accountRepository.findBy(FindAccountCriteria.ById(accountId))
            ?: throw AccountLoggerError.AccountNotFoundError(accountId)
}

sealed class AccountLoggerError : RuntimeException {
    constructor(message: String) : super(message)

    class AccountNotFoundError(accountId: AccountId) : AccountLoggerError("account ${accountId.value}} not found")
}
