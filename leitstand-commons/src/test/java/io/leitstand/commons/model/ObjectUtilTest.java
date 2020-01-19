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

import static io.leitstand.commons.model.ObjectUtil._null;
import static io.leitstand.commons.model.ObjectUtil.isDifferent;
import static io.leitstand.commons.model.ObjectUtil.optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;

public class ObjectUtilTest {

	@Test
	public void optional_maps_null_value_to_null() {
		assertNull(optional(null, String::length));
	}
	
	
	@Test
	public void optional_applies_mapping_to_value() {
		String s = "abc";
		assertEquals(Integer.valueOf(3),optional(s, String::length));
	}
	
	@Test
	public void optional_maps_null_object_to_null() {
		Predicate<?> predicate = mock(Predicate.class);
		Function<Object,?> function = mock(Function.class);
		assertNull(optional(null,predicate,function));
		verifyZeroInteractions(predicate,function);
	}
	
	@Test
	public void optional_maps_null_object_to_default_value() {
		Function<Object,Object> getter = mock(Function.class);
		Object defaultValue = new Object();
		assertSame(defaultValue,optional(null,getter,defaultValue));
		verifyZeroInteractions(getter);
	}

	@Test
	public void optional_maps_null_property_to_default_value() {
		Function<Object,Object> getter = mock(Function.class);
		Object object = new Object();
		Object defaultValue = new Object();
		assertSame(defaultValue,optional(object,getter,defaultValue));
		verify(getter).apply(object);
	}
	
	@Test
	public void optional_returns_property_value() {
		Object object = new Object();
		Object value = new Object();
		Object defaultValue = new Object();
		Function<Object,Object> getter = mock(Function.class);
		when(getter.apply(object)).thenReturn(value);
		assertSame(value,optional(object,getter,defaultValue));
		verify(getter).apply(object);
	}
	
	@Test
	public void optional_returns_null_when_object_does_not_satisfy_the_preficate() {
		Object object = new Object();
		Predicate<Object> predicate = mock(Predicate.class);
		when(predicate.test(object)).thenReturn(false);
		Function<Object,?> function = mock(Function.class);
		
		assertNull(optional(object,predicate,function));
		verifyZeroInteractions(function);
	}

	@Test
	public void optional_extract_property_when_object_satisfies_the_predicate() {
		Object object = new Object();
		Predicate<Object> predicate = mock(Predicate.class);
		when(predicate.test(object)).thenReturn(true);
		Function<Object,?> function = mock(Function.class);
		
		assertNull(optional(object,predicate,function));
		
		verify(function).apply(object);
	}
	
	@Test
	public void validate_is_null_predicate() {
		Object object = new Object();
		Function<Object,Object> function = mock(Function.class);
		assertTrue(_null(function).test(object));
		
		when(function.apply(object)).thenReturn(new Object());
		assertFalse(_null(function).test(object));
	}
	
	@Test
	public void two_different_objects_are_returned_to_be_different() {
		String a = "a";
		String b = "b";
		assertTrue(isDifferent(a,b));
		assertFalse(isDifferent(a,a));
	}
	
	@Test
	public void is_different_is_null_safe() {
		String a = "a";
		String b = null;
		assertTrue(isDifferent(a, b));
		assertTrue(isDifferent(b,a));
		assertFalse(isDifferent(b, b));
		
	}
}
