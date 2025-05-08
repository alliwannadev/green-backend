dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.kafka:spring-kafka")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation(project(":supports:snowflake"))
    implementation(project(":supports:event"))
    implementation(project(":supports:data-serializer"))
}
