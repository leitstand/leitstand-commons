/*
 * (c) RtBrick, Inc - All rights reserved, 2015 - 2019
 */
package io.leitstand.commons.etc;

import static io.leitstand.commons.etc.Environment.getSystemProperties;
import static io.leitstand.commons.etc.Environment.getSystemProperty;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class EnvironmentTest {

	@Test
	public void system_property_overrides_default_value() {
		assertEquals("default",getSystemProperty("set-property","default"));
		System.setProperty("set-property","foobar");
		assertEquals("foobar",getSystemProperty("set-property","default"));
	}
	
	@Test
	public void filter_properties_by_regular_expression() {
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
	
}