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
import static java.util.Arrays.asList;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import io.leitstand.commons.messages.Messages;

public final class Responses {
    
    @FunctionalInterface
    public static interface Decorator {
        ResponseBuilder decorate(ResponseBuilder response);
    }
    
    public static Decorator offsetHeader(int offset){
        return b -> b.header("Leitstand-Offset", offset);
    }
    
    public static Decorator limitHeader(int limit){
        return b -> b.header("Leitstand-Limit", limit);
    }
    
    public static Decorator sizeHeader(int size){
        return b -> b.header("Leitstand-Size", size);
    }
    
    public static Decorator eofHeader(boolean eof){
        return b -> b.header("Leitstand-Eof", eof);
    }
    
    public static Decorator contentType(String mimeType){
        return b -> b.type(mimeType);
    }
    
    public static Decorator contentType(MediaType mimeType){
        return b -> b.type(mimeType);
    }

	public static Response created(Messages messages, String template, Object...args ) {
		return created(messages,
					   URI.create(format(template, args)));
	}
	
	public static Response created(String template, Object... args) {
		return created(null,template,args);
	}
	
	public static Response created(Object uri) {
		return created(URI.create(uri.toString()),new Decorator[0]);
	}

	public static Response created(Messages messages, Object uri) {
		return created(messages,URI.create(uri.toString()));
	}
	
	public static Response created(URI uri, Decorator... decorators) {
		return decorated(Response.created(uri),
		                 decorators);
	}
	
	public static Response seeOther(String template, Object...args ) {
		return seeOther(URI.create(format(template, args)));
	}
	
	public static Response seeOther(Object uri, Decorator... decorators) {
		return seeOther(URI.create(uri.toString()),decorators);
	}
	
	public static Response seeOther(URI uri, Decorator... decorators) {
		return decorated(Response.seeOther(uri),
		                 decorators);
	}
	
	public static Response created(Messages messages, URI uri) {
		if(messages == null || messages.isEmpty()) {
			return created(uri);
		}
		return Response.created(uri).entity(messages).build();
	}

	
	public static Response accepted(Messages messages, Decorator... decorators) {
		if(messages == null || messages.isEmpty()) {
			return decorated(Response.accepted(),
			                 decorators);
		}
		return decorated(Response.accepted(messages),
		                 decorators);
	}

	public static Response accepted(Object entity, Decorator... decorators) {
	    return decorated(Response.accepted(entity),
	                     decorators);
	}
	
	public static Response success(Messages messages, Decorator... decorators) {
		if(messages == null || messages.isEmpty()){
		    return decorated(Response.noContent(),
		                     decorators);
		}
		return decorated(Response.ok(messages),
		                 decorators);
	}

    private static Response decorated(ResponseBuilder b,
                                      Decorator... decorators) {
        for(Decorator decorator : decorators) {
	        b = decorator.decorate(b);
	    }
	    return b.build();
    }
    
	public static Response success(Object entity, Decorator... decorators) {
		if(entity != null) {
		    return decorated(Response.ok(entity), 
		                     decorators);
		}
		return decorated(Response.noContent(), 
		                 decorators);
	}
	
	public static Response success(Object entity, String contentType, Decorator... decorators) {
	    List<Decorator> d = new LinkedList<>();
	    d.add(contentType(contentType));
	    d.addAll(asList(decorators));
	    return success(entity,d.toArray(new Decorator[d.size()]));
	}
	
	public static Response noContent(Decorator... decorators) {
	    return decorated(Response.noContent(),
	                     decorators);
	}
	
	private Responses() {
		// No instances allowed
	}
	
}
