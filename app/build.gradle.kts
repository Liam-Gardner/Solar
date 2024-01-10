plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.solar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.solar"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("com.google.android.gms:play-services-wearable:18.1.0")
    implementation("androidx.compose.ui:ui:1.5.4") // missing version num?
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4") // missing version num?
    implementation("androidx.wear.compose:compose-material:1.2.1")
    implementation("androidx.wear.compose:compose-foundation:1.2.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.wear.tiles:tiles:1.2.0")
    implementation("androidx.wear.tiles:tiles-material:1.2.0")
    implementation("com.google.android.horologist:horologist-compose-tools:0.4.13")
    implementation("com.google.android.horologist:horologist-tiles:0.4.13")
    implementation("androidx.wear.watchface:watchface-complications-data-source-ktx:1.2.0")

    implementation("androidx.compose.material:material:1.5.4")

    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.0.1")


    // Network requests
    implementation("com.squareup.retrofit2:retrofit:2.6.3")
    implementation("com.squareup.retrofit2:converter-gson:2.6.3")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    annotationProcessor("com.google.dagger:hilt-compiler:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.49")
    kapt("androidx.hilt:hilt-compiler:1.1.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // this fixes an error about saved state or something
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.wear.compose:compose-navigation:1.2.1")

    // debug
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4") // missing version num?
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4") // missing version num?

    // not used in previous app
    implementation("androidx.percentlayout:percentlayout:1.0.0") // new
    implementation("androidx.legacy:legacy-support-v4:1.0.0") // new
    implementation("androidx.recyclerview:recyclerview:1.3.2") // new
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4") // new
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00")) // new
}
