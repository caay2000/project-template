package com.github.caay2000.librarykata.context.account.primaryadapter.event

import com.github.caay2000.common.event.DomainEventSubscriber
import com.github.caay2000.librarykata.context.account.application.AccountRepository
import com.github.caay2000.librarykata.context.account.application.print.LogAccountInfoCommand
import com.github.caay2000.librarykata.context.account.application.print.LogAccountInfoCommandHandler
import com.github.caay2000.librarykata.events.account.AccountCreatedEvent
import mu.KLogger
import mu.KotlinLogging

class LogAccountInfoOnLoanAccountCreatedEventSubscriber(accountRepository: AccountRepository) : DomainEventSubscriber<AccountCreatedEvent>() {

    override val logger: KLogger = KotlinLogging.logger {}
    private val commandHandler = LogAccountInfoCommandHandler(accountRepository)

    override fun handleEvent(event: AccountCreatedEvent) {
        commandHandler.handle(LogAccountInfoCommand(event.id))
    }
}
