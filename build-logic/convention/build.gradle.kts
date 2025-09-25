plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.android.tools.common)

}

group = "com.plcoding.convention.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
    //jvmToolchain(17)
}
tasks {
    validatePlugins {
        enableStricterValidation = true
        //ignoreFailures = true
        failOnWarning = true
    }

}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.plcoding.convention.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"

        }
        register("androidComposeApplication") {
            id = "com.plcoding.convention.android.application.compose"
            implementationClass = "AndroidAppComposeConventionPlugin"

        }
        register("cmpApplication") {
            id = "com.plcoding.convention.cmp.application"
            implementationClass = "CMPApplicationConventionPlugin"

        }
    }
}