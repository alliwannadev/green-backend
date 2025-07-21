dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.kafka:spring-kafka")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation(project(":support:snowflake"))
    implementation(project(":support:event"))
    implementation(project(":support:data-serializer"))
}
