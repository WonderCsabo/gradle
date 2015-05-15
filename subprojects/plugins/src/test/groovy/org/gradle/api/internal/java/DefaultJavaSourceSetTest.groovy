/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.internal.java
import org.gradle.api.file.SourceDirectorySet
import org.gradle.jvm.Classpath
import spock.lang.Specification

class DefaultJavaSourceSetTest extends Specification {
    def "has useful String representation"() {
        def resourceSet = new DefaultJavaSourceSet("javaX", "mainX", Stub(SourceDirectorySet), Stub(Classpath))

        expect:
        resourceSet.displayName == "Java source 'mainX:javaX'"
        resourceSet.toString() == "Java source 'mainX:javaX'"
    }

    def "can add a project dependency using dependencies property"() {
        def sourceSet = new DefaultJavaSourceSet("javaX", "mainX", Stub(SourceDirectorySet), Stub(Classpath))

        when:
        sourceSet.dependencies.project ':foo'

        then:
        sourceSet.dependencies.size() == 1
        sourceSet.dependencies[0].projectPath == ':foo'
    }

    def "can add a project dependency"() {
        def sourceSet = new DefaultJavaSourceSet("javaX", "mainX", Stub(SourceDirectorySet), Stub(Classpath))

        when:
        sourceSet.dependencies {
            project ':foo'
        }

        then:
        sourceSet.dependencies.size() == 1
        sourceSet.dependencies[0].projectPath == ':foo'
    }

    def "can add a library dependency"() {
        def sourceSet = new DefaultJavaSourceSet("javaX", "mainX", Stub(SourceDirectorySet), Stub(Classpath))

        when:
        sourceSet.dependencies {
            library 'fooLib'
        }

        then:
        sourceSet.dependencies.size() == 1
        sourceSet.dependencies[0].libraryName == 'fooLib'
    }

    def "can add a project library dependency"() {
        def sourceSet = new DefaultJavaSourceSet("javaX", "mainX", Stub(SourceDirectorySet), Stub(Classpath))

        when:
        sourceSet.dependencies {
            project ':foo' library 'fooLib'
        }

        then:
        sourceSet.dependencies.size() == 1
        sourceSet.dependencies[0].projectPath == ':foo'
        sourceSet.dependencies[0].libraryName == 'fooLib'
    }

    def "can add a multiple dependencies"() {
        def sourceSet = new DefaultJavaSourceSet("javaX", "mainX", Stub(SourceDirectorySet), Stub(Classpath))

        when:
        sourceSet.dependencies {
            project ':foo'
            library 'fooLib'
            project ':bar' library 'barLib'
        }

        then:
        sourceSet.dependencies.size() == 3
        sourceSet.dependencies[0].projectPath == ':foo'
        sourceSet.dependencies[1].libraryName == 'fooLib'
        sourceSet.dependencies[2].projectPath == ':bar'
        sourceSet.dependencies[2].libraryName == 'barLib'
    }
}
