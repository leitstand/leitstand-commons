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

import static io.leitstand.commons.json.SerializableJsonObject.serializable;
import static javax.json.Json.createObjectBuilder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.json.stream.JsonParsingException;

import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PGobject;

import io.leitstand.commons.json.SerializableJsonObject;

public class SerializableJsonConverterTest {
	
	private SerializableJsonObjectConverter converter = new SerializableJsonObjectConverter();
	private SerializableJsonObject ref;
	private String json;
	
	@Before
	public void initDates() {
		ref = serializable(createObjectBuilder()
						   .add("name", "junit")
						   .add("value", "test")
						   .build());
		json = ref.toString();
	}
	
	
	@Test
	public void null_string_is_mapped_to_null() throws Exception{
		assertNull(converter.convertToEntityAttribute(null));
	}
	
	@Test
	public void null_JSON_is_mapped_to_null() throws Exception{
		assertEquals(new PGobject(), converter.convertToDatabaseColumn(null));
	}
	
	@Test
	public void empty_string_is_mapped_to_null() throws Exception{
		PGobject object = new PGobject();
		object.setValue("");
		assertEquals(null,converter.convertToEntityAttribute(object));
	}
	
	@Test
	public void json_is_mapped_to_string() throws Exception{
		PGobject object = new PGobject();
		object.setValue(json);
		assertEquals(object,converter.convertToDatabaseColumn(ref));
	}
	
	@Test
	public void JSON_string_is_mapped_to_json_object() throws Exception{
		PGobject object = new PGobject();
		object.setValue(json);
		assertEquals(ref,converter.convertToEntityAttribute(object));
	}
	
	@Test(expected=JsonParsingException.class)
	public void non_JSON_string_raises_exception() throws Exception{
		PGobject object = new PGobject();
		object.setValue("foo");
		converter.convertToEntityAttribute(object);
	}
}
