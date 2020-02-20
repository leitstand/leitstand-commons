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
package io.leitstand.commons.etc;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.MILLIS;

import java.net.URI;
import java.time.Duration;
import java.time.temporal.TemporalUnit;

public class EndpointConfig {
	
	public static EndpointConfig defaultEndpoint(String url) {
		return new EndpointConfig(url);
	}


	
	protected EndpointConfig() {
		// Tool Ctor
		this.timeout = Duration.ofMillis(10000).toString();
	}
	
	protected EndpointConfig(String endpoint) {
		this();
		this.endpoint = endpoint;
	}
	
	private String endpoint;
	private String timeout;
	private String accessKey;
	
	public URI getEndpoint() {
		return URI.create(endpoint);
	}
	
	public URI getEndpoint(String path) {
		return URI.create(endpoint+path);
	}
	
	public URI getEndpoint(String path, Object... args) {
		return URI.create(endpoint+format(path, args));
	}
	
	public Duration getTimeout() {
		return Duration.parse(timeout);
	}
	
	public long getTimeoutMillis() {
		return getTimeout(MILLIS);
	}

	public long getTimeout(TemporalUnit unit) {
		return getTimeout().get(unit);
	}
	
	public String getAccessKey() {
		return accessKey;
	}
	
}
