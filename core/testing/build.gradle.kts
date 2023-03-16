plugins {
    id("simpleerp.android.library")
    id("simpleerp.android.hilt")
}

android {
    namespace = "com.sonder.simpleerp.core.testing"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    api(libs.junit4)
    api(libs.androidx.test.core)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)

    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.runner)
    api(libs.androidx.test.rules)
    api(libs.hilt.android.testing)
}