package com.github.caay2000.configuration

import com.github.caay2000.common.date.provider.LocalDateProvider
import com.github.caay2000.common.event.AsyncDomainEventBus
import com.github.caay2000.common.event.DomainEventBus
import com.github.caay2000.common.event.init
import com.github.caay2000.common.event.subscribe
import com.github.caay2000.common.eventbus.EventBus
import com.github.caay2000.common.idgenerator.UUIDGenerator
import com.github.caay2000.context.primaryadapter.event.LogAccountInfoOnLoanAccountCreatedEventSubscriber
import com.github.caay2000.context.primaryadapter.http.CreateAccountController
import com.github.caay2000.context.primaryadapter.http.FindAccountController
import com.github.caay2000.context.secondaryadapter.database.InMemoryAccountRepository
import com.github.caay2000.dikt.DiKt
import com.github.caay2000.memorydb.InMemoryDatasource
import io.ktor.server.application.createApplicationPlugin

val DependencyInjectionConfiguration =
    createApplicationPlugin(name = "DependencyInjectionConfiguration") {

        DiKt.register { InMemoryDatasource() }
        DiKt.register { InMemoryAccountRepository(DiKt.bind()) }

        DiKt.register { UUIDGenerator() }
        DiKt.register { LocalDateProvider() }

        DiKt.register { EventBus(numPartitions = 3) }
        DiKt.register { AsyncDomainEventBus(DiKt.bind()) }
        DiKt.get<DomainEventBus>()
            .subscribe(LogAccountInfoOnLoanAccountCreatedEventSubscriber(DiKt.bind()))
            .init()

        DiKt.register { CreateAccountController(DiKt.bind(), DiKt.bind(), DiKt.bind(), DiKt.bind()) }
        DiKt.register { FindAccountController(DiKt.bind()) }
    }
