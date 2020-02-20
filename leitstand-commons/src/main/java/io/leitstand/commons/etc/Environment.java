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

import static io.leitstand.commons.etc.FileProcessor.yaml;
import static io.leitstand.commons.model.StringUtil.isNonEmptyString;
import static java.lang.String.format;
import static java.lang.System.getenv;
import static java.lang.Thread.currentThread;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableList;
import static java.util.logging.Level.FINE;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

/**
 * The <code>Environment</code> provides access to the environment properties stored in the <code>/etc/leitstand/leitstand.yaml</code> file
 * as well as access to all other EMS configuration files. 
 * All configuration files shall be located in the <code>/etc/leitstand</code> directory or sub directories.
 * <p>
 * The <code>Environment</code> is an <code>{@literal @ApplicationScoped}</code> bean and can be obtained via CDI.
 */
@ApplicationScoped
public class Environment {

	private static final Logger LOG = Logger.getLogger(Environment.class.getName());

	public static Environment emptyEnvironment() {
		return new Environment();
	}

	public static String getSystemProperty(String name) {
		return getSystemProperty(name,null);
	}
	
	public static String getSystemProperty(String name, String defaultValue) {
		String value =  System.getProperty(name);
		if(isNonEmptyString(value)) {
			return value;
		}
		value = System.getenv(name);
		if(isNonEmptyString(value)) {
			return value;
		}
		return defaultValue;
	}
	
	public static Map<String,String> getSystemProperties(String pattern){
		return getSystemProperties(compile(pattern));
	}
	
	public static Map<String,String> getSystemProperties(Pattern pattern){
		return getSystemProperties(pattern, emptyMap());
	}

	public static Map<String,String> getSystemProperties(String pattern, Properties defaultProperties){
		return getSystemProperties(compile(pattern),defaultProperties);
	}
	
	public static Map<String,String> getSystemProperties(Pattern pattern, Properties defaultProperties ){
		Map<String,String> defaultPropertiesMap = new HashMap<>();
		for(String property : defaultProperties.stringPropertyNames()) {
			defaultPropertiesMap.put(property, defaultProperties.getProperty(property));
		}
		return getSystemProperties(pattern,defaultPropertiesMap);
	}

	
	public static Map<String,String> getSystemProperties(String pattern, Map<String,String> defaultProperties){
		return getSystemProperties(compile(pattern),defaultProperties);
	}
	
