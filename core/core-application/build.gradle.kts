dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.kafka:spring-kafka")

    // Commons-lang3
    implementation("org.apache.commons:commons-lang3:3.17.0")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // P6Spy
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.2")
    
    implementation(project(":support:snowflake"))
    implementation(project(":support:data-serializer"))
    implementation(project(":support:error"))
    implementation(project(":support:event"))
    implementation(project(":support:transactional-outbox"))
    implementation(project(":support:distributed-lock"))
    implementation(project(":core:core-domain"))
    implementation(project(":core:core-infra:core-infra-jpa"))
    implementation(project(":core:core-infra:core-infra-redis"))
}
