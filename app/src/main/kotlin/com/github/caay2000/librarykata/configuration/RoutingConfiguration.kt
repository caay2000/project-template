package com.github.caay2000.librarykata.configuration

import com.github.caay2000.dikt.DiKt
import com.github.caay2000.librarykata.context.account.primaryadapter.http.CreateAccountController
import com.github.caay2000.librarykata.context.account.primaryadapter.http.FindAccountController
import io.ktor.server.application.call
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

val RoutingConfiguration = createApplicationPlugin(name = "RoutingConfiguration") {
    application.routing {
        post("/account") { DiKt.get<CreateAccountController>().invoke(this.call) }
        get("/account/{id}") { DiKt.get<FindAccountController>().invoke(this.call) }
    }
}
