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
package io.leitstand.testing.it;

import java.util.concurrent.Callable;

public interface TransactionService {
	void begin();
	void commit();
	void rollback();
	default <T> T transactional(Callable<T> call)  {
		begin();
		try{
			try{
				return call.call();
			}finally {
				commit();
			}
		} catch(Exception e){
			rollback();
			throw new IllegalStateException(e);
		}
	}
	default void transactional(Runnable call) {
		begin();
		try{
			try{
				call.run();
			}finally {
				commit();
			}
		} catch(Exception e){
			rollback();
			throw new IllegalStateException(e);
		}
	}
	
}
