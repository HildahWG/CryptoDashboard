import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.isKaptVerbose
import org.jetbrains.kotlin.gradle.model.Kapt

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)




}

android {
    namespace = "com.example.cryptodashboard"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.example.cryptodashboard"
        minSdk = 24
        targetSdk = 34
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

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    val composeVersion = "1.6.1" // Use stable version
    val material3Version = "1.2.1"
    val navComposeVersion = "2.7.7"
    val lifecycleVersion = "2.6.2"
    val coroutinesVersion = "1.7.3"
    //val mpChartVersion = "3.1.0"
    val accompanistVersion = "0.35.0-alpha"



        implementation("androidx.compose.ui:ui:$composeVersion")
        implementation("androidx.compose.material3:material3:$material3Version")
        implementation("androidx.navigation:navigation-compose:$navComposeVersion")

        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")

        implementation("com.google.dagger:hilt-android:2.48")
        kapt("com.google.dagger:hilt-compiler:2.48")
        implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

        //implementation("com.github.PhilJay:MPAndroidChart:$mpChartVersion")
        implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")

        implementation ("com.google.accompanist:accompanist-flowlayout:0.30.1")




    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


}