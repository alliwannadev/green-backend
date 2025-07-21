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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // TODO: 제거 필요!

    implementation(project(":core:core-application"))
    implementation(project(":core:core-domain"))
    implementation(project(":support:error"))

    // Commons-lang3
    implementation("org.apache.commons:commons-lang3:3.17.0")

    // Test
    testImplementation(project(":support:snowflake"))
    testImplementation(project(":support:data-serializer"))
    testImplementation(project(":support:event"))
    testImplementation(project(":support:transactional-outbox"))

    testImplementation(project(":core:core-infra:core-infra-jpa"))

    testImplementation("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.awaitility:awaitility")
}
