package com.github.caay2000.context.mother

import com.github.caay2000.context.domain.AccountId
import java.util.UUID

object AccountIdMother {
    fun random(): AccountId = AccountId(UUID.randomUUID())
}
