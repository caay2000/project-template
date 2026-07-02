package com.github.caay2000.context.application.create

import com.github.caay2000.common.event.DomainEventPublisher
import com.github.caay2000.context.domain.Account
import com.github.caay2000.context.domain.AccountCreationService
import com.github.caay2000.context.domain.AccountRepository
import com.github.caay2000.context.domain.CreateAccountRequest

class AccountCreator(
    private val accountRepository: AccountRepository,
    private val eventPublisher: DomainEventPublisher,
    private val accountCreationService: AccountCreationService = AccountCreationService(accountRepository),
) {
    fun invoke(request: CreateAccountRequest) {
        val account = accountCreationService.create(request)
        account.save()
        account.publishEvents()
    }

    private fun Account.save() = accountRepository.save(this)

    private fun Account.publishEvents() = eventPublisher.publish(pullEvents())
}
