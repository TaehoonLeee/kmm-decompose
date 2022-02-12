plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.appcompat:appcompat:1.4.1")

    implementation(deps.kotlinx.coroutines)
    implementation(deps.koin.core)
    implementation(deps.koin.android)
    implementation(deps.activity.compose)
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.example.decomposesample.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = deps.versions.compose.get()
    }
}