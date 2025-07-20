dependencies {
    api("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.apache.commons:commons-lang3:3.17.0")

    implementation(project(":support:data-serializer"))
    implementation(project(":support:error"))
    implementation(project(":core:core-domain"))
}
