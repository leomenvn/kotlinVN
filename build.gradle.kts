buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.1" apply false
    id("org.jetbrains.kotlin.kapt") version "2.0.0-Beta1" apply false
    kotlin("plugin.serialization") version "1.9.0" apply false
}