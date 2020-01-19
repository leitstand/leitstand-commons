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

import static io.leitstand.commons.model.BuilderUtil.assertNotInvalidated;
import static io.leitstand.commons.model.BuilderUtil.requires;

import org.junit.Test;

public class BuilderUtilTest {

	
	@Test(expected=IllegalStateException.class)
	public void throw_exception_when_instance_under_construction_is_null() {
		assertNotInvalidated(BuilderUtilTest.class, null);
	}
	
	
	@Test
	public void do_nothing_when_instance_under_construction_exists() {
		assertNotInvalidated(BuilderUtilTest.class, new Object());
	}
	
	@Test(expected=IllegalStateException.class)
	public void throw_exception_when_required_string_is_null() {
		requires(BuilderUtilTest.class, "dummy", (String)null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void throw_exception_when_required_string_is_empty() {
		requires(BuilderUtilTest.class, "dummy", "");
	}
	
	@Test
	public void do_nothing_when_string_is_non_empty() {
		requires(BuilderUtilTest.class, "dummy", "abc");
	}
	
	@Test(expected=IllegalStateException.class)
	public void throw_exception_when_required_property_is_null() {
		requires(BuilderUtilTest.class, "dummy", (Object) null);
	}
	
	@Test
	public void do_nothing_when_object_is_not_null() {
		requires(BuilderUtilTest.class, "dummy", new Object());
	}
	
}
