plugins {
	kotlin("multiplatform")
	id("com.android.library")
	id("org.jetbrains.compose")
}

kotlin {
	ios()
	android()

	sourceSets {
		commonMain {
			dependencies {
				implementation(project(mapOf("path" to ":shared")))
				implementation(compose.material)
				implementation(compose.foundation)
				implementation(deps.decompose.extension.compose)
			}
		}
	}
}

android {
	compileSdk = 31
	sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")
}