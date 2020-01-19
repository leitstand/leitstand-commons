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
package io.leitstand.commons.jpa;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converts a <code>java.net.URL</code> to a string and creates a <code>java.net.URL</code> from a string.
 */
@Converter
public class URLConverter implements AttributeConverter<URL, String>{
	
	private static final Logger LOG = Logger.getLogger(URLConverter.class.getName());

	/**
	 * Returns the string representation of the specified URL.
	 * @param url - the URL to be converted to a string
	 * @return the string representation of the URL or <code>null</code> if the specified URL was null.
	 */
	@Override
	public String convertToDatabaseColumn(URL url) {
		if(url == null){
			return null;
		}
		return url.toString();
	}

	/**
	 * Creates a <code>java.net.URL</code> from the specified url.
	 * Returns <code>null</code> if the specified URL is <code>null</code>, empty or malformed.
	 * @return the URL created from the string or <code>null</code> if the string is <code>null</code>, empty or is a malformed URL.
	 */
	@Override
	public URL convertToEntityAttribute(String url) {
		if(url == null || url.isEmpty()){
			return null;
		}
		try{
			return new URL(url);
		} catch(MalformedURLException e){
			LOG.fine(String.format("Cannot convert %s to a URL: %s",url,e.getMessage()));
			LOG.log(Level.FINER,e.getMessage(),e);
			throw new IllegalArgumentException(e);
		}
	}

}
