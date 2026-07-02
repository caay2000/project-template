package com.github.caay2000.context.domain

class AccountCreationService {
    fun create(
        request: CreateAccountRequest,
        duplicates: AccountDuplicates,
    ): Account {
        guardIdentityNumberIsNotRepeated(request.identityNumber, duplicates.identityNumberExists)
        guardEmailIsNotRepeated(request.email, duplicates.emailExists)
        guardPhoneIsNotRepeated(request.phonePrefix, request.phoneNumber, duplicates.phoneExists)
        return Account.create(request)
    }

    private fun guardIdentityNumberIsNotRepeated(
        identityNumber: IdentityNumber,
        exists: Boolean,
    ) {
        if (exists) throw AccountCreationError.IdentityNumberAlreadyExists(identityNumber)
    }

    private fun guardEmailIsNotRepeated(
        email: Email,
        exists: Boolean,
    ) {
        if (exists) throw AccountCreationError.EmailAlreadyExists(email)
    }

    private fun guardPhoneIsNotRepeated(
        phonePrefix: PhonePrefix,
        phoneNumber: PhoneNumber,
        exists: Boolean,
    ) {
        if (exists) throw AccountCreationError.PhoneAlreadyExists(phonePrefix, phoneNumber)
    }
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
