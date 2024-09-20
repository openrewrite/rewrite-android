/*
 * Copyright 2024 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.android;

import org.junit.jupiter.api.Test;
import org.openrewrite.DocumentExample;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.gradle.Assertions.buildGradle;
import static org.openrewrite.gradle.toolingapi.Assertions.withToolingApi;

class ChangeAndroidSdkVersionTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new ChangeAndroidSdkVersion(35))
          .beforeRecipe(withToolingApi("8.10.1", "bin"));
    }

    @DocumentExample
    @Test
    void agp7OrHigher() {
        rewriteRun(
          //language=gradle
          buildGradle(
            """
                buildscript {
                    repositories {
                        mavenCentral()
                        google()
                    }
                    dependencies {
                        classpath 'com.android.tools.build:gradle:8.6.1'
                    }
                }
                repositories {
                    mavenCentral()
                }
                apply plugin: 'com.android.application'
                group = "org.example"
                version = "1.0-SNAPSHOT"
                android {
                   compileSdk 28
                   defaultConfig {
                       targetSdk 28
                   }
                   namespace = "com.example.myapp"
                }
              """,
            """
                buildscript {
                    repositories {
                        mavenCentral()
                        google()
                    }
                    dependencies {
                        classpath 'com.android.tools.build:gradle:8.6.1'
                    }
                }
                repositories {
                    mavenCentral()
                }
                apply plugin: 'com.android.application'
                group = "org.example"
                version = "1.0-SNAPSHOT"
                android {
                   compileSdk 35
                   defaultConfig {
                       targetSdk 35
                   }
                   namespace = "com.example.myapp"
                }
              """
          )
        );
    }

    @Test
    void agp4OrLower() {
        rewriteRun(
          //language=gradle
          buildGradle(
            """
                buildscript {
                    repositories {
                        mavenCentral()
                        google()
                    }
                    dependencies {
                        classpath 'com.android.tools.build:gradle:4.1.3'
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
                    repositories {
                        mavenCentral()
                        google()
                    }
                    dependencies {
                        classpath 'com.android.tools.build:gradle:4.1.3'
                    }
                }
                repositories {
                    mavenCentral()
                }
                apply plugin: 'com.android.application'
                group = "org.example"
                version = "1.0-SNAPSHOT"
                android {
                   compileSdkVersion "35"
                   defaultConfig {
                       targetSdkVersion "35"
                   }
                }
              """
          )
        );
    }
}
