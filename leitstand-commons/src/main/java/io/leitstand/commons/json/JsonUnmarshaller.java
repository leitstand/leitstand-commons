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

import javax.json.JsonObject;

/**
 * A utility to convert a JSON object to a Java object.
 *
 */
public final class JsonUnmarshaller {

	/**
	 * Converts a JSON object to a Java object.
	 * Returns <code>null</code> if the JSON object is <code>null</code>
	 * @param type the Java type
	 * @param json the JSON object.
	 * @return the Java object
	 */
	public static <T> T unmarshal(Class<T> type, JsonObject json) {
		if(json == null) {
			return null;
		}
		return jsonb().fromJson(json.toString(), type);
	}
	
	private JsonUnmarshaller() {
		// No instances allowed
	}
}
