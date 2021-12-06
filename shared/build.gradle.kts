import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("kotlin-parcelize")
}

version = "1.0"

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
        else -> ::iosX64
    }

    iosTarget("ios") {}

    cocoapods {
        framework {
            summary = "Some description for the Shared Module"
            homepage = "Link to the Shared Module homepage"
            ios.deploymentTarget = "14.1"
            baseName = "shared"
            linkerOpts.add("-lsqlite3")
            export(deps.decompose.decompose)
            export(deps.mviKotlin)
            transitiveExport = true
            podfile = project.file("../iosApp/Podfile")
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(deps.kotlinx.coroutines)
                implementation(deps.kotlinx.serialization.json)
                implementation(deps.decompose.decompose)
                implementation(deps.decompose.extension.compose)
                implementation(deps.reaktive)
                implementation(deps.mviKotlin)
                implementation(deps.koin.core)
                api(deps.gitliveFirebase.auth)
            }
        }
        val commonTest by getting
        val androidMain by getting
        val androidTest by getting
        val iosMain by getting
        val iosTest by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}

configurations {
    create("composeCompiler") {
        isCanBeConsumed = false
    }
}

apply(plugin = "com.google.gms.google-services")

//dependencies {
//    add("composeCompiler", "androidx.compose.compiler:compiler:1.1.0-beta02")
//}

//afterEvaluate {
//    val composeCompilerJar =
//        project
//            .configurations
//            .getByName("composeCompiler")
//            .resolve()
//            .firstOrNull()
//            ?: throw Exception("Please add \"androidx.compose.compiler:compiler\" (and only that) as a \"composeCompiler\" dependency")
//
//    project.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions.freeCompilerArgs += listOf("-Xuse-ir", "-Xplugin=$composeCompilerJar")
//    }
//}

//fun getIosTarget(): String {
//    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
//
//    return if (sdkName.startsWith("iphoneos")) "iosArm64" else "iosX64"
//}
//
//val packForXcode by tasks.creating(Sync::class) {
//    group = "build"
//    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
//    val targetName = getIosTarget()
//    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
//    inputs.property("mode", mode)
//    dependsOn(framework.linkTask)
//    val targetDir = File(buildDir, "xcode-frameworks")
//    from(framework.outputDirectory)
//    into(targetDir)
//}