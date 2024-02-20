package com.github.caay2000.context.application

import com.github.caay2000.context.domain.Account
import com.github.caay2000.context.domain.AccountId
import com.github.caay2000.context.domain.Email
import com.github.caay2000.context.domain.IdentityNumber
import com.github.caay2000.context.domain.PhoneNumber
import com.github.caay2000.context.domain.PhonePrefix
import com.github.caay2000.memorydb.Repository

interface AccountRepository : Repository {
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
