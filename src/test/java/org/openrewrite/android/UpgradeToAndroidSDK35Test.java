package org.openrewrite.android;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openrewrite.DocumentExample;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.gradle.Assertions.buildGradle;
import static org.openrewrite.gradle.toolingapi.Assertions.withToolingApi;

class UpgradeToAndroidSDK35Test implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipeFromResources("org.openrewrite.android.UpgradeToAndroidSDK35")
          .parser(JavaParser.fromJavaVersion());
    }

    @Disabled("Not yet implemented")
    @DocumentExample
    @Test
    void upgradeToAndroidSDK35() {
        rewriteRun(
          spec -> spec.beforeRecipe(withToolingApi()),
          //language=gradle
          buildGradle(
            """
                buildscript {
                    dependencies {
                        classpath 'com.android.tools.build:gradle:3.4.0'
                    }
                }
                repositories {
                    mavenCentral()
                }
                apply plugin: 'com.android.application'
                group = "org.example"
                version = "1.0-SNAPSHOT"
                android {
                   compileSdkVersion 28
                   defaultConfig {
                       targetSdkVersion 28
                   }
                }
              """,
            """
                buildscript {
                    dependencies {
                        classpath 'com.android.tools.build:gradle:3.4.0'
                    }
                }
                repositories {
                    mavenCentral()
                }
                apply plugin: 'com.android.application'
                group = "org.example"
                version = "1.0-SNAPSHOT"
                android {
                   compileSdkVersion 35
                   defaultConfig {
                       targetSdkVersion 35
                   }
                }
              """
          )
        );
    }
}
