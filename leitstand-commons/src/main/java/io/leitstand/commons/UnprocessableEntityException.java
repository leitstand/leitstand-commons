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
 * A <code>UnprocessableEntityException</code> is thrown when a request
 * entity was syntactically correct but the server was not able to complete the operation
 * due to a semantical errors.
 */
public class UnprocessableEntityException extends LeitstandException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Create a <code>UnprocessableEntityException</code>.
	 * @param reason - the reason explains why the specified entity was not processable
	 * @param context - provides context information such as the invalid values
	 */
	public UnprocessableEntityException(Reason reason, Object... context){
		super(reason,context);
	}
	
}
