plugins {
    id("org.openrewrite.build.recipe-library") version "latest.release"
}

group = "org.openrewrite.recipe"
description = "Rewrite Android recipes."

val rewriteVersion = rewriteRecipe.rewriteVersion.get()
dependencies {
    implementation(platform("org.openrewrite:rewrite-bom:$rewriteVersion"))
    implementation("org.openrewrite:rewrite-gradle:$rewriteVersion")

    runtimeOnly("org.openrewrite:rewrite-java-17")

    testImplementation("org.openrewrite.gradle.tooling:model:${rewriteVersion}")
    testImplementation(gradleApi())
}
