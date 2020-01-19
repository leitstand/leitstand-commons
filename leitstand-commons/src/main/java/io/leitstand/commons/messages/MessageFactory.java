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

import io.leitstand.commons.Reason;

public final class MessageFactory {

	public static Message createMessage(Reason key, 
									    Object... args){
		return createMessage(key,
							 null,
							 args);
	}

	public static Message createMessage(Reason key,
										String property,
										Object... args){
		Message.Severity severity = key.getSeverity();
		return new Message(severity,
						   key.getReasonCode(),
						   property,
						   key.getMessage(args));
	}

	private MessageFactory() {
		// No instances allowed.
	}
	
	
}
