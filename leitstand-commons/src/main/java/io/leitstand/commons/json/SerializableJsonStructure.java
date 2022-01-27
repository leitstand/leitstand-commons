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
package io.leitstand.commons.json;

import static io.leitstand.commons.jpa.SerializableJsonStructureConverter.parseJson;
import static java.util.Objects.hash;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.json.JsonStructure;

/**
 * A simple wrapper to create a <i>serializable</i> <code>javax.json.JsonStructure</code>.
 * <p>
 * The <code>javax.json.JsonStructure</code> implementations does not have to be serializable which requires
 * to declare <code>transient JsonStructure</code> fields in serializable objects and to customize the serialization by writing the 
 * JSON string of the JSON object to the object output stream and by restoring the object from JSON read from an 
 * object input stream respectively.
 * The downside of this approach is, that JPA does not write <code>transient</code> fields to the database and JSON-B ignores <code>transient</code> fields as well.
 * </p>
 * <p>
 * Since all existing implementations are serializable that <code>transient</code> decleration could be omitted, but static code analysis considers this as an error, 
 * because the code relies on an implementation that is not part of the interface contract which is a bad idea in most cases.
 * </p>
 * This wrapper forwards all method invocations to the wrapped <code>JsonStructure</code> and implements serialization as outlined before.
 * By that, a <code>SerializableJsonStructure</code> does not have to be transient and will not be ignored by JPA and JSON-B respectively.
 *
 */
public class SerializableJsonStructure implements JsonStructure, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static JsonStructure unwrap(SerializableJsonStructure struct) {
		if(struct == null) {
			return null;
		}
		return struct.unwrap();
	}

	public static SerializableJsonStructure serializable(JsonStructure struct) {
		if(struct == null) {
			return null;
		}
		if(struct instanceof SerializableJsonStructure) {
			return (SerializableJsonStructure) struct;
		}
		return new SerializableJsonStructure(struct);
	}
	

	private transient JsonStructure struct;

	public SerializableJsonStructure(JsonStructure struct) {
		this.struct = struct;
	}

	@Override
	public boolean equals(Object o) {
	    if (o == null) {
	        return false;
	    }
	    if (o == this) {
	        return true;
	    }
	    if (o.getClass() != getClass()) {
	        return false;
	    }
	    SerializableJsonStructure s = (SerializableJsonStructure) o;
		return struct.equals(s.struct);
	}

	@Override
	public int hashCode() {
		return hash(struct);
	}

	@Override
	public String toString() {
	    return struct.toString();
	}
	
	public JsonStructure unwrap() {
		return struct;
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.struct = parseJson((String)in.readObject());
	}
	
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(struct.toString());
	}

    @Override
    public ValueType getValueType() {
        return struct.getValueType();
    }

}
