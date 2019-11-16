/*
 * Copyright 2019 MovingBlocks
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

package org.terasology.gestalt.assets.module;

import com.google.common.collect.ImmutableSet;

import org.junit.Test;
import org.terasology.gestalt.module.ModuleEnvironment;
import org.terasology.gestalt.naming.Name;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * @author Immortius
 */
public class ModuleDependencyResolutionStrategyTest {

    private static final Name TEST_MODULE = new Name("test");
    private static final Name MODULE_A = new Name("moduleA");

    @Test
    public void resolveNoOptions() {
        ModuleEnvironment environment = TestModulesUtil.createEnvironment();
        ModuleDependencyResolutionStrategy strategy = new ModuleDependencyResolutionStrategy(environment);
        assertEquals(Collections.<Name>emptySet(), strategy.resolve(Collections.<Name>emptySet(), Name.EMPTY));
    }

    @Test
    public void resolveSingleOptionOutsideOfContextAndDependencies() {
        ModuleEnvironment environment = TestModulesUtil.createEnvironment();
        ModuleDependencyResolutionStrategy strategy = new ModuleDependencyResolutionStrategy(environment);
        assertEquals(Collections.<Name>emptySet(), strategy.resolve(ImmutableSet.of(new Name("Cats")), Name.EMPTY));
    }

    @Test
    public void resolveReturnsContextIfPresent() {
        ModuleEnvironment environment = TestModulesUtil.createEnvironment(TEST_MODULE, MODULE_A);
        ModuleDependencyResolutionStrategy strategy = new ModuleDependencyResolutionStrategy(environment);
        assertEquals(ImmutableSet.of(TEST_MODULE), strategy.resolve(ImmutableSet.of(TEST_MODULE, MODULE_A), TEST_MODULE));
    }

    @Test
    public void resolveReturnsOnlyDependenciesOfContext() {
        ModuleEnvironment environment = TestModulesUtil.createEnvironment(TEST_MODULE, MODULE_A);
        ModuleDependencyResolutionStrategy strategy = new ModuleDependencyResolutionStrategy(environment);
        assertEquals(ImmutableSet.of(TEST_MODULE), strategy.resolve(ImmutableSet.of(TEST_MODULE, new Name("Cat")), MODULE_A));
    }

}