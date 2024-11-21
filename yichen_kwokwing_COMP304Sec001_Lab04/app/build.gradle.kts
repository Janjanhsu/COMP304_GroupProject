plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.yichen.yichen_kwokwing_comp304sec001_lab04"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.yichen.yichen_kwokwing_comp304sec001_lab04"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.1"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
    buildFeatures {
        viewBinding = true
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
        implementation(libs.play.services.maps)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)

        // Compose dependencies
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.koin.androidx.compose.v330)
        implementation(libs.androidx.material.icons.extended)
        // Google Maps
        implementation(libs.play.services.maps.v1802)
        //Dagger - Hilt
        implementation(libs.hilt.android)
        kapt(libs.hilt.android.compiler)
        implementation(libs.androidx.hilt.lifecycle.viewmodel)
        kapt(libs.androidx.hilt.compiler)
        implementation(libs.androidx.hilt.navigation.compose)
        implementation(libs.androidx.navigation.runtime.ktx)
        // Room
        implementation(libs.androidx.room.runtime)
        kapt("androidx.room:room-compiler:2.6.1")
        // Kotlin Extensions and Coroutines support for Room
        implementation(libs.androidx.room.ktx)
        // compose maps library
        implementation(libs.maps.compose.v433)
        // Optionally, you can include the Compose utils library for Clustering,
        // Street View metadata checks, etc.
        implementation (libs.maps.compose.utils)
        // Optionally, you can include the widgets library for ScaleBar, etc.
        implementation (libs.maps.compose.widgets)
        implementation(libs.play.services.location)
        implementation(libs.play.services.maps)
        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.constraintlayout)
    }
}
