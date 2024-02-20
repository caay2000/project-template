package com.github.caay2000.context.application.print

import com.github.caay2000.common.arrow.getOrThrow
import com.github.caay2000.common.cqrs.Command
import com.github.caay2000.common.cqrs.CommandHandler
import com.github.caay2000.context.application.AccountRepository
import com.github.caay2000.context.domain.AccountId
import mu.KLogger
import mu.KotlinLogging
import java.util.UUID

class LogAccountInfoCommandHandler(accountRepository: AccountRepository) : CommandHandler<LogAccountInfoCommand> {
    override val logger: KLogger = KotlinLogging.logger {}
    private val accountLogger = AccountLogger(accountRepository)

    override fun handle(command: LogAccountInfoCommand): Unit =
        accountLogger.invoke(AccountId(command.accountId))
            .getOrThrow()
}

data class LogAccountInfoCommand(val accountId: UUID) : Command
