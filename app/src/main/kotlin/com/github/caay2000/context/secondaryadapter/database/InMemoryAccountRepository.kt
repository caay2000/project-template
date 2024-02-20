package com.github.caay2000.context.secondaryadapter.database

import com.github.caay2000.context.application.AccountRepository
import com.github.caay2000.context.application.FindAccountCriteria
import com.github.caay2000.context.domain.Account
import com.github.caay2000.memorydb.InMemoryDatasource

class InMemoryAccountRepository(private val datasource: InMemoryDatasource) : AccountRepository {
    companion object {
        private const val TABLE_NAME = "account.account"
    }

    override fun save(account: Account) {
        datasource.save(TABLE_NAME, account.id.toString(), account)
    }

    override fun searchAll(): List<Account> = datasource.getAll(TABLE_NAME)

    override fun findBy(criteria: FindAccountCriteria): Account? =
        when (criteria) {
            is FindAccountCriteria.ById -> datasource.getById<Account>(TABLE_NAME, criteria.id.toString())
            is FindAccountCriteria.ByIdentityNumber -> datasource.getAll<Account>(TABLE_NAME).firstOrNull { it.identityNumber == criteria.identityNumber }
            is FindAccountCriteria.ByEmail -> datasource.getAll<Account>(TABLE_NAME).firstOrNull { it.email == criteria.email }
            is FindAccountCriteria.ByPhone -> datasource.getAll<Account>(TABLE_NAME).firstOrNull { it.phonePrefix == criteria.phonePrefix && it.phoneNumber == criteria.phoneNumber }
        }
}
