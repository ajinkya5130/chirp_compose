import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.convention.cmp.application)
    alias(libs.plugins.compose.hot.reload)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }


    /*
    //for time being commented out , because of build issue
    jvm()*/

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.core.splashscreen)
        }
        commonMain.dependencies {

            implementation(projects.core.data)
            implementation(projects.core.domain)
            implementation(projects.core.presentation)
            implementation(projects.core.designsystem)

            implementation(projects.feature.auth.domain)
            implementation(projects.feature.auth.presentation)

            implementation(libs.jetbrains.compose.navigation)

            implementation(projects.feature.chat.data)
            implementation(projects.feature.chat.domain)
            implementation(projects.feature.chat.presentation)
            implementation(projects.feature.chat.database)


            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.jetbrains.compose.viewmodel)
            implementation(libs.jetbrains.lifecycle.compose)
        }

        /*
        //we can configure later when we build desktop app
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }*/
        iosMain.dependencies {

        }
    }
}

// Note: below complete code we extracted to AndroidApplicationConventionPlugin.kt file
/*android {
    namespace = "com.plcoding.chirp"
    compileSdk = libs.versions.projectCompileSdkVersion.get().toInt()

    defaultConfig {
        applicationId = "com.plcoding.chirp"
        minSdk = libs.versions.projectMinSdkVersion.get().toInt()
        targetSdk = libs.versions.projectTargetSdkVersion.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}*/

/*
//Note: below complete code we extracted to CMPApplicationConventionPlugin.kt file
dependencies {
    debugImplementation(compose.uiTooling)
}*/


/*
//for time being commented out , because of build issue
compose.desktop {
    application {
        mainClass = "com.plcoding.chirp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.plcoding.chirp"
            packageVersion = "1.0.0"
        }
    }
}*/
