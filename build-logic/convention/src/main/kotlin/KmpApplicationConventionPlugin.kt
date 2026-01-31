import com.android.build.api.dsl.LibraryExtension
import com.plcoding.chirp.convention.configureKotlinAndroid
import com.plcoding.chirp.convention.configureKotlinMultiplatform
import com.plcoding.chirp.convention.libs
import com.plcoding.chirp.convention.pathToResourcePrefix
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class KmpApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                //apply("com.android.kotlin.multiplatform.library")
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.plugin.serialization")
                //apply("com.android.lint")
            }

            configureKotlinMultiplatform()

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                resourcePrefix = this@with.pathToResourcePrefix()

                //required to make debug build app run in ioS simulator
                experimentalProperties["android.experimental.kmp.enableAndroidResources"] = "true"

            }

            dependencies {
                "commonMainImplementation"(libs.findLibrary("kotlinx-serialization-json").get())
                "commonTestImplementation"(libs.findLibrary("kotlin-test").get())
            }
        }

    }
}