@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("simpleerp.android.library")
    id("simpleerp.android.hilt")
    id("simpleerp.android.room")
    id("kotlinx-serialization")
}

android {
    namespace = "com.sonder.simpleerp.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}
