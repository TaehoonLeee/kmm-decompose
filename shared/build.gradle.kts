import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.library")
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

    ios {
        binaries {
            framework {
                baseName = "shared"
                linkerOpts.add("-lsqlite3")
                transitiveExport = true
                export(deps.decompose.decompose)
                export(deps.bundles.mviKotlin)

                when (val target = this.compilation.target.name) {
                    "iosX64" -> {

                    }
                    "iosArm64" -> {

                    }
                    else -> error("Unsupported target: $target")
                }
            }
        }
    }

    cocoapods {
        framework {
            summary = "Some description for the Shared Module"
            homepage = "Link to the Shared Module homepage"
            ios.deploymentTarget = "14.1"
            baseName = "shared"
            linkerOpts.add("-lsqlite3")
            export(deps.decompose.decompose)
            export(deps.bundles.mviKotlin)
            transitiveExport = true
            podfile = project.file("../iosApp/Podfile")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(deps.kotlinx.coroutines)
                implementation(deps.kotlinx.serialization.json)
                api(deps.decompose.decompose)
                implementation(deps.decompose.extension.compose)
                implementation(deps.koin.core)
                implementation(deps.bundles.ktor)
                implementation(deps.bundles.mviKotlin)
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                implementation(deps.bundles.compose)
                implementation(deps.ktor.okHttp)
            }
        }
        val androidTest by getting
        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
                api(deps.decompose.decompose)
                api(deps.bundles.mviKotlin)
                implementation(deps.ktor.ios)
            }
        }
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

dependencies {
    add("composeCompiler", "androidx.compose.compiler:compiler:1.2.0-alpha02")
}

afterEvaluate {
    val composeCompilerJar =
        project
            .configurations
            .getByName("composeCompiler")
            .resolve()
            .firstOrNull()
            ?: throw Exception("Please add \"androidx.compose.compiler:compiler\" (and only that) as a \"composeCompiler\" dependency")

    project.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.freeCompilerArgs += listOf("-Xuse-ir", "-Xplugin=$composeCompilerJar")
    }
}

fun getIosTarget(): String {
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"

    return if (sdkName.startsWith("iphoneos")) "iosArm64" else "iosX64"
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val targetName = getIosTarget()
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from(framework.outputDirectory)
    into(targetDir)
}