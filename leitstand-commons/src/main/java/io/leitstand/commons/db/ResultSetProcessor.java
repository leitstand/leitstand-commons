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
package io.leitstand.commons.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Performs an operation on each result set record.
 */
@FunctionalInterface
public interface ResultSetProcessor {

	/**
	 * Executes an operation on the current result set record.
	 * @param rs - the result set to be processed.
	 * @throws SQLException in case of an error.
	 */
	void process(ResultSet rs) throws SQLException;
	
}
