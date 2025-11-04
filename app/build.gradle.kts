import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.google.gms.google-services")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "com.example.gameboxpv2"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.gameboxpv2"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //buildConfigField(
          //  "String",
            //"RAWG_API_KEY",
           // "\"195eec7cc1324d5dbfcd6b47c4af9852\"" //5ce3149fcffb494d973e72eafe545b41
        //)
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "RAWG_API_KEY",
                "\"${localProperties.getProperty("RAWG_API_KEY") ?: ""}\""
            )
        }
        debug {
            // Esto asegura que la clave esté disponible cuando se ejecuta la app
            buildConfigField(
                "String",
                "RAWG_API_KEY",
                "\"${localProperties.getProperty("RAWG_API_KEY") ?: ""}\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation(platform("com.google.firebase:firebase-bom:34.5.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    val navVersion = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:${navVersion}")
    implementation("androidx.navigation:navigation-ui-ktx:${navVersion}")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("io.coil-kt:coil:2.6.0")

}