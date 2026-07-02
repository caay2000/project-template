package com.github.caay2000.context.domain

class AccountCreationService(private val accountRepository: AccountRepository) {
    fun create(request: CreateAccountRequest): Account {
        guardIdentityNumberIsNotRepeated(request.identityNumber)
        guardEmailIsNotRepeated(request.email)
        guardPhoneIsNotRepeated(request.phonePrefix, request.phoneNumber)
        return Account.create(request)
    }

    private fun guardIdentityNumberIsNotRepeated(identityNumber: IdentityNumber) {
        val exists = accountRepository.findBy(FindAccountCriteria.ByIdentityNumber(identityNumber)) != null
        if (exists) throw AccountCreationError.IdentityNumberAlreadyExists(identityNumber)
    }

    private fun guardEmailIsNotRepeated(email: Email) {
        val exists = accountRepository.findBy(FindAccountCriteria.ByEmail(email)) != null
        if (exists) throw AccountCreationError.EmailAlreadyExists(email)
    }

    private fun guardPhoneIsNotRepeated(
        phonePrefix: PhonePrefix,
        phoneNumber: PhoneNumber,
    ) {
        val exists = accountRepository.findBy(FindAccountCriteria.ByPhone(phonePrefix, phoneNumber)) != null
        if (exists) throw AccountCreationError.PhoneAlreadyExists(phonePrefix, phoneNumber)
    }
}

sealed class AccountCreationError : RuntimeException {
    constructor(message: String) : super(message)

    class IdentityNumberAlreadyExists(identityNumber: IdentityNumber) : AccountCreationError("an account with identity number ${identityNumber.value} already exists")

    class EmailAlreadyExists(email: Email) : AccountCreationError("an account with email ${email.value} already exists")

    class PhoneAlreadyExists(phonePrefix: PhonePrefix, phoneNumber: PhoneNumber) :
        AccountCreationError("an account with phone ${phonePrefix.value} ${phoneNumber.value} already exists")
}
