plugins {
    java
    jacoco

    id("org.springframework.boot") version "2.7.9"
    id("io.spring.dependency-management") version "1.1.0"

}

jacoco {
    toolVersion = "0.8.8"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.projectlombok:lombok:1.18.22")
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    implementation("org.mapstruct:mapstruct-processor:1.4.2.Final")
    implementation("org.springframework:spring-jdbc:6.0.4")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required.set(true)
        csv.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}