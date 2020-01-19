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
 * The <code>ConflictException</code> is thrown whenever a conflict has been detected during request processing.
 * <p>
 * Example causes of conflicts are:
 * <ul>
 * 	<li>Attempts to modify the same resource concurrently</li>
 *  <li>Attempts to remove a resource which is required to be available for other active resources.</li>
 * </ul>
 */
public class ConflictException extends LeitstandException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a <code>ConflictException</code>.
	 * @param reason the reason of the conflict.
	 * @param args message template parameters.
	 */
	public ConflictException(Reason reason, Object... args){
		super(reason,args);
	}
	
	
}
