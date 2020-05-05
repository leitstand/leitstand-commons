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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the behavior of {@link Environment} if no <code>ems.properties</code> file exists.
 */
public class NoneEnvironmentTest {

	private Environment env = new Environment();
	
	@Before
	public void initEnvironment() {
		// Init env as CDI container would do it
		env.readEnvironmentProperties();
	}
	

	@Test
	public void return_empty_map_if_configuration_file_does_not_exist() {
		assertTrue(env.loadFile("non-existing", yaml()).isEmpty());
	}

	@Test
	public void return_null_if_configuration_file_does_not_exist() {
		assertNull(env.loadFile("non-existing", yaml(DummyConfig.class)));
	}

	@Test
	public void return_default_config_if_configuration_file_does_not_exist() {
		DummyConfig cfg = env.loadConfig("non-existing", yaml(DummyConfig.class),DummyConfig::defaultConfig);
		assertNotNull(cfg);
		assertEquals(DummyConfig.DEFAULT,cfg);
	}
	
}
