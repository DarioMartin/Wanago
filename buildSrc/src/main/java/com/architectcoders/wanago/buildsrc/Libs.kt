@file:Suppress("unused")

package com.architectcoders.wanago.buildsrc

object Libs {

    const val androidGradlePlugin = "com.android.tools.build:gradle:7.3.1"
    const val playServicesLocation = "com.google.android.gms:play-services-location:19.0.1"
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:0.45.0"

    object Kotlin {
        private const val version = "1.8.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

        object Coroutines {
            private const val version = "1.6.0"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        }
    }

    object AndroidX {

        const val coreKtx = "androidx.core:core-ktx:1.9.0"
        const val appCompat = "androidx.appcompat:appcompat:1.6.1"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        const val material = "com.google.android.material:material:1.8.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

        object Activity {
            private const val version = "1.6.1"
            const val ktx = "androidx.activity:activity-ktx:$version"
        }

        object Fragment {
            private const val version = "1.5.5"
            const val ktx = "androidx.fragment:fragment-ktx:$version"
        }

        object Navigation {
            private const val version = "2.5.3"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
            const val gradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Test {
            object Ext {
                private const val version = "1.1.5"
                const val junit = "androidx.test.ext:junit:$version"
            }

            object Espresso {
                private const val version = "3.5.1"
                const val core = "androidx.test.espresso:espresso-core:$version"
            }
        }
    }

    object PlayServicesLocation {
        private const val version = "21.0.1"
        const val library = "com.google.android.gms:play-services-location:$version"
    }

    object Glide {
        private const val version = "4.14.2"
        const val library = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object OkHttp3 {
        private const val version = "4.10.0"
        const val loginInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val library = "com.squareup.retrofit2:retrofit:$version"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
    }

    object JUnit {
        private const val version = "4.13.2"
        const val library = "junit:junit:$version"
    }
}
