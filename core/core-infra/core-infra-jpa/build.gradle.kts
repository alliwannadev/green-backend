val querydslVersion = "5.1.0"

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    // Querydsl
    api("com.querydsl:querydsl-jpa:${querydslVersion}:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    // Commons-lang3
    implementation("org.apache.commons:commons-lang3:3.17.0")

    implementation(project(":support:snowflake"))
    implementation(project(":support:error"))
    implementation(project(":core:core-domain"))
}

// QueryDSL Build 옵션
val querydslDir = "src/main/generated"

sourceSets {
    main {
        java.srcDirs(querydslDir)
    }
}

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(file(querydslDir))
}

tasks.named("clean") {
    doLast {
        file(querydslDir).deleteRecursively()
    }
}
