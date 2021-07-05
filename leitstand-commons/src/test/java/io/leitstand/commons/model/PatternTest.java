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

import static io.leitstand.commons.model.Patterns.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PatternTest {
	
	private static final boolean MATCHES = true;
	private static final boolean MATCHES_NOT = false;

	private Pattern pattern;
	private String  test;
	private boolean match;
	
	@Parameters
	public static Collection<Object[]> getParameters(){

		return asList(new Object[][]{
			{HTTP_URL_PATTERN, "http://rtbrick",MATCHES},
			{HTTP_URL_PATTERN,"http://rtbrick.com",MATCHES},
			{HTTP_URL_PATTERN,"http://rtbrick.com:80",MATCHES},
			{HTTP_URL_PATTERN,"http://rtbrick.com:80/",MATCHES},
			{HTTP_URL_PATTERN,"http://rtbrick.com:80/foo",MATCHES},
			{HTTP_URL_PATTERN,"http://rtbrick.com:80/foo/bar",MATCHES},
			{HTTP_URL_PATTERN,"http://rtbrick.com:80/foo/bar/foobar.html",MATCHES},
			{HTTP_URL_PATTERN,"http://rtbrick.com/",MATCHES},
			{HTTP_URL_PATTERN,"http://rtbrick.com/foo",MATCHES},
			{HTTP_URL_PATTERN,"http://rtbrick.com/foo/bar",MATCHES},
			{HTTP_URL_PATTERN,"http://rtbrick.com/foo/bar/foobar.html",MATCHES},
			{HTTP_URL_PATTERN,"https://rtbrick",MATCHES},
			{HTTP_URL_PATTERN,"https://rtbrick.com",MATCHES},
			{HTTP_URL_PATTERN,"https://rtbrick.com:443",MATCHES},
			{HTTP_URL_PATTERN,"https://rtbrick.com:443/",MATCHES},
			{HTTP_URL_PATTERN,"https://rtbrick.com:443/foo",MATCHES},
			{HTTP_URL_PATTERN,"https://rtbrick.com:443/foo/bar",MATCHES},
			{HTTP_URL_PATTERN,"https://rtbrick.com:443/foo/bar/foobar.html",MATCHES},
			{HTTP_URL_PATTERN,"https://rtbrick.com/",MATCHES},
			{HTTP_URL_PATTERN,"https://rtbrick.com/foo",MATCHES},
			{HTTP_URL_PATTERN,"https://rtbrick.com/foo/bar",MATCHES},
			{HTTP_URL_PATTERN,"https://rtbrick.com/foo/bar/foobar.html",MATCHES},
			{HTTP_URL_PATTERN, "ftp://rtbrick",MATCHES_NOT},
			{HTTP_URL_PATTERN,"ftp://rtbrick.com",MATCHES_NOT},
			{DNS_PATTERN,"spine.lab.rtbrick.com",MATCHES},
			{DNS_PATTERN,"xpc-spine",MATCHES},
			{DNS_PATTERN,"spine.lab.",MATCHES_NOT},
            {DNS_PATTERN,"spine1.lab.local",MATCHES},
            {DNS_PATTERN,"spine-1.lab.local",MATCHES},
            {DNS_PATTERN,"localhost",MATCHES},
            {DNS_PATTERN,"0.0.0.0",MATCHES},
            {DNS_PATTERN,"127.0.0.1",MATCHES}});
	}

	public PatternTest(String pattern, String test, boolean matches) {
		this.pattern = Pattern.compile(pattern);
		this.test = test;
		this.match = matches;
	}
	
	@Test
	public void accept_string() {
		assertThat(test, 
				   pattern.matcher(test).matches(),
				   is(match));
	}


	
}
