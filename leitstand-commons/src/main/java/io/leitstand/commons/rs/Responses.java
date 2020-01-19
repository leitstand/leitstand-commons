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

import static java.lang.String.format;

import java.net.URI;

import javax.ws.rs.core.Response;

import io.leitstand.commons.messages.Messages;

public final class Responses {

	public static Response created(Messages messages, String template, Object...args ) {
		return created(messages,
					   URI.create(format(template, args)));
	}
	
	public static Response created(String template, Object... args) {
		return created(null,template,args);
	}
	
	public static Response created(Object uri) {
		return created(URI.create(uri.toString()));
	}

	public static Response created(Messages messages, Object uri) {
		return created(messages,URI.create(uri.toString()));
	}
	
	public static Response created(URI uri) {
		return Response.created(uri).build();
	}
	
	public static Response seeOther(String template, Object...args ) {
		return seeOther(URI.create(format(template, args)));
	}
	
	public static Response seeOther(Object uri) {
		return seeOther(URI.create(uri.toString()));
	}
	
	public static Response seeOther(URI uri) {
		return Response.seeOther(uri).build();
	}
	
	public static Response created(Messages messages, URI uri) {
		if(messages == null || messages.isEmpty()) {
			return created(uri);
		}
		return Response.created(uri).entity(messages).build();
	}

	
	public static Response accepted(Messages messages) {
		if(messages.isEmpty()) {
			return Response.accepted().build();
		}
		return Response.accepted(messages).build();
		
	}
	
	public static Response success(Messages messages) {
		if(messages == null || messages.isEmpty()) {
			return Response.noContent().build();
		}
		return Response.ok(messages).build();
		
	}
	
	public static Response success(Object entity) {
		if(entity == null) {
			return Response.noContent().build();
		}
		return Response.ok(entity).build();
	}
	
	public static Response success(Object entity, String contentType) {
		if(entity == null) {
			return Response.noContent().build();
		}
		return Response.ok(entity,contentType).build();
	}
	
	private Responses() {
		// No instances allowed
	}
	
}
