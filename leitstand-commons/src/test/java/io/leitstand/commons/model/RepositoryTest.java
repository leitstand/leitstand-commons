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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

	@Mock
	private EntityManager em;
	
	@InjectMocks
	private Repository repository = new Repository();
	
	@Test
	public void no_result_exception_is_mapped_to_null() {
		Query query = mock(Query.class);
		when(query.execute(em)).thenThrow(new NoResultException());
		assertNull(repository.execute(query));
		assertNull(repository.execute(query,mock(Function.class)));

	}
	
	@Test
	public void attached_entity_is_returned() {
		Object entity = new Object();
		Query query = mock(Query.class);
		when(query.execute(em)).thenReturn(entity);
		assertSame(entity,repository.execute(query));
	}
	
	@Test
	public void translated_attached_entity_is_returned() {
		Object entity = new Object();
		Object translated = new Object();
		Function<Object,Object> mapping = mock(Function.class);
		when(mapping.apply(entity)).thenReturn(translated);
		Query query = mock(Query.class);
		when(query.execute(em)).thenReturn(entity);
		assertSame(translated,repository.execute(query,mapping));
	}
	
	
	@Test
	public void translated_attached_entity_list_is_returned() {
		Object entity = new Object();
		Object translated = new Object();
		Function<Object,Object> mapping = mock(Function.class);
		when(mapping.apply(entity)).thenReturn(translated);
		Query query = mock(Query.class);
		when(query.execute(em)).thenReturn(asList(entity));
		assertEquals(asList(translated),repository.executeMapListItem(query,mapping));
	}
	
	@Test
	public void entity_is_returned() {
		Query query = mock(Query.class);
		AbstractEntity entity = mock(AbstractEntity.class);
		when(query.execute(em)).thenReturn(entity);
		assertSame(entity,repository.execute(query));
	}
	
	@Test
	public void add_if_absent_returns_existing_entity() {
		Query query = mock(Query.class);
		AbstractEntity entity = mock(AbstractEntity.class);
		when(query.execute(em)).thenReturn(entity);

		assertSame(entity,repository.addIfAbsent(query, () -> entity));
		verify(em,never()).persist(entity);
		verify(em,never()).merge(entity);
	}
	
	@Test
	public void add_if_absent_creates_missing_entity() {
		Query query = mock(Query.class);
		AbstractEntity entity = mock(AbstractEntity.class);
		when(query.execute(em)).thenThrow(new NoResultException());

		assertSame(entity,repository.addIfAbsent(query, () -> entity));
		verify(em).persist(entity);
		verify(em,never()).merge(entity);
	}
	
	
}
