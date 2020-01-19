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
import static javax.ws.rs.core.Response.Status.CONFLICT;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.leitstand.commons.ConflictException;
import io.leitstand.commons.EntityNotFoundException;

/**
 * Maps a {@link EntityNotFoundException} to HTTP Status Code <code>404 Not found</code>.
 */
@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException>{
	@Override
	public Response toResponse(ConflictException e) {
		JsonObject message = createObjectBuilder()
				             .add("severity", ERROR.name())
				             .add("reason", e.getReason().getReasonCode())
				             .add("message", e.getMessage())
				             .build();
		return status(CONFLICT)
			   .type(APPLICATION_JSON)
			   .entity(message)
			   .build();
	}
}
