package com.github.caay2000.context.application.create

import com.github.caay2000.common.event.DomainEventPublisher
import com.github.caay2000.context.application.AccountRepository
import com.github.caay2000.context.application.FindAccountCriteria
import com.github.caay2000.context.domain.Account
import com.github.caay2000.context.domain.AccountCreationService
import com.github.caay2000.context.domain.AccountDuplicates
import com.github.caay2000.context.domain.CreateAccountRequest

class AccountCreator(
    private val accountRepository: AccountRepository,
    private val eventPublisher: DomainEventPublisher,
    private val accountCreationService: AccountCreationService = AccountCreationService(),
) {
    fun invoke(request: CreateAccountRequest) {
        val account = accountCreationService.create(request, findDuplicates(request))
        account.save()
        account.publishEvents()
    }

    private fun findDuplicates(request: CreateAccountRequest): AccountDuplicates =
        AccountDuplicates(
            identityNumberExists = accountRepository.findBy(FindAccountCriteria.ByIdentityNumber(request.identityNumber)) != null,
            emailExists = accountRepository.findBy(FindAccountCriteria.ByEmail(request.email)) != null,
            phoneExists = accountRepository.findBy(FindAccountCriteria.ByPhone(request.phonePrefix, request.phoneNumber)) != null,
        )

    private fun Account.save() = accountRepository.save(this)

    private fun Account.publishEvents() = eventPublisher.publish(pullEvents())
}
