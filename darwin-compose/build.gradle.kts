plugins {
	kotlin("multiplatform")
	id("org.jetbrains.compose")
}

kotlin {
	iosX64("uikitX64") {
		binaries {
			executable {
				entryPoint = "main"
				freeCompilerArgs += listOf(
					"-linker-option", "-framework", "-linker-option", "Metal",
					"-linker-option", "-framework", "-linker-option", "CoreText",
					"-linker-option", "-framework", "-linker-option", "CoreGraphics"
				)
			}
		}
	}

	sourceSets {
		commonMain {
			dependencies {
				implementation(project(mapOf("path" to ":shared")))
				implementation(project(mapOf("path" to ":ui-compose")))
				implementation(compose.ui)
				implementation(compose.runtime)
				implementation(compose.material)
				implementation(compose.foundation)
			}
		}
	}
}