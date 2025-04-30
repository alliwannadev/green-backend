dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    // P6Spy
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.2")

    implementation(project(":supports:snowflake"))
}
