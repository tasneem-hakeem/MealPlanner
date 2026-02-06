plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.tasneem.mealplanner"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.tasneem.mealplanner"
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.cardview)
    implementation(libs.recyclerview)
    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.fragment)
    // Retrofit
    implementation(libs.retrofit)
    // Gson Converter
    implementation(libs.converter.gson)
    // Glide
    implementation(libs.glide)
    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    // Firebase
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    // BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.auth)
    // Rx Java
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.adapter.rxjava3)
    implementation(libs.room.rxjava3)
    // Lottie
    implementation(libs.lottie)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}