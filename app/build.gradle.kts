plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.hilt.android)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.kapt)
}

android {
	namespace = "com.wit.fetchrewardsexercise"
	compileSdk = 35

	buildFeatures {
		viewBinding = true
	}

	defaultConfig {
		applicationId = "com.wit.fetchrewardsexercise"
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
				getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
	implementation(libs.androidx.fragment.ktx)
	implementation(libs.material)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)

	// Hilt
	implementation(libs.hilt.android)
	kapt(libs.hilt.android.compiler)

	// Retrofit
	implementation(libs.converter.gson)
	implementation(libs.retrofit)
}

kapt {
	correctErrorTypes = true
}