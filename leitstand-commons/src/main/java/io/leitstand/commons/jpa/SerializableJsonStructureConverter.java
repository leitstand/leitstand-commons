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
package io.leitstand.commons.jpa;

import static io.leitstand.commons.json.SerializableJsonStructure.serializable;
import static java.lang.Boolean.parseBoolean;
import static java.lang.System.getProperty;
import static javax.json.Json.createReader;

import java.io.StringReader;
import java.sql.SQLException;

import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.postgresql.util.PGobject;

import io.leitstand.commons.json.SerializableJsonStructure;

@Converter(autoApply = true)
public class SerializableJsonStructureConverter implements AttributeConverter<SerializableJsonStructure, Object>{

	/*
	 * Use PGObject for postgres but String for other databases. 
	 */
	static final boolean POSTGRES;
	
	static {
		String test = getProperty("POSTGRES","true");
		POSTGRES = parseBoolean(test);
	}
	
	@Override
	public Object convertToDatabaseColumn(SerializableJsonStructure json) {
		if(POSTGRES) {
			try{
				PGobject object = new PGobject();
				object.setType("json");
				object.setValue(json != null ? json.toString() : null);
				return object;
			} catch (SQLException e){
				throw new IllegalStateException(e);
			}
		}
		if(json != null) {
			return json.toString();
		}
		return null;
	}

	@Override
	public SerializableJsonStructure convertToEntityAttribute(Object value) {
		if(value == null){
			return null;
		}
		String json = value.toString();
		if(POSTGRES) {
			json = ((PGobject)value).getValue();
		}
		return serializable(parseJson(json));
	}

	public static JsonStructure parseJson(String json) {
		if(json == null || json.isEmpty()) {
			return null;
		}
		try(JsonReader reader = createReader(new StringReader(json))){
			return reader.read();
		}
	}

}
