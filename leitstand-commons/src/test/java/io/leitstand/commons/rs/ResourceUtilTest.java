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

import static io.leitstand.commons.rs.ResourceUtil.tryParseInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ResourceUtilTest {

	
	@Test
	public void null_string_is_translated_to_default_value() {
		assertEquals(10,tryParseInt(null, 10));
	}
	
	@Test
	public void empty_string_is_translated_to_default_value() {
		assertEquals(10,tryParseInt("", 10));
	}
	
	@Test
	public void integer_string_is_translated_to_int() {
		assertEquals(20,tryParseInt("20", 10));

	}
	
	@Test
	public void float_string_is_translated_to_default_value() {
		assertEquals(10,tryParseInt("20.5", 10));

	}
	
	@Test
	public void text_string_is_translated_to_default_value() {
		assertEquals(10,tryParseInt("abc", 10));
	}
}
