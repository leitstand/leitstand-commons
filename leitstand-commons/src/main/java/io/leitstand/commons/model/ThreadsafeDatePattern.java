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
package io.leitstand.commons.model;

import static java.lang.ThreadLocal.withInitial;
import static java.util.TimeZone.getTimeZone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class to cache a <code>SimpleDateFormat</code> per thread, since <code>SimpleDateFormat</code> is not thread-safe.
 */
public class ThreadsafeDatePattern {

	private ThreadLocal<SimpleDateFormat> cache;

	/**
	 * Creates a <code>ThreadsafeDatePattern</code>.
	 * @param pattern the simple date format
	 */
	public ThreadsafeDatePattern(String pattern){
		this.cache = withInitial(()->new SimpleDateFormat(pattern));
	}
	
	/**
	 * Creates a <code>ThreadsafeDatePattern</code> and bounds it to the given timezone.
	 * This allows generating UTC timestamps by binding the pattern to the UTC timezone.
	 * @param pattern the date pattern
	 * @param timezoneId the timezone identifier
	 */
	public ThreadsafeDatePattern(String pattern, String timezoneId) {
		this(pattern, getTimeZone(timezoneId));
	}
	
	/**
	 * Creates a <code>ThreadsafeDatePattern</code> and bounds it to the given timezone.
	 * This allows generating UTC timestamps by binding the pattern to the UTC timezone.
	 * @param pattern the date pattern
	 * @param timezoneId the timezone
	 */
	public ThreadsafeDatePattern(String pattern, TimeZone tz) {
		this.cache = withInitial(() -> {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setTimeZone(tz);
			return sdf;
		});
	}
	
	/**
	 * Formats a date according to the specified simple date format
	 * @param date the date to format
	 * @return the formatted date.
	 */
	public String format(Date date) {
		return cache.get().format(date);
	}
	
	/**
	 * Remove the date pattern from the internal thread local.
	 */
	public void close() {
	    this.cache.remove();
	}

    public Date parse(String date) throws ParseException  {
        return cache.get().parse(date);
    }
	
}
