plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.rom"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.rom"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

}
configurations.configureEach {
    exclude(group = "com.intellij", module = "annotations")
}

dependencies {

    implementation(libs.material)
    implementation(libs.androidx.activity)
    val lifecycle_version = "2.10.0"
    val arch_version = "2.2.0"

    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")

    implementation("androidx.cardview:cardview:1.0.0")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")


    implementation(libs.androidx.room.compiler)
    val room_version = "2.8.4"
    ksp("androidx.room:room-compiler:$room_version")

    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:${room_version}")

    implementation("androidx.appcompat:appcompat:1.7.1")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}