plugins {
	kotlin("multiplatform")
	id("org.jetbrains.compose")
}

kotlin {
	iosX64("uikitX64") {
		binaries {
			executable {
				entryPoint = "com.example.decomposesample.main"
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
				implementation(deps.ktor.darwin)
				implementation(compose.material)
				implementation(compose.foundation)
			}
		}
	}
}

kotlin.targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
	binaries.all {
		binaryOptions["memoryModel"] = "experimental"
		binaryOptions["freezing"] = "disabled"
	}
}

compose.experimental {
	uikit.application {
		bundleIdPrefix = "com.example"
		projectName = "DecomposeSample"

		deployConfigurations {
			simulator("IPhone12Pro") {
				device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPHONE_12_PRO
			}
		}
	}
}

kotlin {
	targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
		binaries.all {
			freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
		}
	}
}