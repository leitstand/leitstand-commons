package io.leitstand.commons.json;

import javax.json.JsonObject;

public final class JsonUtil {

	public static String tryGetString(JsonObject object, String name) {
		if(object.containsKey(name)) {
			return object.getString(name);
		}
		return null;
	}
	
	private JsonUtil() {
		// No instances allowed
	}
}
