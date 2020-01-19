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
package io.leitstand.commons;

/**
 * The <code>EntityNotFoundException</code> signals that a requested entity does
 * not exist.
 */
public class EntityNotFoundException extends LeitstandException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a <code>EntityNotFoundException</code>.
	 * @param reason - the reason code explaining which entity was not found
	 * @param key - key information about the requested entity
	 */
	public EntityNotFoundException(Reason reason, Object... key) {
		super(reason, key);
	}

}
