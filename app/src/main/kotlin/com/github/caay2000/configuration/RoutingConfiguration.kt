package com.github.caay2000.configuration

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureRouting(dependencies: Dependencies) {
    routing {
        post("/account") { dependencies.createAccountController.invoke(call) }
        get("/account/{id}") { dependencies.findAccountController.invoke(call) }
    }
}
