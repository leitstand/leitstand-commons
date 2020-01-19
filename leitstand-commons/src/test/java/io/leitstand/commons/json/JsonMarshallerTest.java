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

import static io.leitstand.commons.json.JsonMarshaller.marshal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class JsonMarshallerTest {

	@Test
	public void null_value_is_mapped_to_null() {
		assertNull(marshal(null));
	}
	
	
	@Test
	public void can_serialize_object_to_json() {
		assertEquals("{\"name\":\"name\",\"value\":\"value\"}",marshal(new FakeJsonObject()).toString());
	}

	@Test
	public void can_serialize_map_to_json() {
		Map<String,String> map = new HashMap<>();
		map.put("name", "value");
		
		assertEquals("{\"name\":\"value\"}",marshal(map).toString());
	}
	
	
	@Test
	public void can_serialize_map_with_nested_objects_to_json() {
		Map<String,Object> map = new HashMap<>();
		map.put("nested",new FakeJsonObject());
		
		assertEquals("{\"nested\":{\"name\":\"name\",\"value\":\"value\"}}",marshal(map).toString());
	}
	
}
