plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.news_app_mvvm_sec20"
    compileSdk = 34

    // 2- it is important to add our costume apikey and baseUrl Fields for using buildConfigField
    android.buildFeatures.buildConfig = true
    
    //------------------------------
    defaultConfig {
        applicationId = "com.example.news_app_mvvm_sec20"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        //2- secure api key importing My Key from gradle.properties file, the first step is in gradle.properties

        val MY_KEY:String by project
        val MY_BASE_URL:String by project

        buildConfigField("String","API_KEY",MY_KEY)
        buildConfigField("String","BASE_URL",MY_BASE_URL)

        //------------------------

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {

            //3- secure api key using Proguard
            isMinifyEnabled = true
            isShrinkResources= true // this will shrink the APK size by removing unused classes and method, ignorers the resources that are not called
            //----------------------------------

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
    
    buildFeatures{
        viewBinding = true
    }
    
    

    //New Api Key: fca4d499ce1f48f9aa5b75b2535c9554
   //News Api Link: https://newsapi.org/v2/top-headlines?country=us&apiKey=fca4d499ce1f48f9aa5b75b2535c9554
    // Github Repository: https://github.com/AlhosinMohamad/News-App.git
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //-----------------------------
    val lifecycle_version = "2.6.2"
    // 1- ViewModel LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")
    // Annotation processor
     kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")

    // 2- Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
     kapt("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // 3- Coroutines
    val coroutines_version = "1.8.0-RC"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    
    //4- Hilt For Dependency Injection instead of Dagger
    implementation("com.google.dagger:hilt-android:2.50")
     kapt("com.google.dagger:hilt-android-compiler:2.50")

    // 5- Retrofit
    val retrofit2_version ="2.9.0"
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    val logging_interceptor = "4.11.0"
    implementation("com.squareup.okhttp3:logging-interceptor:$logging_interceptor")

    // 6- Glide
    val glide_version = "4.16.0"
    implementation("com.github.bumptech.glide:glide:$glide_version")

    //7- MockWebServer: it will intercept our requests and return whatever mocked response.
    // we use it to test NewsApiService by using fake API Calls. it doesnt need internet connection
    // Truth Library: it is to use mor assertions and functionalities in the test process
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation ("com.google.truth:truth:1.2.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    
    // 8- Navigation Jetpack
    val nav_version = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    
    
    
    
    
    /* 4- Dagger
    val dagger_version = "2.49"
    implementation ("com.google.dagger:dagger:$dagger_version")
    kapt ("com.google.dagger:dagger-compiler:$dagger_version")
    implementation ("com.google.dagger:dagger-android:$dagger_version")
    kapt  ("com.google.dagger:dagger-android-processor:$dagger_version")
    implementation ("com.google.dagger:dagger-android-support:$dagger_version")
    kapt  ("com.google.dagger:dagger-android-support:2.49")*/
}

kapt {
    correctErrorTypes = true
}