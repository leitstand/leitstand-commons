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

import static io.leitstand.commons.etc.EndpointConfig.defaultEndpoint;
import static io.leitstand.commons.etc.Environment.getSystemProperty;

import io.leitstand.commons.model.ValueObject;

public class LeitstandSettings extends ValueObject {

	private EndpointConfig leitstand = defaultEndpoint(getSystemProperty("LEITSTAND_ENDPOINT","http://localhost:8080/api/v1"));
	private OAuthConfig oauth2 = new OAuthConfig();
	private EndpointConfig repository = defaultEndpoint(getSystemProperty("IMAGE_REPOSITORY_BASE_URL","http://localhost:8080/images"));
	private String uiModulesDir = getSystemProperty("leitstand.ui.modules.dir","/META-INF/resources");
	
	public EndpointConfig getLeitstandApi() {
		return leitstand;
	}
	
	public OAuthConfig getOAuthConfig() {
		return oauth2;
	}
	
	public EndpointConfig getImageRepository() {
		return repository;
	}
	
	public String getUIModulesDir() {
		return uiModulesDir;
	}
	
}
