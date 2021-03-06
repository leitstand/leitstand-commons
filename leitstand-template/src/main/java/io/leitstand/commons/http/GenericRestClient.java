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
package io.leitstand.commons.http;

import static io.leitstand.commons.json.MapUnmarshaller.unmarshal;
import static io.leitstand.commons.model.StringUtil.isEmptyString;
import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.ws.rs.client.ClientBuilder.newBuilder;
import static javax.ws.rs.client.Entity.entity;

import java.io.StringReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import io.leitstand.commons.jsonb.JsonbDefaults;
import io.leitstand.commons.template.Template;

public class GenericRestClient {
	
	private URI endpoint;
	private String token;
	private int readTimeout;
	private TimeUnit readTimeoutUnit;
	private int connectTimeout;
	private TimeUnit connectTimeoutUnit;
	
	public GenericRestClient(URI endpoint) {
		this.endpoint = endpoint;
		this.readTimeout = 10;
		this.readTimeoutUnit = SECONDS;
		this.connectTimeout =2;
		this.connectTimeoutUnit = SECONDS;
	}
	
	public GenericRestClient readTimeout(int timeout, TimeUnit unit) {
		this.readTimeout = timeout;
		this.readTimeoutUnit = unit;
		return this;
	}
	
	public GenericRestClient connectTimeout(int timeout, TimeUnit unit) {
		this.connectTimeout = timeout;
		this.connectTimeoutUnit = unit;
		return this;
	}

	public void setAuthorizationHeader(String token) {
		this.token = token;
	}
	
	public <T extends JsonStructure> T submit(Request request) {
		String raw = submit(request,String.class);
		if(raw == null || raw.isEmpty()) {
			return null;
		}
		try(JsonReader reader = Json.createReader(new StringReader(raw))){
			return (T) reader.read();
		}
	}
	
	public <T> T submit(Request request, Template<T> responseMapping) {
		JsonObject raw = submit(request);
		Map<String,Object> model = unmarshal(raw).toMap();
		return responseMapping.apply(model);
	}
	
	public <T> T submit(Map<String,Object> model, Template<Request> requestTemplate, Template<T> responseTemplate) {
		Request request = requestTemplate.apply(model);
		JsonObject raw = submit(request);
		Map<String,Object> env = new HashMap<>();
		env.putAll(model);
		env.putAll(unmarshal(raw).toMap());
		return responseTemplate.apply(env);
	}

	public <T> T submit(Request request, Class<T> responseEntity) {
		return invoke(request).readEntity(responseEntity);
	}
	
	public Response invoke(Request request){
		Client client = newBuilder()
					 	.readTimeout(readTimeout,readTimeoutUnit)
					 	.connectTimeout(connectTimeout,connectTimeoutUnit)
					 	.register(new JsonbDefaults())
					 	.build();
					
		Builder call = client    
					   .target(endpoint + request.getPath())
					   .request();
		for(Map.Entry<String, Object> header : request.getHeaders().entrySet()) {
			call.header(header.getKey(),
					    header.getValue());
		}
		if(token != null) {
			call.header("Authorization", token);
		}

		switch(request.getMethod()) {
			case GET: return call.get();
			case PUT: return call.put(entity(request.getBody(),
			                                 contentType(request)));
			case DELETE: return call.delete();
			case POST:
			default: return call.post(entity(request.getBody(),
			                                 contentType(request)));
		}
	}
	
	private String contentType(Request request) {
	    String contentType = request.getHeader("Content-Type");
	    if(isEmptyString(contentType)){
	        return "application/json";
	    }
	    return contentType;
	}

	public URI getEndpoint() {
		return endpoint;
	}
	
}
