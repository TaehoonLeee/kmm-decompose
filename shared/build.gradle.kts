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

    ios {
        binaries {
            framework {
                baseName = "shared"
                linkerOpts.add("-lsqlite3")
                transitiveExport = true
                export(deps.decompose.decompose)
                export(deps.bundles.mviKotlin)
                export(deps.paging)

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
            export(deps.paging)
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
                api(deps.paging)
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
                implementation(deps.ktor.darwin)
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