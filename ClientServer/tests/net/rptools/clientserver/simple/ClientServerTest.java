/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package net.rptools.clientserver.simple;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;
import net.rptools.clientserver.simple.client.ClientConnection;
import net.rptools.clientserver.simple.server.ServerConnection;

public class ClientServerTest extends TestCase {

    private static final String SERVER_HOSTNAME = "localhost";
    private static final int SERVER_PORT = 4444;
    private static final String CLIENT_ID = "testClient";

    private static final byte[] TEST_MESSAGE_01 = "TEST MESSAGE 01".getBytes();
    
    private ServerConnection server;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        server = new ServerConnection(4444);
    }

    @Override
    protected void tearDown() throws Exception {
        server.close();
        super.tearDown();
    }
    
    
    public void testFoo() throws UnknownHostException, IOException, InterruptedException {
        TestMessageHandler clientMessageHandler = new TestMessageHandler();
        
        ClientConnection client = new ClientConnection(SERVER_HOSTNAME, SERVER_PORT, CLIENT_ID);
        client.addMessageHandler(clientMessageHandler);
        
        client.start();
        
        server.broadcastMessage(TEST_MESSAGE_01);
        
        Thread.sleep(1000);
        
        assertEquals(CLIENT_ID, clientMessageHandler.getId());
        assertEquals(new String(TEST_MESSAGE_01), new String(clientMessageHandler.getMessage()));
        
        client.close();
    }
    
    public void testConcurrency() throws Exception {

    	AdditiveMessageHandler[] handlers = new AdditiveMessageHandler[15];
    	ClientConnection[] clients = new ClientConnection[15];
    	for (int i = 0; i < clients.length; i++) {
    		clients[i] = new ClientConnection(SERVER_HOSTNAME, SERVER_PORT, CLIENT_ID + i);
    		handlers[i] = new AdditiveMessageHandler();
    		clients[i].addMessageHandler(handlers[i]);
    		clients[i].start();
    	}

    	Thread.sleep(1000);
    	for(int i = 0; i < 30; i++) {
    		
    		for (int j = 0; j < 15; j++) {

    			final int id = j;
    			final int msg = i;
    			new Thread(new Runnable(){
    				
    				public void run() {
    					server.sendMessage("127.0.0.1-"+id, (new String(TEST_MESSAGE_01) + msg).getBytes());
    				}
    			}).start();
    		}
    	}
    	Thread.sleep(1000);
    	
    	// Verify
    	for (int i = 0; i < 15; i++) {
    		assertEquals(30, handlers[i].getMessageCount());
    		for (int j = 0; j < 30; j++) {
    			assertEquals(CLIENT_ID+i, handlers[i].idList.get(j));
    			assertEquals((new String(TEST_MESSAGE_01) + j), new String(handlers[i].messageList.get(j)));
    		}
    	}
    }
    
    private class TestMessageHandler implements net.rptools.clientserver.simple.MessageHandler {
        private String id;
        private byte[] message;

        public void handleMessage(String id, byte[] message) {
            this.id = id;
            this.message = message;
        }
        
        public String getId() {
            return id;
        }
        
        public byte[] getMessage() {
            return message;
        }
    }
    private class AdditiveMessageHandler implements net.rptools.clientserver.simple.MessageHandler {
    	private List<String> idList = new ArrayList<String>();
    	private List<byte[]> messageList = new ArrayList<byte[]>();
    	
        public void handleMessage(String id, byte[] message) {
        	idList.add(id);
        	messageList.add(message);
        }
        
        public String getId(int index) {
            return idList.get(index);
        }
        
        public byte[] getMessage(int index) {
            return messageList.get(index);
        }
        public int getMessageCount() {
        	return idList.size();
        }
    }
}
