import Build_gradle.Extensions.implementAll
import Build_gradle.Extensions.kaptAll

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id("io.gitlab.arturbosch.detekt")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.secrets)
}

android {

    buildFeatures {
        buildConfig = true
    }
    namespace = "com.example.biometricauthdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.biometricauthdemo"
        minSdk = 24
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
}
detekt {
    toolVersion = "1.23.6"
    buildUponDefaultConfig = true
    config.setFrom(file("${rootDir}/detekt.yml"))
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementAll(
        libs.androidx.navigation,
        libs.google.dagger.hilt,
        libs.squareup.retrofit,
        libs.squareup.okhttp3,
        libs.squareup.logging.interceptor,
        libs.squareup.converter.jackson,
        libs.androidx.hilt.navigation,
        libs.kotlinx.serialization.json,
        libs.jackson.module.kotlin,
        libs.androidx.lifecycle.runtime.compose,
        libs.androidx.compose.runtime.livedata,
        libs.gson,
        libs.androidx.biometrics
    )

    kaptAll(
        libs.google.dagger.hilt.compiler
    )

    detektPlugins(libs.twitter.compose.rules)
    detektPlugins(libs.arturbosh.detekt.formatting)

}

object Extensions {

    private fun DependencyHandler.implementation(dependency: Any) = add(
        "implementation", dependency
    )

    fun DependencyHandler.implementAll(vararg dependencies: Any) {
        dependencies.forEach {
            implementation(it)
        }
    }

    private fun DependencyHandler.kapt(dependency: Any) = add(
        "kapt", dependency
    )

    fun DependencyHandler.kaptAll(vararg dependencies: Any) {
        dependencies.forEach {
            kapt(it)
        }
    }
}