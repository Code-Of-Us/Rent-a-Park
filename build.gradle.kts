plugins {
    java
    jacoco

    id("org.springframework.boot") version "2.7.6"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.2.0"
}

jacoco {
    toolVersion = "0.8.8"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

val mapstructVersion = "1.4.2."
val jdbcVersion = "6.0.4"
val lombokVersion = "1.18.22"
val testContainersVersion = "1.17.6"
val jupiterVersion = "5.9.0"
val springCloudVersion = "2021.0.3"
val kafkaSchemaRegistryVersion = "7.0.1"
val avroVersion = "1.11.0"
val avroSerializerVersion = "5.2.1"
val awaitilityVersion = "4.2.0"
val kafkaTestContainerVersion = "1.17.6"

repositories {
    mavenCentral()
    maven { url = uri("https://artifactory-oss.prod.netflix.net/artifactory/maven-oss-candidates") }
    maven { url = uri("https://packages.confluent.io/maven/") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.projectlombok:lombok:$lombokVersion")
    implementation("org.mapstruct:mapstruct:${mapstructVersion}Final")
    implementation("org.mapstruct:mapstruct-processor:${mapstructVersion}Final")
    implementation("org.springframework:spring-jdbc:$jdbcVersion")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")

    implementation("org.springframework.kafka:spring-kafka")
    implementation("io.confluent:kafka-schema-registry-client:$kafkaSchemaRegistryVersion")
    implementation("org.apache.avro:avro:$avroVersion")
    implementation("io.confluent:kafka-avro-serializer:$avroSerializerVersion")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:postgresql:$testContainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testImplementation("org.awaitility:awaitility:$awaitilityVersion")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.testcontainers:kafka:$kafkaTestContainerVersion")

    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}Final")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
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

val generateAvro = tasks.register("generateAvro", com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask::class) {
    source("src/main/avro")
    setOutputDir(file("$buildDir/generated-sources/avro"))
}

tasks.named("compileJava") {
    dependsOn(generateAvro)
}