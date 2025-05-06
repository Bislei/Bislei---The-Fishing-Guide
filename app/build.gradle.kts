import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

// Load the API key from apikey.properties
val apiKeyPropertiesFile = rootProject.file("apikey.properties")
val apiKeyProperties = Properties().apply {
    load(FileInputStream(apiKeyPropertiesFile))
}

android {
    namespace = "com.kashmir.bislei"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kashmir.bislei"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Inject the MAPS_API_KEY into the manifest
        manifestPlaceholders["MAPS_API_KEY"] = apiKeyProperties.getProperty("MAPS_API_KEY")

    }

    configurations.all {
        resolutionStrategy {
            force("androidx.compose.animation:animation-lint:1.8.0-rc03")
        }
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

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.room.runtime.android)
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)

    // Compose Runtime & LiveData
    implementation("androidx.compose.runtime:runtime:1.7.8")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.8")

    // Maps Compose
    implementation("com.google.maps.android:maps-compose:6.4.1")

    // Accompanist Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.24.13-rc")

    // Coil (for image loading)
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Icons & Animations
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.animation)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("androidx.compose.material3:material3:1.2.0")
}
