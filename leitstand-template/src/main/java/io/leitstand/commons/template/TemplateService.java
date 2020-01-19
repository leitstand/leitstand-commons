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
package io.leitstand.commons.template;

import static java.util.logging.Level.FINER;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@ApplicationScoped
public class TemplateService {
	
	private static final Logger LOG = Logger.getLogger(TemplateService.class.getName());

	public static TemplateService newTemplateService() {
		TemplateService service = new TemplateService();
		service.initTemplateFactory();
		return service;
	}

	private ScriptEngine engine;
	
	protected TemplateService() {
		// Hide default constructor
	}
	
	@PostConstruct
	protected void initTemplateFactory() {
		ScriptEngineManager manager = new ScriptEngineManager();
		engine = manager.getEngineByName("nashorn");
		try (Reader mustachejs = new InputStreamReader(TemplateService.class.getResourceAsStream("mustache.js"))){
			engine.eval(mustachejs);
		} catch (Exception e) {
			LOG.log(FINER,e.getMessage(),e);
			throw new IllegalStateException(e);
		}
	}
	
	public <T> Template<T> compileTemplate(String template, 
										  TemplateProcessor<T> processor){
		return new Template<>(engine,
							  null,
							  template,
							  processor);
	}
	
	public <T> Template<T> compileTemplate(String model, 
										   String template, 
										   TemplateProcessor<T> processor){
		
		return new Template<>(engine,
							  model,
							  template,
							  processor);
	}
	
	
	public <T> Template<T> loadTemplate(URL template, TemplateProcessor<T> processor){
		return new Template<>(engine,
							  null,
							  template,
							  processor);
	}
	
	public <T> Template<T> loadTemplate(URL functions, URL template, TemplateProcessor<T> processor){
		return new Template<>(engine,
							  functions,
							  template,
							  processor);
	}
	
	
}
