/*
 * Copyright 2014 the original author or authors.
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

package org.gradle.runtime.jvm.internal

import org.gradle.internal.reflect.DirectInstantiator
import org.gradle.language.base.FunctionalSourceSet
import org.gradle.language.base.LanguageSourceSet
import org.gradle.language.base.internal.DefaultFunctionalSourceSet
import org.gradle.runtime.base.ComponentSpecIdentifier
import spock.lang.Specification

class DefaultJvmLibrarySpecTest extends Specification {
    def libraryId = Mock(ComponentSpecIdentifier)
    FunctionalSourceSet mainSourceSet

    def setup(){
        mainSourceSet = new DefaultFunctionalSourceSet("testFss", new DirectInstantiator());
    }

    def "library has name and path"() {
        def library = new DefaultJvmLibrarySpec(libraryId, mainSourceSet)

        when:
        _ * libraryId.name >> "jvm-lib"
        _ * libraryId.projectPath >> ":project-path"

        then:
        library.name == "jvm-lib"
        library.projectPath == ":project-path"
    }

    def "contains all languageSourceSet of main SourceSet"(){
        given:
        def library = new DefaultJvmLibrarySpec(libraryId, mainSourceSet)

        when:
        def sourceSet1 = Stub(LanguageSourceSet) {
            getName() >> "ss1"
        }
        mainSourceSet.add(sourceSet1)
        then:
        library.source.contains(sourceSet1)
    }
}
