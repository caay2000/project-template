package com.github.caay2000.context.application.find

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.github.caay2000.context.application.AccountRepository
import com.github.caay2000.context.application.FindAccountCriteria
import com.github.caay2000.context.domain.Account
import com.github.caay2000.context.domain.AccountId

class AccountFinder(private val accountRepository: AccountRepository) {
    fun invoke(accountId: AccountId): Either<AccountFinderError, Account> =
        accountRepository.findBy(FindAccountCriteria.ById(accountId))?.right()
            ?: AccountFinderError.AccountNotFoundError(accountId).left()
}

sealed class AccountFinderError : RuntimeException {
    constructor(message: String) : super(message)

    class AccountNotFoundError(accountId: AccountId) : AccountFinderError("account $accountId not found")
}
