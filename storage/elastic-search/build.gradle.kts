plugins {
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation("org.springframework.data:spring-data-elasticsearch:${property("elasticSearchVersion")}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springframework.boot:spring-boot-starter-web")
}
