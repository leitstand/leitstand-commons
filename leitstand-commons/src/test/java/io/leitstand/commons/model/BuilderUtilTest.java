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
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BuilderUtilTest {
    
    @Rule
    public ExpectedException exception = ExpectedException.none();

	
	public void throw_exception_when_instance_under_construction_is_null() {
        exception.expect(IllegalStateException.class);
		assertNotInvalidated(BuilderUtilTest.class, null);
	}
	
	
	@Test
	public void do_nothing_when_instance_under_construction_exists() {
		assertNotInvalidated(BuilderUtilTest.class, new Object());
	}
	
	public void throw_exception_when_required_string_is_null() {
        exception.expect(IllegalStateException.class);
	    requires(BuilderUtilTest.class, "dummy", (String)null);
	}
	
	public void throw_exception_when_required_string_is_empty() {
        exception.expect(IllegalStateException.class);
	    requires(BuilderUtilTest.class, "dummy", "");
	}
	
	@Test
	public void do_nothing_when_string_is_non_empty() {
	    try {
	        requires(BuilderUtilTest.class, "dummy", "abc");
	    } catch (Exception e) {
	        fail("Unexpected exception!");
	    }
	       
	}
	
	public void throw_exception_when_required_property_is_null() {
        exception.expect(IllegalStateException.class);
	    requires(BuilderUtilTest.class, "dummy", (Object) null);
	}
	
	@Test
	public void do_nothing_when_object_is_not_null() {
	    try {
	        requires(BuilderUtilTest.class, "dummy", new Object());
	    } catch (Exception e) {
	        fail("Unexpected exception");
	    }
	}
	
}
