package com.github.caay2000.context.domain

interface AccountRepository {
    fun save(account: Account)

    fun searchAll(): List<Account>

    fun findBy(criteria: FindAccountCriteria): Account?
}

sealed class FindAccountCriteria {
    class ById(val id: AccountId) : FindAccountCriteria()

    class ByIdentityNumber(val identityNumber: IdentityNumber) : FindAccountCriteria()

    class ByEmail(val email: Email) : FindAccountCriteria()

    class ByPhone(val phonePrefix: PhonePrefix, val phoneNumber: PhoneNumber) : FindAccountCriteria()
}
