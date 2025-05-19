tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")

    implementation(project(":core:core-domain"))
    implementation(project(":supports:snowflake"))
    implementation(project(":supports:event"))
}
