package com.github.caay2000

import com.github.caay2000.configuration.DependencyInjectionConfiguration
import com.github.caay2000.configuration.RoutingConfiguration
import com.github.caay2000.configuration.ShutdownHookConfiguration
import com.github.caay2000.configuration.StartupHookConfiguration
import com.github.caay2000.configuration.configureSerialization
import com.github.caay2000.configuration.requestLoggingConfiguration
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.main() {
    module()
}

fun Application.module() {
    install(StartupHookConfiguration)
    install(ShutdownHookConfiguration)
    install(DependencyInjectionConfiguration)
    install(RoutingConfiguration)

    requestLoggingConfiguration()
    configureSerialization()
}
