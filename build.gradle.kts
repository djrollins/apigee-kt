import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
	idea
	application
	kotlin("jvm") version "1.3.41"
}

group = "com.djrollins"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib"))
}

application {
	mainClassName = "com.djrollins.apigeekt.MainKt"
}

tasks.withType(KotlinCompile::class.java) {
	(kotlinOptions as KotlinJvmOptions).apply {
		jvmTarget = JavaVersion.VERSION_1_8.toString()
	}
}