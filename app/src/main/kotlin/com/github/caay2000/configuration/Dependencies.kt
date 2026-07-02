package com.github.caay2000.configuration

import com.github.caay2000.common.date.provider.DateProvider
import com.github.caay2000.common.date.provider.LocalDateProvider
import com.github.caay2000.common.event.SyncDomainEventBus
import com.github.caay2000.common.event.subscribe
import com.github.caay2000.common.eventbus.EventBus
import com.github.caay2000.common.idgenerator.IdGenerator
import com.github.caay2000.common.idgenerator.UUIDGenerator
import com.github.caay2000.context.application.create.CreateAccountCommandHandler
import com.github.caay2000.context.application.find.FindAccountByIdQueryHandler
import com.github.caay2000.context.application.print.LogAccountInfoCommandHandler
import com.github.caay2000.context.domain.AccountRepository
import com.github.caay2000.context.primaryadapter.event.LogAccountInfoOnLoanAccountCreatedEventSubscriber
import com.github.caay2000.context.primaryadapter.http.CreateAccountController
import com.github.caay2000.context.primaryadapter.http.FindAccountController
import com.github.caay2000.context.secondaryadapter.database.InMemoryAccountRepository
import com.github.caay2000.memorydb.InMemoryDatasource

class Dependencies(
    val idGenerator: IdGenerator = UUIDGenerator(),
    val dateProvider: DateProvider = LocalDateProvider(),
    val accountRepository: AccountRepository = InMemoryAccountRepository(InMemoryDatasource()),
    eventBus: EventBus = EventBus(),
) {
    private val domainEventBus = SyncDomainEventBus(eventBus)

    private val createAccountCommandHandler = CreateAccountCommandHandler(accountRepository, domainEventBus)
    private val findAccountByIdQueryHandler = FindAccountByIdQueryHandler(accountRepository)
    private val logAccountInfoCommandHandler = LogAccountInfoCommandHandler(accountRepository)

    val createAccountController = CreateAccountController(idGenerator, dateProvider, createAccountCommandHandler, findAccountByIdQueryHandler)
    val findAccountController = FindAccountController(findAccountByIdQueryHandler)

    init {
        domainEventBus.subscribe(LogAccountInfoOnLoanAccountCreatedEventSubscriber(logAccountInfoCommandHandler))
    }
}
