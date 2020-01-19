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
package io.leitstand.commons.model;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Stereotype;
import javax.transaction.Transactional;

/**
 * Stateless, transactional Service stereotype.
 * <p>
 * A service is application-scoped. Only a single service instance is created and hence the implementation 
 * must be stateless with respect to concurrently processed requests.
 * All methods are transactional and either join an already active transaction or create a new transaction.
 * The {@literal @Transactional} annotation creates a user-managed transaction internally and the limitations 
 * outlined in the EJB specification must be respected. In essence,
 * <ul>
 * <li>whenever a service invokes an EJB with container-managed transaction demarcation, then the EJB joins the transaction</li>
 * <li>however, an exception is raised whenever an EJB with container-managed transaction demarcation calls a service, because the EJB specification states that any attempt to access the transaction from a bean with container-managed transaction demarcation is strictly prohibited.</li>
 * </ul>
 * The transaction is rolled back when the service raises a <code>RuntimeException</code>, irrespective whether an {@literal @ApplicatonException} annotation is present or not.
 */
@Stereotype
@ApplicationScoped
@Transactional(rollbackOn=RuntimeException.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface Service {

	// Marker annotation
	
}
