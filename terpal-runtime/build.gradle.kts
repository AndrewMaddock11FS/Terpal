plugins {
  id("conventions")
  //id("publish")
  kotlin("multiplatform") version "1.9.22"
}



kotlin {
  jvm {
    jvmToolchain(11)
  }

  linuxX64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        //api(kotlin("reflect"))
        implementation("io.exoquery:decomat-core:3.0.0")
      }
    }

    val commonTest by getting {
      kotlin.srcDir("$buildDir/generated/ksp/metadata/commonMain/kotlin")
      dependencies {
        // Used to ad-hoc some examples but not needed.
        //api("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.2")
        //implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
        implementation(kotlin("test"))
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions{
        freeCompilerArgs = listOf("-Xcontext-receivers")
        // Otherwise will have: Could not resolve io.exoquery:pprint-kotlin:2.0.1.
        // Incompatible because this component declares a component, compatible with Java 11 and the consumer needed a component, compatible with Java 8
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}


dependencies {

}

// Needed for testing
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}