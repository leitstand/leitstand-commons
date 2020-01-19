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
package io.leitstand.commons.messages;

import static io.leitstand.commons.messages.Message.Severity.ERROR;
import static io.leitstand.commons.messages.Message.Severity.INFO;
import static io.leitstand.commons.messages.Message.Severity.WARNING;
import static io.leitstand.commons.messages.Messages.errors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MessagesTest {

	private Messages messages;
	
	@Before
	public void initMessages() {
		messages = new Messages();
		messages.init();
	}
	
	@Test
	public void add_error_message() {
		Message error = new Message(ERROR, "TST0000E", "Unittest");
		assertTrue(messages.isEmpty());
		assertEquals(0,messages.size());
		messages.add(error);
		assertFalse(messages.isEmpty());
		assertEquals(1,messages.size());
		assertTrue(messages.contains(errors()));
	}
	
	@Test
	public void add_warning_message() {
		Message error = new Message(WARNING, "TST0000W", "Unittest");
		assertTrue(messages.isEmpty());
		assertEquals(0,messages.size());
		messages.add(error);
		assertFalse(messages.isEmpty());
		assertEquals(1,messages.size());
		assertFalse(messages.contains(errors()));
		assertTrue(messages.contains(m -> m.getSeverity() == WARNING));
	}
	
	@Test
	public void add_info_message() {
		Message error = new Message(INFO, "TST0000W", "Unittest");
		assertTrue(messages.isEmpty());
		assertEquals(0,messages.size());
		messages.add(error);
		assertFalse(messages.isEmpty());
		assertEquals(1,messages.size());
		assertFalse(messages.contains(errors()));
		assertTrue(messages.contains(m -> m.getSeverity() == INFO));
	}
	
}
