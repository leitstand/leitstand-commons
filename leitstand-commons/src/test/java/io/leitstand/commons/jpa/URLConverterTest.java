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
import static org.junit.Assert.assertNull;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class URLConverterTest {
	
	private URLConverter converter = new URLConverter();
	private URL ref;
	private String url;
	
	@Before
	public void initDates() throws Exception {
		url = "http://www.rtbrick.com";
		ref = new URL(url);
	}
	
	
	@Test
	public void null_string_is_mapped_to_null() throws Exception{
		assertNull(converter.convertToEntityAttribute(null));
	}
	
	@Test
	public void null_URL_is_mapped_to_null() throws Exception{
		assertNull(converter.convertToDatabaseColumn(null));
	}
	
	@Test
	public void empty_string_is_mapped_to_null() throws Exception{
		assertNull(converter.convertToEntityAttribute(""));
	}
	
	@Test
	public void url_is_mapped_to_string() throws Exception{
		assertEquals(url,converter.convertToDatabaseColumn(ref));
	}
	
	@Test
	public void url_string_is_mapped_to_URL() throws Exception{
		assertEquals(ref,converter.convertToEntityAttribute(url));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void malformed_url_string_raises_exception() throws Exception{
		converter.convertToEntityAttribute("foo");
	}
}
