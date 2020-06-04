package io.leitstand.commons.rs;

import static io.leitstand.commons.messages.Message.Severity.ERROR;
import static javax.json.Json.createObjectBuilder;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import javax.json.JsonObject;
import javax.transaction.RollbackException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RollbackExceptionMapper implements ExceptionMapper<RollbackException> {

	@Override
	public Response toResponse(RollbackException e) {
	
		JsonObject message = createObjectBuilder()
   			 				 .add("severity", ERROR.name())
   			 				 .add("message", "Database transaction rolled back. Details: "+e.getMessage())
   			 				 .build();
		return status(INTERNAL_SERVER_ERROR)
			   .type(APPLICATION_JSON)
			   .entity(message)
			   .build();
	}

}
