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
package io.leitstand.commons.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class NullSafeJsonObjectBuilder implements JsonObjectBuilder{


	public static JsonObjectBuilder createNullSafeJsonObjectBuilder(){
		return new NullSafeJsonObjectBuilder(Json.createObjectBuilder());
	}
	
	private JsonObjectBuilder builder;

	NullSafeJsonObjectBuilder(JsonObjectBuilder builder) {
		this.builder = builder;
	}

	
	@Override
	public JsonObjectBuilder add(String name, BigDecimal value) {
		if(value != null){
			builder.add(name, value);
		}
		return this;
	}

	@Override
	public JsonObjectBuilder add(String name, BigInteger value) {
		if(value != null){
			builder.add(name, value);
		}
		return this;
				
	}

	@Override
	public JsonObjectBuilder add(String name, boolean value) {
		builder.add(name, value);
		return this;	}

	@Override
	public JsonObjectBuilder add(String name, double value) {
		builder.add(name, value);
		return this;
	}

	@Override
	public JsonObjectBuilder add(String name, int value) {
		builder.add(name, value);
		return this;
	}

	@Override
	public JsonObjectBuilder add(String name, JsonArrayBuilder value) {
		if(value != null) {
			builder.add(name, value);
		}
		return this;
	}

	@Override
	public JsonObjectBuilder add(String name, JsonObjectBuilder value) {
		if(value != null) {
			builder.add(name, value);
		}
		return this;
	}

	@Override
	public JsonObjectBuilder add(String name, JsonValue value) {
		if(value != null) {
			builder.add(name, value);
		}
		return this;
	}

	@Override
	public JsonObjectBuilder add(String name, long value) {
		builder.add(name, value);
		return this;
	}
	

	@Override
	public JsonObjectBuilder add(String name, String value) {
		if(value != null){
			builder.add(name, value);
		}
		return this;
	}

	@Override
	public JsonObjectBuilder addNull(String name) {
		builder.addNull(name);
		return this;
	}

	@Override
	public JsonObject build() {
		return builder.build();
	}
	
}
