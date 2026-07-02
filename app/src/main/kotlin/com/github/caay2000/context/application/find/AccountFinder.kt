package com.github.caay2000.context.application.find

import com.github.caay2000.context.application.AccountRepository
import com.github.caay2000.context.application.FindAccountCriteria
import com.github.caay2000.context.domain.Account
import com.github.caay2000.context.domain.AccountId

class AccountFinder(private val accountRepository: AccountRepository) {
    fun invoke(accountId: AccountId): Account =
        accountRepository.findBy(FindAccountCriteria.ById(accountId))
            ?: throw AccountFinderError.AccountNotFoundError(accountId)
}

sealed class AccountFinderError : RuntimeException {
    constructor(message: String) : super(message)

    class AccountNotFoundError(accountId: AccountId) : AccountFinderError("account $accountId not found")
}
