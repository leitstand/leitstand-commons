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

import static java.lang.String.format;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.leitstand.commons.jsonb.IsoDateAdapter;
import io.leitstand.commons.jsonb.JsonMessageBodyWriter;
import io.leitstand.commons.jsonb.JsonbDefaults;
import io.leitstand.commons.jsonb.MessagesMessageBodyWriter;

/**
 * Provider of all Leitstand available system resources.
 * <p>
 * System resources are used for managing the distributed Leitstand services and are not public available. 
 * The API gateway providing access to all Leitstand APIs SHALL NOT forward requests to system resources.
 * </p>
 * CDI is leveraged to discover all available {@link SystemResourceProvider} instances and obtain the available resources.
 * @see ApiResourceProvider
 */
@ApplicationPath("/system")
@ApplicationScoped
public class SystemResources extends Application {

	private static final Logger LOG = Logger.getLogger(SystemResources.class.getName());
	
	@Inject
	private Instance<SystemResourceProvider> modules;
	
	private Set<Class<?>> resources;
	
	@PostConstruct
	protected void discoverResources() {
		resources = new LinkedHashSet<>();
		for(SystemResourceProvider module : modules) {
			Set<Class<?>> moduleResources = module.getResources();
			if(resources.addAll(moduleResources)) {
				LOG.info(() -> format("Registered %2d system resources from %s", 
									  moduleResources.size(), 
									  module.getClass().getSimpleName()));
				moduleResources.forEach(resource -> LOG.fine(format("Register %s for %s", 
																	resource, 
																	module.getClass().getSimpleName()) ));
			}
		}
		resources.add(AccessDeniedExceptionMapper.class);
		resources.add(PersistenceExceptionMapper.class);
		resources.add(ConflictExceptionMapper.class);
		resources.add(UniqueKeyConstraintViolationExceptionMapper.class);
		resources.add(EntityNotFoundExceptionMapper.class);
		resources.add(OptimisticLockExceptionMapper.class);
		resources.add(UnprocessableEntityExceptionMapper.class);
		resources.add(InternalServerErrorExceptionMapper.class);
		resources.add(ValidationExceptionMapper.class);
		resources.add(IsoDateAdapter.class);
		resources.add(MessagesMessageBodyWriter.class);
		resources.add(JsonMessageBodyWriter.class);
		resources.add(RollbackExceptionMapper.class);
		resources.add(ServiceUnavailableExceptionMapper.class);
		resources.add(JsonbDefaults.class);
		resources.add(IsoDateParamConverterProvider.class);
	}
	
	
	/**
	 * Returns all API resources available in this Leitstand installation.
	 * @return the available API resources.
	 */
	@Override
	public Set<Class<?>> getClasses(){
		return resources;
	}
	
}
