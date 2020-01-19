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

import static io.leitstand.commons.model.Patterns.IPV4_PREFIX;
import static io.leitstand.commons.model.Patterns.IP_PREFIX;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Ipv4PrefixPatternTest {

	@Parameters
	public static Collection<Object[]> getAddresses(){
		return asList(new Object[][] {
			   {"192.168.10.1",true},
			   {"192.168.10.1/8",true},
			   {"192.168.10.1/16",true},
			   {"192.168.10.1/32",true},
			   {"192.168.10.1/33",false},
			   {"192.168.10.1/40",false}});
	}
	
	
	
	private String cidr;
	private boolean match;
	
	public Ipv4PrefixPatternTest(String cidr, boolean match) {
		this.cidr = cidr;
		this.match = match;
	}
	
	@Test
	public void addressMatch() {
		assertThat(format("%s mismatch", cidr),cidr.matches(IP_PREFIX),is(match));
		assertThat(format("%s mismatch", cidr),cidr.matches(IPV4_PREFIX),is(match));
	}
	
}
