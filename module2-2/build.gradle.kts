plugins {
    id("java")
}

group = "org.oladushek"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.mysql:mysql-connector-j:9.4.0")
    implementation("org.projectlombok:lombok:1.18.34")
    implementation("org.liquibase:liquibase-core:4.33.0")
    testImplementation("org.mockito:mockito-core:5.18.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}