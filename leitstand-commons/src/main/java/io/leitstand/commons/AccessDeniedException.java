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
 * The <code>AccessDeniedException</code> signals that the authenticated user 
 * is not allowed to perform the requested operation.
 */
public class AccessDeniedException extends LeitstandException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a <code>AccessDeniedException</code>
	 * @param reason the reason why the access is denied
	 * @param cause the root cause
	 */
	public AccessDeniedException(Exception cause, Reason reason, Object... args) {
		super(cause, reason, args);
	}
	
	public AccessDeniedException(Reason reason, Object... args) {
		super(reason,args);
	}


}
