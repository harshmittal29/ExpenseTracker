repositories {
    jcenter()
    google()
}

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

dependencies {
    // android gradle plugin, required by custom plugin
    implementation("com.android.tools.build:gradle:3.6.3")
    // kotlin plugin, required by custom plugin
    implementation(kotlin("gradle-plugin", "1.3.72"))
}

