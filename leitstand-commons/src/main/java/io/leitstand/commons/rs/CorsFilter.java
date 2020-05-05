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
package io.leitstand.commons.rs;

import static io.leitstand.commons.etc.Environment.getSystemProperty;
import static io.leitstand.commons.model.StringUtil.isNonEmptyString;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * A filter to set HTTP caching directives to disable invocations of all REST API invocations.
 *
 */
@WebFilter(urlPatterns= {"/api/*","/ui/*"})
public class CorsFilter implements Filter{

	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "LEITSTAND_ACCESS_CONTROL_ALLOW_ORIGIN";
	
	//TODO Add logging
	//TODO Add Environment variable (disabled by default)
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String accessControlAllowOrigin = getSystemProperty(ACCESS_CONTROL_ALLOW_ORIGIN);
		if(isNonEmptyString(accessControlAllowOrigin)) {
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			httpResponse.addHeader("Access-Control-Allow-Origin","*");
			httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
		}
		
		chain.doFilter(request, response);
	}

}
