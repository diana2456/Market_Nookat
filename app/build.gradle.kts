plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "nook.test.market_nookat"
    compileSdk = 34

    packagingOptions {
       resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
    }
    defaultConfig {
        applicationId = "nook.test.market_nookat"
        minSdk = 24
        //noinspection EditedTargetSdkVersion,OldTargetApi
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

    }
    kotlinOptions {
        jvmTarget = "1.8"
    }


    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.firebase:firebase-messaging:23.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //dost indicator
    implementation("com.tbuonomo:dotsindicator:4.3")
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation("com.github.smarteist:autoimageslider:1.4.0")

    //KoinDI
    implementation("io.insert-koin:koin-android:2.2.2")
    implementation("io.insert-koin:koin-androidx-scope:2.2.2")
    implementation("io.insert-koin:koin-androidx-viewmodel:2.2.2")

    //google facobook
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.facebook.android:facebook-android-sdk:latest.release")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Activity/Fragment Kotlin Extension
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.6.1")

    //Retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit-mock:2.9.0")

    //Okhttp
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")


    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")


    //Refreshlayout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //Galegy
    implementation("com.github.QuadFlask:colorpicker:0.0.13")
    implementation ("com.github.dhaval2404:imagepicker:2.1")

    //Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    //Gson
    implementation("com.google.code.gson:gson:2.9.0")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    //swiper
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //okthhp
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

}