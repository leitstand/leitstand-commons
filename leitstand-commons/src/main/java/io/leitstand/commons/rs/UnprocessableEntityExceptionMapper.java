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

import static io.leitstand.commons.messages.Message.Severity.ERROR;
import static javax.json.Json.createObjectBuilder;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.leitstand.commons.UnprocessableEntityException;
import io.leitstand.commons.messages.Message;
import io.leitstand.commons.messages.Messages;

/**
 * Maps an {@link UnprocessableEntityException} to HTTP Status Code <code>422 Unprocessable entity</code>.
 */
@Provider
public class UnprocessableEntityExceptionMapper implements ExceptionMapper<UnprocessableEntityException> {

	@Inject
	private Messages messages;
	
	@Override
	public Response toResponse(UnprocessableEntityException e) {
		if(messages.isEmpty()) {
			JsonObject message = createObjectBuilder()
								.add("severity", ERROR.name())
								.add("reason", e.getReason().getReasonCode())
								.add("message", e.getMessage())
								.build();
			return status(422)
					.type(APPLICATION_JSON)
					.entity(message)
					.build();
		}
		
		// Insert unprocessable entity message at the beginning of all returned messages.
		messages.add(0,
					 new Message(ERROR, 
							 	 e.getReason().getReasonCode(), 
								 e.getMessage()));
		
		return status(422)
			   .type(APPLICATION_JSON)
			   .entity(messages)
			   .build();
		
	}
	
}
