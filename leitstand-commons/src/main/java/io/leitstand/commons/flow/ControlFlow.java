/*
 * Copyright 2020 RtBrick Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.leitstand.commons.flow;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Stereotype;

/**
 * The <code>ControlFlow</code> stereotype.
 * <p>
 * A control flow is a stateless dependent services that orchestrates the execution of transactional services in a certain order.
 * A sample flow is the completion of a scheduler task: first, a transaction updates the state of the completed task. 
 * Once this is done, a subsequent transaction searches the next tasks to be executed.
 * </p>
 */
@Retention(RUNTIME)
@Target(TYPE)
@Stereotype
@Dependent
public @interface ControlFlow {

}
