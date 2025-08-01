/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    `maven-publish`
    application
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("kr.dogfoot:hwpxlib:1.0.5")
    testImplementation("junit:junit:4.11")
}

group = "com.hotbros"
version = "0.1.1"
description = "sejong"
java.sourceCompatibility = JavaVersion.VERSION_11

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

application {
    // 여기에 실제 실행할 메인 클래스의 FQCN(패키지 포함 전체 이름) 지정
    mainClass.set("com.hotbros.sejong.example.HWPXBuilderExample")
}
