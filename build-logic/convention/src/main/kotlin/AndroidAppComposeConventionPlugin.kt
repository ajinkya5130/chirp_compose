import com.android.build.api.dsl.ApplicationExtension
import com.plcoding.chirp.convention.configureComposeAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidAppComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.plcoding.convention.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<ApplicationExtension> {
                configureComposeAndroid(this)
            }

            /*
                        //Or you can mention like below also both are same
                        val extension = extensions.getByType<ApplicationExtension>()
                        configureComposeAndroid(extension)
            */

        }
    }

}