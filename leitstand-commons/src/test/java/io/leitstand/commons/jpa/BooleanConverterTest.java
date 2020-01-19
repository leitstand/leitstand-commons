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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BooleanConverterTest {
	
	private BooleanConverter converter = new BooleanConverter();
	
	@Test
	public void null_string_is_mapped_to_false() throws Exception{
		assertFalse(converter.convertToEntityAttribute(null));
	}
	
	@Test
	public void null_boolean_is_mapped_to_false() throws Exception{
		assertEquals("N",converter.convertToDatabaseColumn(null));
	}
		
	@Test
	public void empty_string_is_mapped_to_false() throws Exception{
		assertFalse("N",converter.convertToEntityAttribute(""));
	}
	
	@Test
	public void boolean_is_mapped_to_string() throws Exception{
		assertEquals("N",converter.convertToDatabaseColumn(Boolean.FALSE));
		assertEquals("Y",converter.convertToDatabaseColumn(Boolean.TRUE));

	}
	
	@Test
	public void string_is_mapped_to_boolean() throws Exception{
		assertTrue(converter.convertToEntityAttribute("Y"));
		assertFalse(converter.convertToEntityAttribute("N"));

	}
	
	@Test
	public void malformed_url_string_raises_exception() throws Exception{
		assertFalse(converter.convertToEntityAttribute("foo"));
	}
}
