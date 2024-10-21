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

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.openrewrite.Option;
import org.openrewrite.Recipe;
import org.openrewrite.gradle.UpdateGradleWrapper;
import org.openrewrite.gradle.UpgradeDependencyVersion;

import java.util.Arrays;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = false)
public class UpgradeAndroidGradlePluginVersion extends Recipe {

    @Option(displayName = "Android Gradle Plugin (AGP) version",
            description = "The version of the Android Gradle Plugin to use.",
            example = "8.6.x")
    String agpVersion;

    @Option(displayName = "Gradle Wrapper version",
            description = "The version of the Gradle Wrapper to use.",
            example = "(8.7, 9]")
    String gradleWrapperVersion;

    @Override
    public List<Recipe> getRecipeList() {
        return Arrays.asList(
                new UpgradeDependencyVersion("com.android.tools.build", "gradle", agpVersion, null),
                new UpdateGradleWrapper(gradleWrapperVersion, null, null, null, null));
    }

    @Override
    public String getDisplayName() {
        return "Upgrade Android Gradle Plugin (AGP) version";
    }

    @Override
    public String getDescription() {
        return "Upgrade Android Gradle Plugin (AGP) version and update the Gradle Wrapper version. Compatible versions are published in the [AGP release notes](https://developer.android.com/build/releases/gradle-plugin).";
    }
}
