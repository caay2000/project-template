package com.github.caay2000

import com.github.caay2000.configuration.Dependencies
import com.github.caay2000.configuration.ShutdownHookConfiguration
import com.github.caay2000.configuration.StartupHookConfiguration
import com.github.caay2000.configuration.configureRouting
import com.github.caay2000.configuration.configureSerialization
import com.github.caay2000.configuration.requestLoggingConfiguration
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.main() {
    module()
}

fun Application.module() {
    configureApplication(Dependencies())
}

fun Application.configureApplication(dependencies: Dependencies) {
    install(StartupHookConfiguration)
    install(ShutdownHookConfiguration)
    configureRouting(dependencies)

    requestLoggingConfiguration()
    configureSerialization()
}
