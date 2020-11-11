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
import java.util.function.UnaryOperator;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import io.leitstand.commons.messages.Messages;

public final class Responses {
    
    public static UnaryOperator<ResponseBuilder> offsetHeader(int offset){
        return b -> b.header("Leitstand-Offset", offset);
    }
    
    public static UnaryOperator<ResponseBuilder> limitHeader(int limit){
        return b -> b.header("Leitstand-Limit", limit);
    }
    
    public static UnaryOperator<ResponseBuilder> sizeHeader(int size){
        return b -> b.header("Leitstand-Size", size);
    }
    
    public static UnaryOperator<ResponseBuilder> eofHeader(boolean eof){
        return b -> b.header("Leitstand-Eof", eof);
    }
    

	public static Response created(Messages messages, String template, Object...args ) {
		return created(messages,
					   URI.create(format(template, args)));
	}
	
	public static Response created(String template, Object... args) {
		return created(null,template,args);
	}
	
	public static Response created(Object uri) {
		return created(URI.create(uri.toString()),new UnaryOperator[0]);
	}

	public static Response created(Messages messages, Object uri) {
		return created(messages,URI.create(uri.toString()));
	}
	
	public static Response created(URI uri, UnaryOperator<ResponseBuilder>... decorators) {
		return decorated(Response.created(uri),decorators);
	}
	
	public static Response seeOther(String template, Object...args ) {
		return seeOther(URI.create(format(template, args)));
	}
	
	public static Response seeOther(Object uri, UnaryOperator<ResponseBuilder>... decorators) {
		return seeOther(URI.create(uri.toString()),decorators);
	}
	
	public static Response seeOther(URI uri, UnaryOperator<ResponseBuilder>... decorators) {
		return decorated(Response.seeOther(uri),decorators);
	}
	
	public static Response created(Messages messages, URI uri) {
		if(messages == null || messages.isEmpty()) {
			return created(uri);
		}
		return Response.created(uri).entity(messages).build();
	}

	
	public static Response accepted(Messages messages, UnaryOperator<ResponseBuilder>... decorators) {
		if(messages == null || messages.isEmpty()) {
			return decorated(Response.accepted(),decorators);
		}
		return decorated(Response.accepted(messages),decorators);
	}

	public static Response accepted(Object entity, UnaryOperator<ResponseBuilder>... decorators) {
	    return decorated(Response.accepted(entity),decorators);
	}
	
	public static Response success(Messages messages, UnaryOperator<ResponseBuilder>... decorators) {
		if(messages == null || messages.isEmpty()){
		    return decorated(Response.noContent(),decorators);
		}
		return decorated(Response.ok(messages),decorators);
	}

    private static Response decorated(ResponseBuilder b,
            UnaryOperator<ResponseBuilder>... decorators) {
        for(UnaryOperator<ResponseBuilder> decorator : decorators) {
	        b = decorator.apply(b);
	    }
	    return b.build();
    }
    
	public static Response success(Object entity, UnaryOperator<ResponseBuilder>... decorators) {
		if(entity != null) {
		    return decorated(Response.ok(entity), decorators);
		}
		return decorated(Response.noContent(),decorators);
	}
	
	public static Response success(Object entity, String contentType, UnaryOperator<ResponseBuilder>... decorators) {
		if(entity == null) {
			return decorated(Response.noContent(),decorators);
		}
		return decorated(Response.ok(entity,contentType),decorators);
	}
	
	public static Response noContent(UnaryOperator<ResponseBuilder>... decorators) {
	    return decorated(Response.noContent(),decorators);
	}
	
	private Responses() {
		// No instances allowed
	}
	
}
