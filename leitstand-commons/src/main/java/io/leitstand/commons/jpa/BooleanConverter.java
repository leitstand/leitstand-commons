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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class BooleanConverter implements AttributeConverter<Boolean, String>{

	@Override
	public String convertToDatabaseColumn(Boolean value) {
		if(value == null){
			return "N";
		}
		if(value.booleanValue()){
			return "Y";
		}
		return "N";
	}

	@Override
	public Boolean convertToEntityAttribute(String value) {
		return parseBoolean(value);
	}

	public static Boolean parseBoolean(String dbValue) {
		if(dbValue == null){
			return FALSE;
		}
		if("Y".equalsIgnoreCase(dbValue)){
			return TRUE;
		}
		return FALSE;
	}
	
}
