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
 * The <code>LeistandException</code> is the base class for all Leitstand exceptions.
 * <p>
 * Every <code>LeitstandException</code> conveys the {@link Reason} why the exception was fired.  
 * </p>
 * @see Reason
 */
public abstract class LeitstandException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Reason reason;
	
	/**
	 * Create a <code>LeitstandException</code>. 
	 * @param reason the reason for this fault
	 * @param arguments arguments to form the message text
	 */
	protected LeitstandException(Reason reason, Object... arguments){
		super(reason.getMessage(arguments));
		this.reason = reason;
	}
	
	
	/**
	 * Create a <code>LeitstandException</code>. 
	 * @param cause the cause of this exception
	 * @param reason the reason for this fault
 	 * @param arguments arguments to form the message text
	 */
	protected LeitstandException(Exception cause, Reason reason, Object... arguments){
		super(cause);
		this.reason = reason;
	}

	/**
	 * Returns the reason of the fault reported by this exception.
	 * @return the reason code of the reported error.
	 */
	public Reason getReason() {
		return reason;
	}
	
}
