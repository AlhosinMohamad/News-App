// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    kotlin("jvm") version "1.5.0"
    id("com.google.dagger.hilt.android") version "2.50" apply false
}

buildscript {
    repositories{
        google()
    }
    
    dependencies{
        val nav_version = "2.7.6"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}