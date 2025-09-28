plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.android.tools.common)
    implementation(libs.buildkonfig.gradle.plugin)
    implementation(libs.buildkonfig.compiler)

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

        register("kmpLibrary") {
            id = "com.plcoding.convention.kmp.library"
            implementationClass = "KmpApplicationConventionPlugin"

        }

        register("cmpLibrary") {
            id = "com.plcoding.convention.cmp.library"
            implementationClass = "CmpLibraryConventionPlugin"

        }

        register("cmpFetaure") {
            id = "com.plcoding.convention.cmp.feature"
            implementationClass = "CmpFeatureConventionPlugin"

        }

        register("bildKonfig") {
            id = "com.plcoding.convention.bildKonfig"
            implementationClass = "BuildKonfigConventionPlugin"

        }
    }
}