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

/**
 * A <code>CompositeValue</code> is a {@link ValueObject} that a represents a single value, typically a string, composed by multiple attributes..
 * <p>
 * The <code>Version</code> is a good example for a composite value. 
 * Major, minor and patch level  are the scalar values forming the version composite value. 
 * Two version instances are equal, when they have the same major, minor and patch level.
 * The version is represented as a string like <code>1.2.3</code> as an example 
 * with <code>1</code> as major version number, <code>2</code> as minor version number</code> and <code>3</code> as patch level.
 * </p> 
 */
public abstract class CompositeValue extends ValueObject {
	
}
