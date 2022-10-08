package io.leitstand.commons.json;

import static io.leitstand.commons.json.JsonUtil.tryGetString;
import static javax.json.Json.createObjectBuilder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.json.JsonObject;

import org.junit.Test;

public class JsonUtilTest {

	@Test
	public void try_get_string_returns_null_when_string_attribute_does_not_exist() {
		JsonObject j = createObjectBuilder().build();
		assertNull(tryGetString(j, "foo"));
	}
	
	@Test
	public void try_get_string_returns_empty_string_when_string_attribute_is_empty() {
		JsonObject j = createObjectBuilder().add("foo", "").build();
		assertTrue(tryGetString(j, "foo").isEmpty());
	}
	
	@Test
	public void try_get_string_returns_string_value() {
		JsonObject j = createObjectBuilder().add("foo", "bar").build();
		assertEquals(tryGetString(j, "foo"),"bar");
	}
	
}
