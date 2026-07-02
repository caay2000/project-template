package com.github.caay2000.context.application.find

import com.github.caay2000.context.domain.Account
import com.github.caay2000.context.domain.AccountId
import com.github.caay2000.context.domain.AccountRepository
import com.github.caay2000.context.domain.FindAccountCriteria

class AccountFinder(private val accountRepository: AccountRepository) {
    fun invoke(accountId: AccountId): Account =
        accountRepository.findBy(FindAccountCriteria.ById(accountId))
            ?: throw AccountFinderError.AccountNotFoundError(accountId)
}

sealed class AccountFinderError : RuntimeException {
    constructor(message: String) : super(message)

    class AccountNotFoundError(accountId: AccountId) : AccountFinderError("account $accountId not found")
}
