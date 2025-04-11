plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    id("kotlin-kapt")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.example.health"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.health"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
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
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.volley)
    implementation(libs.play.services.fido)
    implementation(libs.androidx.espresso.core)
    implementation(libs.play.services.fido)
    implementation(libs.play.services.fido)
    implementation(libs.play.services.fido)
    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.androidx.webkit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("com.google.android.gms:play-services-auth:21.3.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    implementation ("androidx.room:room-ktx:2.6.1")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation ("com.google.code.gson:gson:2.10.1")

    implementation ("com.chargemap.compose:numberpicker:1.0.3")


    implementation("com.airbnb.android:lottie-compose:6.3.0")

    // Coil core (cho Android View và Bitmap)
    implementation("io.coil-kt:coil:2.4.0")

    // Nếu bạn dùng Compose (có thể bỏ nếu không dùng Compose)
    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.kizitonwose.calendar:compose:2.3.0")
    //implementation("io.github.ehsannarmani:compose-charts:1.0.0-beta04")
    //implementation("com.github.tehras:charts:0.2.4")
    implementation("com.patrykandpatrick.vico:core:1.6.4")
    implementation("com.patrykandpatrick.vico:compose:1.6.4")
    implementation("com.patrykandpatrick.vico:views:1.6.4")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation( "androidx.compose.foundation:foundation:1.7.8" )// hoặc mới hơn




}