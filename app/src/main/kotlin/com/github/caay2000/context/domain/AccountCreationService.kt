package com.github.caay2000.context.domain

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right

class AccountCreationService {
    fun create(
        request: CreateAccountRequest,
        duplicates: AccountDuplicates,
    ): Either<AccountCreationError, Account> =
        guardIdentityNumberIsNotRepeated(request.identityNumber, duplicates.identityNumberExists)
            .flatMap { guardEmailIsNotRepeated(request.email, duplicates.emailExists) }
            .flatMap { guardPhoneIsNotRepeated(request.phonePrefix, request.phoneNumber, duplicates.phoneExists) }
            .map { Account.create(request) }

    private fun guardIdentityNumberIsNotRepeated(
        identityNumber: IdentityNumber,
        exists: Boolean,
    ): Either<AccountCreationError, Unit> = if (exists) AccountCreationError.IdentityNumberAlreadyExists(identityNumber).left() else Unit.right()

    private fun guardEmailIsNotRepeated(
        email: Email,
        exists: Boolean,
    ): Either<AccountCreationError, Unit> = if (exists) AccountCreationError.EmailAlreadyExists(email).left() else Unit.right()

    private fun guardPhoneIsNotRepeated(
        phonePrefix: PhonePrefix,
        phoneNumber: PhoneNumber,
        exists: Boolean,
    ): Either<AccountCreationError, Unit> = if (exists) AccountCreationError.PhoneAlreadyExists(phonePrefix, phoneNumber).left() else Unit.right()
}

data class AccountDuplicates(
    val identityNumberExists: Boolean,
    val emailExists: Boolean,
    val phoneExists: Boolean,
)

sealed class AccountCreationError : RuntimeException {
    constructor(message: String) : super(message)

    class IdentityNumberAlreadyExists(identityNumber: IdentityNumber) : AccountCreationError("an account with identity number ${identityNumber.value} already exists")

    class EmailAlreadyExists(email: Email) : AccountCreationError("an account with email ${email.value} already exists")

    class PhoneAlreadyExists(phonePrefix: PhonePrefix, phoneNumber: PhoneNumber) :
        AccountCreationError("an account with phone ${phonePrefix.value} ${phoneNumber.value} already exists")
}
