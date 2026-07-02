package com.github.caay2000.context.application.create

import arrow.core.Either
import com.github.caay2000.common.event.DomainEventPublisher
import com.github.caay2000.context.application.AccountRepository
import com.github.caay2000.context.application.FindAccountCriteria
import com.github.caay2000.context.domain.Account
import com.github.caay2000.context.domain.AccountCreationError
import com.github.caay2000.context.domain.AccountCreationService
import com.github.caay2000.context.domain.AccountDuplicates
import com.github.caay2000.context.domain.CreateAccountRequest

class AccountCreator(
    private val accountRepository: AccountRepository,
    private val eventPublisher: DomainEventPublisher,
    private val accountCreationService: AccountCreationService = AccountCreationService(),
) {
    fun invoke(request: CreateAccountRequest): Either<AccountCreationError, Unit> =
        accountCreationService.create(request, findDuplicates(request))
            .map { account -> account.save() }
            .map { account -> account.publishEvents() }

    private fun findDuplicates(request: CreateAccountRequest): AccountDuplicates =
        AccountDuplicates(
            identityNumberExists = accountRepository.findBy(FindAccountCriteria.ByIdentityNumber(request.identityNumber)) != null,
            emailExists = accountRepository.findBy(FindAccountCriteria.ByEmail(request.email)) != null,
            phoneExists = accountRepository.findBy(FindAccountCriteria.ByPhone(request.phonePrefix, request.phoneNumber)) != null,
        )

    private fun Account.save(): Account = accountRepository.save(this).let { this }

    private fun Account.publishEvents(): Unit = eventPublisher.publish(pullEvents())
}
