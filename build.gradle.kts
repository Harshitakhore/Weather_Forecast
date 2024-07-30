plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.vertx:vertx-core:4.5.8")
    implementation("io.vertx:vertx-web:4.5.8")
    implementation("io.vertx:vertx-web-client:4.5.8")
    implementation ("io.vertx:vertx-config:4.4.0") // If you are using configuration management
    implementation ("io.vertx:vertx-config-yaml:4.4.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation ("com.fasterxml.jackson.core:jackson-databind")
    implementation ("com.squareup.retrofit2:retrofit:2.1.0")
    implementation ("com.squareup.retrofit2:converter-jackson:2.1.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")



}

tasks.test {
    useJUnitPlatform()
}