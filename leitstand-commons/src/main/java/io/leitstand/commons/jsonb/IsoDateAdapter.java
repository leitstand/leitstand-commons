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

import static java.util.logging.Level.FINE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.json.bind.adapter.JsonbAdapter;
import javax.ws.rs.ext.Provider;

/**
 * Maps a <code>java.util.Date</code> to a string in ISO format (<code>yyyy-MM-dd'T'HH:mm:ss.SSSXXX</code>)
 * and creates a <code>java.util.Date</code> instance from a string in ISO format.
 */
@Provider
public class IsoDateAdapter implements JsonbAdapter<Date,String> {
	private static final Logger LOG = Logger.getLogger(IsoDateAdapter.class.getName());

	protected static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	public static final String ISO_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}[+|-]\\d{2}:\\d{2}";
	private static final ThreadLocal<SimpleDateFormat> FORMAT = new ThreadLocal<>();

	/**
	 * Formats the specified date in ISO date format.
	 * @param date the date to be formated
	 * @return returns the date in ISO format or <code>null</code> if the specified date is <code>null</code>.
	 */
	public static final String isoDateFormat(Date date){
		if(date == null) {
			return null;
		}
		return format().format(date);
	}
	
	/**
	 * Creates a date from the specified date string in ISO date format.
	 * Returns <code>null</code> if the specified string is <code>null</code> or empty.
	 * @param date a date in ISO string format.
	 * @return the date object or <code>null</code> if the specified date is <code>null</code> or empty.
	 */
	public static Date parseIsoDate(String date) {
		if(date == null || date.isEmpty()){
			return null;
		}
		try{
			return format().parse(date);
		} catch (Exception e){
			LOG.log(FINE,e.getMessage(),e);
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Creates a date from the specified date string in ISO date format.
	 * Returns <code>null</code> if the specified string is <code>null</code> or empty.
	 * @param date a date in ISO string format.
	 * @return the date object or <code>null</code> if the specified date is <code>null</code> or empty.
	 */
	@Override
	public Date adaptFromJson(String date) throws Exception {
		return parseIsoDate(date);
	}

	
	/**
	 * Formats the specified date in ISO date format.
	 * @param date the date to be formated
	 * @return the date in ISO format or <code>null</code> if the specified date is <code>null</code>.
	 */
	@Override
	public String adaptToJson(Date date) throws Exception {
		return isoDateFormat(date);
	}
	
	static SimpleDateFormat format(){
		SimpleDateFormat format = FORMAT.get();
		if(format == null){
			format = new SimpleDateFormat(ISO_FORMAT);
			FORMAT.set(format);
		}
		return format;
	}

}
