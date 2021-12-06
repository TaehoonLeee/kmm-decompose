buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        val deps = project.extensions.getByType<VersionCatalogsExtension>().named("deps") as org.gradle.accessors.dm.LibrariesForDeps
        classpath(deps.android.gradle.plugin)
        classpath(deps.kotlin.gradle.plugin)
        classpath(deps.kotlinx.serialization.plugin)
        classpath(deps.sqldelight.gradle.plugin)
        classpath(deps.google.service.plugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}