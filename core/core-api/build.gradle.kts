tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation(project(":core:core-domain"))

    // Commons-lang3
    implementation("org.apache.commons:commons-lang3:3.17.0")

    // Test
    testImplementation(project(":supports:snowflake"))
    testImplementation(project(":supports:data-serializer"))
    testImplementation(project(":supports:event"))
    testImplementation(project(":supports:transactional-outbox"))

    testImplementation("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.awaitility:awaitility")
}
