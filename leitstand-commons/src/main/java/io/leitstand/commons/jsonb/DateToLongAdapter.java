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

import java.util.Date;

import javax.json.bind.adapter.JsonbAdapter;
import javax.ws.rs.ext.Provider;

/**
 * Maps a <code>java.util.Date</code> to unixtime long value, i.e milliseconds from 01-01-1970T00:00:00.000 UTC, and vice versa.
 */
@Provider
public class DateToLongAdapter implements JsonbAdapter<Date,Long> {

	/**
	 * Maps the given unixtime to a <code>java.util.Date</code>.
	 * @param date - the unixtime value
	 * @return a date object representing the given unixtime or <code>null</code> if the unixtime was null.
	 */
	@Override
	public Date adaptFromJson(Long date) throws Exception {
		if(date == null) {
			return null;
		}
		return new Date(date.longValue());
	}

	
	/**
	 * Converts the given date to unixtime.
	 * @param date - the date to be converted into unixtime
	 * @return the unix epoch timestamp or <code>null</code> if the specified date is <code>null</code>.
	 */
	@Override
	public Long adaptToJson(Date date) throws Exception {
		if(date == null) {
			return null;
		}
		return Long.valueOf(date.getTime());
	}
	

}