	public static Map<String,String> getSystemProperties(Pattern pattern, Map<String,String> defaultProperties){
		Map<String,String> properties = new TreeMap<>();
		
		properties.putAll(defaultProperties.entrySet()
										   .stream()
										   .filter(entry -> pattern.matcher(entry.getKey()).matches())
										   .collect(toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		
		properties.putAll(getenv().entrySet()
				   				  .stream()
				   				  .filter(entry -> pattern.matcher(entry.getKey()).matches())
				   				  .collect(toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		for(String name : System.getProperties().stringPropertyNames()) {
			if(pattern.matcher(name).matches()) {
				properties.put(name, System.getProperty(name));
			}
		}
		return properties;
	}

	
	
	private File baseDir;
	
	@PostConstruct
	protected void readEnvironmentProperties() {
		baseDir = new File(getSystemProperty("LEITSTAND_ETC_ROOT","/etc/leitstand"));
	}
	
	public <T> T loadConfig(String fileName,
						  FileProcessor<T> processor) {
		return loadConfig(fileName,processor);
	}
	
	
	public <T> List<T> loadConfigs(String path, FilenameFilter filter, FileProcessor<T> processor){
		File dir = new File(baseDir,path);
		if(!dir.exists()) {
			LOG.warning(() -> format("Directory %s does not exist and will not be processed.",
									 dir.getAbsolutePath()));
			return emptyList();
		}
		
		List<T> configs = new LinkedList<>();
		int success = 0;
		int failure = 0;
		for(File file : dir.listFiles(filter)) {
			try (FileReader reader = new FileReader(file)){
				configs.add(processor.process(reader)); 
				success++;
			} catch (Exception e) {
				failure++;
				LOG.warning(() -> format("Cannot process file %s. Reason: %s",
									file.getAbsolutePath(),
									e.getMessage()));
				LOG.log(Level.FINE,e.getMessage(),e);
			}
		}
		LOG.info(format("Succeeded reading %d configurations from %s.",success,dir.getAbsolutePath()));
		LOG.info(format("Failed reading %d configurations from %s.",failure,dir.getAbsolutePath()));
		return unmodifiableList(configs);
	}
	
	public <T> List<T> loadConfigs(String path, FileFilter filter, FileProcessor<T> processor){
		File dir = new File(baseDir,path);
		if(!dir.exists()) {
			String msg = format("Directory %s does not exist and will not be processed.",
								dir.getAbsolutePath());
			LOG.warning(msg);
			return emptyList();
		}
		
		List<T> configs = new LinkedList<>();
		int success = 0;
		int failure = 0;
		for(File file : dir.listFiles(filter)) {
			try (FileReader reader = new FileReader(file)){
				configs.add(processor.process(reader)); 
				success++;
			} catch (Exception e) {
				failure++;
				LOG.warning(() -> format("Cannot process file %s. Reason: %s",
									file.getAbsolutePath(),
									e.getMessage()));
				LOG.log(Level.FINE,e.getMessage(),e);
			}
		}
		LOG.info(format("Added %d contributions from %s.",success,dir.getAbsolutePath()));
		LOG.warning(format("Failed to add %d contributions from %s.",failure,dir.getAbsolutePath()));
		return unmodifiableList(configs);		
	}
	
	public <T> T loadConfig(String fileName,
							FileProcessor<T> processor,
							Supplier<T> defaultFactory) {
		
		File config = new File(baseDir,fileName);
		if(!config.exists()) {
			if(defaultFactory != null) {
				LOG.warning(() -> format("File %s does not exist. Proceed with default configuration.",
		   			   					 config.getAbsolutePath()));
				return defaultFactory.get();
			}
			
			String msg = format("File %s does not exist. No default configuration available.",
								config.getAbsolutePath());
			LOG.severe(msg);
			return null;
		}
		
		try(FileReader reader = new FileReader(config)){
			return processor.process(reader);
		} catch (IOException e) {
			LOG.severe(format("An IO error occured while attempting to read %s: %s",
							   config.getAbsolutePath(),
							   e));
			LOG.log(FINE, e.getMessage(), e);
			if(defaultFactory != null) {
				LOG.info(() -> format("Use %s default config",fileName));
				return defaultFactory.get();
			}	
			return null;
		}
		
	}
	
	/**
	 * Parses a configuration file and returns an object structure created from the configuration file.
	 * If the specified configuration file does not exist a default configuration file shipped with the EMS binaries can be used.
	 * All default files are typically located within the <code>/META-INF</code> directory or sub directories.
	 * However, the environment basically can access all files that are accessible by the thread's context classloader.
	 * @param file - the file to load
	 * @param processor - the processor to parse the configuration file.
	 * @return the object structure created from the configuration file.
	 */
	public <T> T loadFile(String file, 
					  	  FileProcessor<T> processor){
	
		URL url = openFileUrl(file);
		
		if(url == null){
			String msg = format("File %s not found!",
					  		    file);
			LOG.severe(msg);
		}
		
		try {
			return processor.process(url);
		} catch(IOException e) {
			String msg = format("An I/O error occured while reading %s: %s",
								url,
								e.getMessage());
			LOG.log(FINE,msg,e);
			throw new UncheckedIOException(e);
		}
	}

	private URL openFileUrl( String file) {
		
		File config = new File(file);
		if(!config.isAbsolute() && !config.exists()) {
			config = new File(baseDir,file);
		}
		
		URL defaultUrl = currentThread()
						 .getContextClassLoader()
						 .getResource(file);
		
		
		if(!config.exists() || !config.canRead()) {
			LOG.warning(format("Cannot read from file %s. Proceed with default file (%s) shipped with Leitstand binaries.",
		   			    	   config,
		   					   defaultUrl));
			return defaultUrl;
		}
		
		try {
			return config.toURI().toURL();
		} catch (MalformedURLException e) {
			LOG.warning(format("File %s cannot be accessed due to malformed URL: %s. Proceed with default file (%s) shipped with Leitstand binaries.",
		   					   file,
		   					   e.getMessage(),
		   					   defaultUrl));
			LOG.log(FINE, e.getMessage(), e);
			return defaultUrl;
		}
	}
	
}
