plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.safeArgs)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        applicationId = "com.servicenow.test"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            matchingFallbacks = listOf("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isMinifyEnabled = false
            matchingFallbacks = listOf("debug")
        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project( ":baseframework", "default"))

    implementation(AndroidLibraries.kotlinStdLib)
    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.ktxCore)
    implementation(AndroidLibraries.constraintLayout)

    implementation(AndroidLibraries.navigationFragment)
    implementation(AndroidLibraries.navigationUI)
    implementation(AndroidLibraries.navigationDynamic)
    implementation(AndroidLibraries.archLifeCycle)
    implementation(AndroidLibraries.archLifeCycleViewModel)

    implementation(ThirdPartyLibraries.koinAndroidLibrary)
    implementation(ThirdPartyLibraries.koinScopeLibrary)
    implementation(ThirdPartyLibraries.koinViewModelLibrary)
    implementation(ThirdPartyLibraries.gsonLibrary)
    implementation(ThirdPartyLibraries.retrofitCore)
    implementation(ThirdPartyLibraries.retrofitConvertGson)
    implementation(ThirdPartyLibraries.retrofitAdapter)
    implementation(ThirdPartyLibraries.coroutines)
    implementation(ThirdPartyLibraries.coroutinesAdapter)
    implementation(ThirdPartyLibraries.room)

    implementation(ThirdPartyLibraries.lottieLibrary)
    implementation("androidx.room:room-runtime:2.2.5")

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)
    kapt("androidx.room:room-compiler:2.2.5")
}