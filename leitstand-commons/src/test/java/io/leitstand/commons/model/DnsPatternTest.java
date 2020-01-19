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

import static io.leitstand.commons.model.Patterns.DNS_PATTERN;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;



public class DnsPatternTest {

	@Test
	public void acceptNameWithoutDots(){
		assertTrue("leaf1".matches(DNS_PATTERN));
	}
	
	@Test
	public void acceptNameWithHyphen(){
		assertTrue("test-name".matches(DNS_PATTERN));
	}
	
	@Test
	public void acceptNameWithDots(){
		assertTrue("test-name.rtbrick.com".matches(DNS_PATTERN));
	}

	@Test
	public void rejectNameStartingWithHyphen(){
		assertFalse("-name.rtbrick.com".matches(DNS_PATTERN));
	}
	

	@Test
	public void rejectNameWithDotAfterHyphen(){
		assertFalse("name-.rtbrick.com".matches(DNS_PATTERN));
	}
	
	@Test
	public void rejectNameWithHyphenAfterDot(){
		assertFalse("name.-rtbrick.com".matches(DNS_PATTERN));
	}

	@Test
	public void acceptIpAddress(){
		assertTrue("192.168.1.2".matches(DNS_PATTERN));
	}
	
	@Test
	public void rejectNameWithTwoSubsequentialDots(){
		assertFalse("test-name..rtbrick.com".matches(DNS_PATTERN));
	}
	
}
