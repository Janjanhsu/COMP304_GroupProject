plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Kotlin Symbol Processing (KSP)
    id("com.google.devtools.ksp")
    // Kotlin Annotation Processing Tool (KAPT)
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.example.yichen.yichen_kwokwing_comp304sec001_lab03"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.yichen.yichen_kwokwing_comp304sec001_lab03"
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
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    // Architectural Components
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // Coroutine Lifecycle Scopes
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.contentpager)
    implementation(libs.compose.window.size)

    // Room - Local database
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.compiler)
    // Kotlin Extension and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    // Retrofit - API calls
    implementation(libs.retrofit)
    implementation (libs.converter.gson)
    // Debug the API requests
    //implementation(libs.logging.interceptor)

    // Navigation
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)

    // Glide - load the image from the API
    //implementation(libs.glide)
    //kapt(libs.compiler)
    implementation(libs.workmanager.koin)
    implementation(libs.koin.androidx.compose.v330)

    implementation(libs.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

//Fix Duplicate class org.intellij.lang.annotations.Identifier found in modules annotations-12.0.jar -> annotations-12.0 (com.intellij:annotations:12.0) and annotations-23.0.0.jar -> annotations-23.0.0 (org.jetbrains:annotations:23.0.0)
configurations {
    create("cleanedAnnotations")
    implementation {
        exclude(group = "org.jetbrains", module = "annotations")
    }
}