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
package io.leitstand.commons.jsonb;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import io.leitstand.commons.messages.Message;
import io.leitstand.commons.messages.Messages;

/**
 * Marshals the {@link Messages} container in propert JSON.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MessagesMessageBodyWriter implements MessageBodyWriter<Messages> {

	/**
	 * Returns always <code>0</code>.
	 * {@inheritDoc}
	 */
	@Override
	public long getSize(Messages object, 
						Class<?> type, 
						Type genericType, 
						Annotation[] annotations,
						MediaType mediaType) {
		return 0;
	}

	/**
	 * Returns always <code>true</code>.
	 * {@inheritDoc}
	 */
	@Override
	public boolean isWriteable(Class<?> type, 
							   Type genericType, 
							   Annotation[] annotations, 
							   MediaType mediaType) {
		return true;
	}

	/**
	 * Marshals all messages in <code>JSON</code> using <code>UTF-8</code> character encoding.
	 */
	@Override
	public void writeTo(Messages messages,
						Class<?> type,
						Type genericType,
						Annotation[] annotations,
						MediaType mediaType,
						MultivaluedMap<String, Object> httpHeaders,
						OutputStream entityStream)
						throws IOException {

		JsonArrayBuilder builder = Json.createArrayBuilder();
		for(Message message : messages){
			JsonObjectBuilder msgBuilder = Json.createObjectBuilder();
			msgBuilder.add("severity", message.getSeverity().name())
					  .add("reason",message.getReason())
					  .add("message", message.getMessage());
			builder.add(msgBuilder);
		}	
		StringWriter adaptToJsonled = new StringWriter();
		JsonWriter jsonWriter = Json.createWriter(adaptToJsonled);
		jsonWriter.write(builder.build());
		jsonWriter.close();
		entityStream.write(adaptToJsonled.toString().getBytes("UTF-8"));
	}

}
