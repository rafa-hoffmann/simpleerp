plugins {
    id("simpleerp.android.library")
    id("simpleerp.android.hilt")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.sonder.simpleerp.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}
