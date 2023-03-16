import com.sonder.simpleerp.SerpBuildType

plugins {
    id("simpleerp.android.application")
    id("simpleerp.android.hilt")
}

android {
    defaultConfig {
        applicationId = "com.sonder.simpleerp"
        versionCode = 1
        versionName = "0.0.1"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = SerpBuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = SerpBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    viewBinding {
        enable = true
    }

    namespace = "com.sonder.simpleerp"
}

dependencies {
    implementation(project(":feature:sales"))

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    androidTestImplementation(kotlin("test"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.material)
}
