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
package io.leitstand.commons.etc;

import static io.leitstand.commons.etc.Environment.getSystemProperties;
import static io.leitstand.commons.etc.Environment.getSystemProperty;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Properties;

import org.junit.Test;

public class EnvironmentTest {

	@Test
	public void system_property_overrides_default_value() {
		assertEquals("default",getSystemProperty("set-property","default"));
		System.setProperty("set-property","foobar");
		assertEquals("foobar",getSystemProperty("set-property","default"));
	}
	
	@Test
	public void filter_properties_by_regular_expression_merge_default_values() {
		System.setProperty("foo.bar.a","a");
		System.setProperty("foo.bar.b","b");
		System.setProperty("foo.xyz.c","c");
		
		Map<String,String> defaultProperties = singletonMap("foo.bar.c","c");
		
		Map<String,String> props = getSystemProperties("foo\\.bar\\..*",defaultProperties);
		assertEquals(3, props.size());
		assertTrue(props.containsKey("foo.bar.a"));
		assertTrue(props.containsKey("foo.bar.b"));
		assertTrue(props.containsKey("foo.bar.c"));
	}
	
	
	@Test
	public void filter_properties_by_regular_expression_merge_default_properties() {
		System.setProperty("foo.bar.a","a");
		System.setProperty("foo.bar.b","b");
		System.setProperty("foo.xyz.c","c");
		
		Properties defaultProperties = new Properties();
		defaultProperties.put("foo.bar.c","c");
		
		Map<String,String> props = getSystemProperties("foo\\.bar\\..*",defaultProperties);
		assertEquals(3, props.size());
		assertTrue(props.containsKey("foo.bar.a"));
		assertTrue(props.containsKey("foo.bar.b"));
		assertTrue(props.containsKey("foo.bar.c"));
	}
	
	@Test
	public void filter_properties_by_regular_expression_without_defaults() {
		System.setProperty("foo.bar.a","a");
		System.setProperty("foo.bar.b","b");
		System.setProperty("foo.xyz.c","c");
		
		Map<String,String> props = getSystemProperties("foo\\.bar\\..*");
		assertEquals(2, props.size());
		assertTrue(props.containsKey("foo.bar.a"));
		assertTrue(props.containsKey("foo.bar.b"));
	}
	
}
