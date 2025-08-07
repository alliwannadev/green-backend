plugins {
	java
	id("org.springframework.boot") version "3.4.5" apply false
	id("io.spring.dependency-management") version "1.1.7"
}

group = "alliwannadev"
version = "1.0.2"

val javaVersion = "21"
java.sourceCompatibility = JavaVersion.valueOf("VERSION_${javaVersion}")

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "java-library")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.springframework.boot")

	repositories {
		mavenCentral()
	}

	dependencies {
		implementation("ch.qos.logback:logback-classic")

		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")

		testCompileOnly("org.projectlombok:lombok")
		testAnnotationProcessor("org.projectlombok:lombok")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
		testRuntimeOnly("org.testcontainers:testcontainers:1.20.6")
		testImplementation("org.testcontainers:junit-jupiter:1.20.6")
		testImplementation("org.testcontainers:mysql:1.20.6")
		testImplementation("org.testcontainers:kafka:1.20.6")
	}

	tasks.getByName("bootJar") {
		enabled = false
	}

	tasks.getByName("jar") {
		enabled = true
	}

	java.sourceCompatibility = JavaVersion.valueOf("VERSION_${javaVersion}")

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
