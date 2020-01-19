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
package io.leitstand.commons.tx;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.inject.Provider;

import org.junit.Before;
import org.junit.Test;

import io.leitstand.commons.model.Repository;

public class SubtransactionServiceTest {
	
	static class UnitTestSubtransactionService extends SubtransactionService{

		private Provider<SubtransactionService> provider;
		private Repository repository;

		public UnitTestSubtransactionService(Provider<SubtransactionService> provider, Repository repository) {
			this.repository = repository;
			this.provider = provider;
		}
		
		@Override
		protected Repository getRepository() {
			return repository;
		}

		@Override
		protected Provider<SubtransactionService> getServiceProvider() {
			return provider;
		}
		
	}
	
	@Before
	public void init() {
		this.provider = mock(Provider.class);
		this.repository = mock(Repository.class);
		this.service = new UnitTestSubtransactionService(provider, repository);
	}
	

	private UnitTestSubtransactionService service;
	private Repository repository;
	private Provider<SubtransactionService> provider;

	@Test
	public void resume_transaction_if_new_commit_failed() {
		Transaction tx = mock(Transaction.class);
		Resume<?> resume = mock(Resume.class);
		SubtransactionService proxy = mock(SubtransactionService.class);
		when(provider.get()).thenReturn(proxy);
		service.run(tx, resume);	
		verify(resume).resume(repository);
	}
	
	
	@Test
	public void resume_transaction_if_new_transaction_failed() {
		Transaction tx = mock(Transaction.class);
		Resume<?> resume = mock(Resume.class);
		SubtransactionService proxy = mock(SubtransactionService.class);
		when(provider.get()).thenReturn(proxy);
		doThrow(new RuntimeException()).when(tx).transaction(repository);
		
		service.run(tx, resume);
		
		verify(resume).resume(repository);
	}
	
	@Test
	public void resume_transaction_if_new_transaction_succeeded() {
		Transaction tx = mock(Transaction.class);
		Resume<?> resume = mock(Resume.class);
		SubtransactionService newTx = mock(SubtransactionService.class);
		when(provider.get()).thenReturn(newTx);
		
		service.run(tx, resume);
		
		verify(newTx).run(tx);
		verify(resume).resume(repository);
	}
	
}
