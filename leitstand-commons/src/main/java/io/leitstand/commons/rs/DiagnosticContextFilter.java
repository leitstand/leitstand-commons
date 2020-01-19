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

import static io.leitstand.commons.log.DiagnosticContext.clear;
import static io.leitstand.commons.log.DiagnosticContext.push;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;

import io.leitstand.commons.log.DiagnosticContext;

/**
 * A REST-API filter that reads the <code>Transaction-ID</code> HTTP header and pushes it to the {@link DiagnosticContext}.
 *
 */
@Provider
public class DiagnosticContextFilter implements ClientRequestFilter, ClientResponseFilter{

	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
		clear();
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		push("TID: "+requestContext.getHeaderString("Transaction-ID"));
	}

}
