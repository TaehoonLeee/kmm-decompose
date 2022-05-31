pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("deps") {
            from(files("deps.version.toml"))
        }
    }
}

rootProject.name = "Decompose_Sample"
include(":androidApp")
include(":shared")
include(":ui-compose")
include(":darwin-compose")
