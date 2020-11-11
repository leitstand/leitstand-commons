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

import static io.leitstand.commons.rs.Responses.accepted;
import static io.leitstand.commons.rs.Responses.created;
import static io.leitstand.commons.rs.Responses.eofHeader;
import static io.leitstand.commons.rs.Responses.limitHeader;
import static io.leitstand.commons.rs.Responses.offsetHeader;
import static io.leitstand.commons.rs.Responses.seeOther;
import static io.leitstand.commons.rs.Responses.sizeHeader;
import static io.leitstand.commons.rs.Responses.success;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import io.leitstand.commons.messages.Messages;

@SuppressWarnings("unchecked")
public class ResponsesTest {

	private static final int HTTP_NO_CONTENT = 204;
	private static final int HTTP_OK = 200;
	private static final int HTTP_ACCEPTED = 202;
	private static final int HTTP_CREATED = 201;
    private static final int HTTP_SEE_OTHER = 303;
	
	private Messages messages;
	
	@Before
	public void initMessages() {
		this.messages = mock(Messages.class);
	}
	
	@Test
	public void ok_response() {
		when(messages.isEmpty()).thenReturn(true);
		Response response = success("entity");
		assertEquals(HTTP_OK, response.getStatus());
		assertEquals("entity",response.getEntity());
	}
	
	@Test
	public void null_entity_is_translated_to_no_content() {
		when(messages.isEmpty()).thenReturn(true);
		Response response = success((Object)null);
		assertEquals(HTTP_NO_CONTENT, response.getStatus());
		assertNull(response.getEntity());
	}
	
	@Test
	public void send_no_content_response_when_messages_is_null() {
		Response response = success((Messages)null);
		assertEquals(HTTP_NO_CONTENT, response.getStatus());
		assertNull(response.getEntity());
	}
	
	@Test
	public void send_no_content_response_when_messages_is_empty() {
		when(messages.isEmpty()).thenReturn(true);
		Response response = success(messages);
		assertEquals(HTTP_NO_CONTENT, response.getStatus());
		assertNull(response.getEntity());
	}
	
	@Test
	public void send_ok_response_when_messages_exist() {
		when(messages.isEmpty()).thenReturn(false);
		Response response = success(messages);
		assertEquals(HTTP_OK, response.getStatus());
		assertSame(messages,response.getEntity());
	}
	
	@Test
	public void send_created_response_with_detail_messages() {
		when(messages.isEmpty()).thenReturn(false);
		Response response = created(messages,URI.create("test"));
		assertEquals(HTTP_CREATED,response.getStatus());
		assertEquals(URI.create("test"),response.getLocation());
		assertSame(messages,response.getEntity());
	}
	
	@Test
	public void send_created_response_without_detail_messages() {
		when(messages.isEmpty()).thenReturn(true);
		Response response = created(messages,URI.create("test"));
		assertEquals(HTTP_CREATED,response.getStatus());
		assertEquals(URI.create("test"),response.getLocation());
		assertNull(response.getEntity());
	}
	
	@Test
	public void accepted_response_with_detail_messages() {
		when(messages.isEmpty()).thenReturn(false);
		Response response = accepted(messages);
		assertEquals(HTTP_ACCEPTED,response.getStatus());
		assertSame(messages,response.getEntity());
	}
	
	@Test
	public void accepted_response_without_detail_messages() {
		when(messages.isEmpty()).thenReturn(true);
		Response response = accepted(messages);
		assertEquals(HTTP_ACCEPTED,response.getStatus());
		assertNull(response.getEntity());
	}
	
	@Test
	public void decorate_success_response_with_headers() {
	    Response response = success("test", r -> r.header("header", "value"));
	    assertEquals(HTTP_OK,response.getStatus());
	    assertEquals("value",response.getHeaderString("header"));
	}
	
    @Test
    public void decorate_empty_success_response_with_headers() {
        Response response = success(null, r -> r.header("header", "value"));
        assertEquals(HTTP_NO_CONTENT,response.getStatus());
        assertEquals("value",response.getHeaderString("header"));
    }
    
    @Test
    public void create_accepted_response() {
        Response response = accepted(messages);
        assertEquals(HTTP_ACCEPTED,response.getStatus());
    }
    
    @Test
    public void decorate_accepted_response_with_headers() {
        Response response = accepted(messages, r -> r.header("header", "value"));
        assertEquals(HTTP_ACCEPTED,response.getStatus());
        assertEquals("value",response.getHeaderString("header"));
    }
    
    @Test
    public void create_seeother_response() {
        Response response = seeOther("/foo/bar",messages);
        assertEquals(HTTP_SEE_OTHER,response.getStatus());
        assertEquals(URI.create("/foo/bar"),response.getLocation());
    }
    
    @Test
    public void decorate_seeother_response_with_headers() {
        Response response = seeOther("/foo/bar", r -> r.header("header", "value"));
        assertEquals(HTTP_SEE_OTHER,response.getStatus());
        assertEquals(URI.create("/foo/bar"),response.getLocation());
        assertEquals("value",response.getHeaderString("header"));
    }
    
    @Test
    public void decorate_success_with_leistand_headers() {
        
        Response response = success(null,
                                    offsetHeader(200),
                                    limitHeader(100),
                                    sizeHeader(73),
                                    eofHeader(true));
        
        assertEquals(200,intHeader(response,"Leitstand-Offset"));
        assertEquals(100,intHeader(response,"Leitstand-Limit"));
        assertEquals(73,intHeader(response,"Leitstand-Size"));
        assertEquals(true,booleanHeader(response,"Leitstand-Eof"));
        
        
    }
	
    static int intHeader(Response response, String name) {
        List<Object> values = (List<Object>) response.getHeaders().get(name);
        return (Integer) values.get(0);
    }
    
    static boolean booleanHeader(Response response, String name) {
        List<Object> values = (List<Object>) response.getHeaders().get(name);
        return (Boolean) values.get(0);
    }
    
}
