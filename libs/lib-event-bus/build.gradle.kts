plugins {
    id("project-library")
    id("plugin-kotlin-logging")
}

dependencies {
    testImplementation(project(":libs:common-test"))
}
