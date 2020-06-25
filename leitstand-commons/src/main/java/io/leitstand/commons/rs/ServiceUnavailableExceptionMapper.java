package io.leitstand.commons.rs;

import static io.leitstand.commons.messages.Message.Severity.ERROR;
import static javax.json.Json.createObjectBuilder;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.leitstand.commons.ServiceUnavailableException;

@Provider
public class ServiceUnavailableExceptionMapper implements ExceptionMapper<ServiceUnavailableException> {

	@Override
	public Response toResponse(ServiceUnavailableException e) {
	
		JsonObject message = createObjectBuilder()
   			 				 .add("severity", ERROR.name())
   			 				 .add("message", "Database transaction rolled back. Details: "+e.getMessage())
   			 				 .build();
		return status(SERVICE_UNAVAILABLE)
			   .type(APPLICATION_JSON)
			   .entity(message)
			   .build();
	}

}
