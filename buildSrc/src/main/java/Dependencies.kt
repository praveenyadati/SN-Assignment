const val kotlin = "1.4.0"

object BuildPlugins {
    object Versions {
        const val buildToolsVersion = "29.0.3"
        const val gradlePluginVersion = "4.0.1"
        const val typeSafePluginVersion = "2.3.0"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePluginVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin}"
    const val typeSafeGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.typeSafePluginVersion}"

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val safeArgs = "androidx.navigation.safeargs"
}

object AndroidSdk {
    const val min = 21
    const val compile = 28
    const val target = compile
}

object AndroidLibraries {
    private object Versions {
        const val jetpack = "1.1.0"
        const val constraintLayout = "1.1.3"
        const val ktx = "1.2.0"
        const val nav_version = "2.3.0"
        const val archLifecycleVersion = "2.2.0"
    }

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"
    const val navigationDynamic =
        "androidx.navigation:navigation-dynamic-features-fragment:${Versions.nav_version}"

    const val archLifeCycle = "androidx.lifecycle:lifecycle-extensions:${Versions.archLifecycleVersion}"
    const val archLifeCycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.archLifecycleVersion}"
}

object ThirdPartyLibraries {
    private object Versions {
        const val koin = "2.1.6"
        const val gson = "2.8.5"
        const val retrofit = "2.8.1"
        const val lottie = "3.4.0"
        const val coroutine = "1.0.0"
        const val roomVersion = "2.2.3"
    }

    const val koinAndroidLibrary = "org.koin:koin-android:${Versions.koin}"
    const val koinScopeLibrary = "org.koin:koin-android-scope:${Versions.koin}"
    const val koinViewModelLibrary = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    const val gsonLibrary = "com.google.code.gson:gson:${Versions.gson}"

    const val retrofitCore = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConvertGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    const val coroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"

    const val lottieLibrary = "com.airbnb.android:lottie:${Versions.lottie}"
    const val room = "androidx.room:room-compiler:${Versions.roomVersion}"

}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.12"
        const val testRunner = "1.1.1"
        const val espresso = "3.2.0"
    }

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}