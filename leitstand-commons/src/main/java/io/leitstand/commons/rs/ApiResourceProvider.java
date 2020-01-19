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
package io.leitstand.commons.rs;

import java.util.Set;

/**
 * Returns all API resources provided by a EMS module.
 * <p>
 * The provider must be a CDI managed bean.
 * <code>{@literal @Dependent}</code> scope is recommended, as the bean is only required at boot time.
 * </p>
 * @see ApiResources
 */
public interface ApiResourceProvider {
	
	/**
	 * Returns a set of API resources provided by a single module.
	 * @return an immutable set of API resources
	 */
	Set<Class<?>> getResources();

}
