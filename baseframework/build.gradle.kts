import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.safeArgs)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    buildToolsVersion(BuildPlugins.Versions.buildToolsVersion)

    defaultConfig {
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

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

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
    implementation(ThirdPartyLibraries.retrofitAdapter)
    implementation(ThirdPartyLibraries.retrofitAdapter)

    implementation(ThirdPartyLibraries.lottieLibrary)
    implementation(ThirdPartyLibraries.coroutinesAdapter)
    implementation(ThirdPartyLibraries.logger)

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)
}
