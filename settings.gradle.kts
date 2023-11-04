pluginManagement {
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
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://jcenter.bintray.com") }
        mavenCentral()
    }
}

rootProject.name = "Market_Nookat"
include(":app")
 