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
package io.leitstand.commons.log;

import static io.leitstand.commons.model.StringUtil.isEmptyString;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.LogRecord;

/**
 * Formats a <code>LogRecord</code> by executiong a list of functions to extract information from the log record in a certain order.
 */
class LogRecordFormatter {

	private List<Function<LogRecord,String>> segments;
	
	/**
	 * Create a <code>LogRecordFormatter</code>.
	 */
	LogRecordFormatter(){
		this.segments = new LinkedList<>();
	}
	
	/**
	 * Adds a new extractor to the formatter.
	 * @param extractor - the funcation to extract information from the log record.
	 */
	void add(Function<LogRecord,String> extractor) {
		segments.add(extractor);
	}
	
	/**
	 * Adds a string that shall be used as delimiter between two log record extractions.
	 * @param s - the delimiter string.
	 */
	void add(String s) {
		if(isEmptyString(s)) {
			return;
		}
		segments.add(r -> s);
	}

	/**
	 * Formats the log record into a string.
	 * @param record - the log record to be formatted.
	 * @return the formatted log record string
	 */
	String format(LogRecord record) {
		StringBuilder buffer = new StringBuilder();
		for(Function<LogRecord,String> segment : segments) {
			buffer.append(segment.apply(record));
		}
		return buffer.toString();
	}
	
}
