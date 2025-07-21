tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // P6Spy
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.2")

    implementation(project(":support:transactional-outbox"))
}
