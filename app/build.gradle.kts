
import java.io.FileInputStream
import java.util.Properties

val apiKeyPropertiesFile = rootProject.file("apikey.properties")
val apiKeyProperties = Properties()

if (apiKeyPropertiesFile.exists()) {
    FileInputStream(apiKeyPropertiesFile).use {
        apiKeyProperties.load(it)
    }
}

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.githubprofile"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.githubprofile"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField(
            "String",
            "ACCESS_TOKEN",
            "\"${apiKeyProperties.getProperty("ACCESS_TOKEN", "")}\""
        )

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    implementation("com.airbnb.android:lottie:5.2.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Glide untuk memuat foto profil GitHub
    implementation("com.github.bumptech.glide:glide:4.13.2")

    // Retrofit untuk mengambil data GitHub API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // HTTP logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}
