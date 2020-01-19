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

import javax.persistence.EntityManager;

/**
 * A functional interface to facilitate the execution of named JPQL queries.
 * <p>
 * The <code>Update</code> class is based on the same concept as outlined in {@link Query} 
 * but is used for the execution of <code>UPDATE</code> and <code>DELETE</code> statements.
 * Consequently, an update does not return a result set but the number of affected database records.
 * </p>
 * 
 * @see Query The <code>Query</code> description
 */
@FunctionalInterface
public interface Update {
	
	/**
	 * Execute and update and returns the number of affected rows.
	 * @param em - the entity manager executing the update
	 * @return the number of affected rows.
	 */
	int execute(EntityManager em);
}
