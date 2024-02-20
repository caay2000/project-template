plugins {
    id("project-common")
    application
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}
