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

import static io.leitstand.commons.jsonb.IsoDateAdapter.parseIsoDate;

import java.util.Date;

public final class ResourceUtil {

	public static int tryParseInt(String s, int defaultValue) {
		if(s == null || s.isEmpty()) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(s.trim());
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public static Date tryParseDate(String date) {
		return parseIsoDate(date);
	}

	private ResourceUtil() {
		// No instances allowed
	}

}
