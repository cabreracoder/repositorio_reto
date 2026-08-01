plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.tutorialreto"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.tutorialreto"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Retrofit: hace las peticiones HTTP por nosotros
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // Gson: convierte el JSON del servidor en objetos Kotlin
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // OkHttp + logging: motor de red y registro para depurar
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // Corrutinas: para no congelar la app mientras se espera la red
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
}