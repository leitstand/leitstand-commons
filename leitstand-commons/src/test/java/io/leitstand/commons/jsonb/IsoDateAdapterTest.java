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
package io.leitstand.commons.jsonb;

import static java.util.Calendar.AUGUST;
import static java.util.Calendar.DST_OFFSET;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.ZONE_OFFSET;
import static java.util.Calendar.getInstance;
import static java.util.TimeZone.getTimeZone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class IsoDateAdapterTest {
	
	private IsoDateAdapter adapter;
	private Date ref;
	private String iso;
	
	@Before
	public void initDates() {
		Calendar cal = getInstance();
		cal.setTimeZone(getTimeZone("GMT"));
		cal.set(ZONE_OFFSET, 36000000);
		cal.set(DST_OFFSET,3600000);
		cal.setTimeInMillis(0);
		cal.set(2020,AUGUST,3,14,32,05);
		ref = cal.getTime();
		iso = "2020-08-03T14:32:05Z";
		adapter = new IsoDateAdapter();
	}
	
	@Test
	public void null_string_is_mapped_to_null() throws Exception{
		assertNull(adapter.adaptFromJson(null));
	}
	
	@Test
	public void null_date_is_mapped_to_null() throws Exception{
		assertNull(adapter.adaptToJson(null));
	}
	
	@Test
	public void empty_string_is_mapped_to_null() throws Exception{
		assertNull(adapter.adaptFromJson(""));
	}
	
	@Test
	public void date_is_mapped_to_ISO_format() throws Exception{
		assertEquals(iso,adapter.adaptToJson(ref));
	}
	
	@Test
	public void ISO_string_is_mapped_to_date() throws Exception{
		assertEquals(ref,adapter.adaptFromJson(iso));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nonISO_string_raises_exception() throws Exception{
		adapter.adaptFromJson("foo");
	}
}
