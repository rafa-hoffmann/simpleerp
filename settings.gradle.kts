pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "simpleerp"
include(":app")
include(":core")
include(":core:common")
include(":core:model")
include(":core:data")
include(":core:database")
include(":feature")
include(":feature:sales")
