package com.github.caay2000.context.primaryadapter.event

import com.github.caay2000.common.event.DomainEventSubscriber
import com.github.caay2000.context.application.print.LogAccountInfoCommand
import com.github.caay2000.context.application.print.LogAccountInfoCommandHandler
import com.github.caay2000.events.AccountCreatedEvent
import mu.KLogger
import mu.KotlinLogging

class LogAccountInfoOnLoanAccountCreatedEventSubscriber(
    private val commandHandler: LogAccountInfoCommandHandler,
) : DomainEventSubscriber<AccountCreatedEvent>() {
    override val logger: KLogger = KotlinLogging.logger {}

    override fun handleEvent(event: AccountCreatedEvent) {
        commandHandler.handle(LogAccountInfoCommand(event.id))
    }
}
