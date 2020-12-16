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

import static io.leitstand.commons.jsonb.JsonbDefaults.jsonb;
import static javax.json.Json.createReader;

import java.io.StringReader;

import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 * A utility to convert a Java object to a JSON object.
 */
public final class JsonMarshaller {

    /**
     * Converts a Java String to a JSON string.
     * Returns <code>null</code> if the specified string is <code>null</code>.
     * @param string the Java string
     * @return the JSON string
     */
    public static JsonValue marshal(String string) {
        if(string == null) {
            return null;
        }
        String json = jsonb().toJson(string);
        try(StringReader buffer = new StringReader(json);
            JsonReader reader = createReader(buffer)){
            return reader.readValue();
        }
    }
    
	/**
	 * Converts a Java object to a JSON object.
	 * Returns <code>null</code> if the specified object is <code>null</code>.
	 * @param object the Java object
	 * @return the JSON object
	 */
	public static JsonObject marshal(Object object) {
		if(object == null) {
			return null;
		}
		String json = jsonb().toJson(object);
		try(StringReader buffer = new StringReader(json);
			JsonReader reader = createReader(buffer)){
			return reader.readObject();
		}
	}
	
	private JsonMarshaller() {
		// No instances allowed
	}
}
