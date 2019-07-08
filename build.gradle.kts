plugins {
	idea
	kotlin("jvm") version "1.3.41"
	application
}

group = "com.djrollins"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib"))
}

application {
	mainClassName = "com.djrollins.apigeekt.MainKt"
}
