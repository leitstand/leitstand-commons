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
package io.leitstand.commons.messages;

import io.leitstand.commons.model.ValueObject;

/**
 * A message to inform the API client about an incident on the server.
 * <p>
 * The message consists of a severity, a reason code and a message text.
 * </p>
 * <p>
 * The severity defines whether the message reports an error, a warning or 
 * a general information. The reason code identifies the root cause of every
 * message unambiguously and last but not least the message provides a detailed
 * description of what happened.
 * </p>
 * <p>
 * All messages are collected per request. 
 * The <code>{@literal @RequestScoped}</code> {@link Messages} object is used for the same.
 * </p>
 * An operation is failed, if at least one reported message is an error message.
 */
public class Message extends ValueObject{

	/**
	 * Enumeration of all existing message severities.
	 */
	public enum Severity{
		/** 
		 * An unexpected situation occurred and the current operation has failed.
		 * Corrective actions are likely required (e.g. fix a wrong parameter).
		 */
		ERROR,
		/** 
		 * An unexpected situation occurred but the operation still succeeded.
		 * Further actions might be required to fix the root cause of a warning
		 * to avoid the generation of errors in the future.
		 */
		WARNING,
		/** 
		 * An information of an action or incident that happened but does not reqiure any further interventions.
		 * Typically, an information message reports details of a succeeded operation.
		 */
		INFO
	}
	
	private String reason;
	
	private String message;
	
	private Severity severity;
	
	private String property;
	
	protected Message(){
		// JAXB
	}
	
	/**
	 * Creates a <code>Message</code>
	 * @param severity the message severity 
	 * @param reason the message reason code
	 * @param message the description of the.
	 */
	public Message(Severity severity, 
				   String reason, 
				   String message){
		this(severity,
			 reason,
			 null,
			 message);
	}
	
	/**
	 * Creates a  <code>Message</code>.
	 * @param severity the message severity 
	 * @param reason the message reason code
	 * @param property the name of the property that caused the problem
	 * @param message the description of the.
	 */
	public Message(Severity severity, 
				   String reason,
				   String property,
				   String message){
		this.severity = severity;
		this.reason = reason;
		this.property = property;
		this.message = message;
	}
	
	/**
	 * Returns the message text
	 * @return the message text
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Returns the name of the property associated with this message or <code>null</code> if this message is not associated with a particular property.
	 * @return the property this message is associated with
	 */
	public String getProperty() {
		return property;
	}
	
	/**
	 * Returns the message severity
	 * @return the message severity
	 */
	public Severity getSeverity() {
		return severity;
	}

	/**
	 * Returns the message reason code.
	 * @return the reason code.
	 */
	public String getReason() {
		return reason;
	}
	
	
}
