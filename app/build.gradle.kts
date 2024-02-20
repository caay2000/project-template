plugins {
    id("project-application")
    id("project-context")
    id("plugin-kotlin-logging")
    id("plugin-kotlin-serialization")
}
dependencies {
    implementation(project(":libs:common-arrow"))
    implementation(project(":libs:common-cqrs"))
    implementation(project(":libs:common-date"))
    implementation(project(":libs:common-http"))
    implementation(project(":libs:common-ddd"))
    implementation(project(":libs:common-id-generator"))
    implementation(project(":libs:lib-dependency-injection"))
    implementation(project(":libs:lib-memory-database"))
    implementation(project(":libs:lib-event-bus"))

    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-call-logging")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-call-id-jvm")
    implementation("io.ktor:ktor-server-double-receive")

    testImplementation(project(":libs:common-test"))
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
    kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
}
