dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.kafka:spring-kafka")

    implementation(project(":supports:snowflake"))
    implementation(project(":supports:event"))
}
