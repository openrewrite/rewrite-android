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
import org.openrewrite.ExecutionContext;
import org.openrewrite.Option;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.groovy.GroovyIsoVisitor;
import org.openrewrite.internal.ListUtils;
import org.openrewrite.java.tree.J;

@Value
@EqualsAndHashCode(callSuper = false)
public class ChangeAndroidSdkVersion extends Recipe {

    @Option(displayName = "Android SDK Version",
            description = "The version of the Android SDK to use.",
            example = "35")
    Integer version;

    @Override
    public String getDisplayName() {
        return "Change Android SDK version";
    }

    @Override
    public String getDescription() {
        return "Change `compileSdk`, `compileSdkVersion`, `targetSdk` and `targetSdkVersion` in an Android Gradle build file to the argument version.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new GroovyIsoVisitor<ExecutionContext>() {
            @Override
            public J.MethodInvocation visitMethodInvocation(J.MethodInvocation method, ExecutionContext ctx) {
                J.MethodInvocation mi = super.visitMethodInvocation(method, ctx);
                String simpleName = mi.getSimpleName();
                if (simpleName.equals("compileSdk") || simpleName.equals("targetSdk")) {
                    return mi.withArguments(ListUtils.map(mi.getArguments(), arg -> {
                        if (arg instanceof J.Literal && !version.equals(((J.Literal) arg).getValue())) {
                            return ((J.Literal) arg).withValue(version).withValueSource(String.valueOf(version));
                        }
                        return arg;
                    }));
                }
                if (simpleName.equals("compileSdkVersion") || simpleName.equals("targetSdkVersion")) {
                    return mi.withArguments(ListUtils.map(mi.getArguments(), arg -> {
                        if (arg instanceof J.Literal && !String.valueOf(version).equals(((J.Literal) arg).getValue())) {
                            return ((J.Literal) arg).withValue(String.valueOf(version)).withValueSource(String.format("\"%d\"", version));
                        }
                        return arg;
                    }));
                }
                return mi;
            }
        };
    }
}
