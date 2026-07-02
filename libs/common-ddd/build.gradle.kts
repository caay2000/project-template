plugins {
    id("project-library")
}

dependencies {

    api(project(":libs:common-http"))
    api(project(":libs:lib-event-bus"))

    implementation("io.github.microutils:kotlin-logging-jvm")
}
