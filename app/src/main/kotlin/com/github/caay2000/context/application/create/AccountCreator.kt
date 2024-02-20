package com.github.caay2000.context.application.create

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.github.caay2000.common.event.DomainEventPublisher
import com.github.caay2000.context.application.AccountRepository
import com.github.caay2000.context.application.FindAccountCriteria
import com.github.caay2000.context.domain.Account
import com.github.caay2000.context.domain.CreateAccountRequest
import com.github.caay2000.context.domain.Email
import com.github.caay2000.context.domain.IdentityNumber
import com.github.caay2000.context.domain.PhoneNumber
import com.github.caay2000.context.domain.PhonePrefix

class AccountCreator(
    private val accountRepository: AccountRepository,
    private val eventPublisher: DomainEventPublisher,
) {
    fun invoke(request: CreateAccountRequest): Either<AccountCreatorError, Unit> =
        guardIdentityNumberIsNotRepeated(request.identityNumber)
            .flatMap { guardEmailIsNotRepeated(request.email) }
            .flatMap { guardPhoneIsNotRepeated(request.phonePrefix, request.phoneNumber) }
            .map { Account.create(request) }
            .map { account -> account.save() }
            .map { account -> account.publishEvents() }

    private fun guardIdentityNumberIsNotRepeated(identityNumber: IdentityNumber): Either<AccountCreatorError, Unit> =
        accountRepository.findBy(FindAccountCriteria.ByIdentityNumber(identityNumber))
            ?.let { AccountCreatorError.IdentityNumberAlreadyExists(identityNumber).left() }
            ?: Unit.right()

    private fun guardEmailIsNotRepeated(email: Email): Either<AccountCreatorError, Unit> =
        accountRepository.findBy(FindAccountCriteria.ByEmail(email))
            ?.let { AccountCreatorError.EmailAlreadyExists(email).left() }
            ?: Unit.right()

    private fun guardPhoneIsNotRepeated(
        phonePrefix: PhonePrefix,
        phoneNumber: PhoneNumber,
    ): Either<AccountCreatorError, Unit> =
        accountRepository.findBy(FindAccountCriteria.ByPhone(phonePrefix, phoneNumber))
            ?.let { AccountCreatorError.PhoneAlreadyExists(phonePrefix, phoneNumber).left() }
            ?: Unit.right()

    private fun Account.save(): Account = accountRepository.save(this).let { this }

    private fun Account.publishEvents(): Unit = eventPublisher.publish(pullEvents())
}

sealed class AccountCreatorError : RuntimeException {
    constructor(message: String) : super(message)

    class IdentityNumberAlreadyExists(identityNumber: IdentityNumber) : AccountCreatorError("an account with identity number ${identityNumber.value} already exists")

    class EmailAlreadyExists(email: Email) : AccountCreatorError("an account with email ${email.value} already exists")

    class PhoneAlreadyExists(phonePrefix: PhonePrefix, phoneNumber: PhoneNumber) :
        AccountCreatorError("an account with phone ${phonePrefix.value} ${phoneNumber.value} already exists")
}
