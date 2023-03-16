plugins {
    id("simpleerp.android.library")
    id("simpleerp.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.sonder.simpleerp.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
}
