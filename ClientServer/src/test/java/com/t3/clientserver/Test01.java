/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.clientserver;

import java.util.LinkedList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.t3.clientserver.connection.ClientConnection;
import com.t3.clientserver.connection.ServerConnection;
import com.t3.clientserver.handler.AbstractMethodHandler;

public class Test01 {
	
	private enum NetworkMessage implements Command {A,B};

	@Test
    public void simpleNetworkTest() throws Exception {
        ServerConnection server = new ServerConnection(4444);
        ServerHandler sh = new ServerHandler();
        server.addMessageHandler(sh);
        
        ClientConnection client = new ClientConnection("127.0.0.1", 4444, "Testing");
        ClientHandler ch = new ClientHandler();
        client.addMessageHandler(ch);
        client.start();
        
        for (int i = 0; i < 5; i++) {
            if (i % 3 == 0) {
                client.callMethod(NetworkMessage.A, new Float(2.3f));
            }
            server.broadcastCallMethod(NetworkMessage.B, new Float(5.3f));
            Thread.sleep(1000);
        }

        client.close();
        server.close();

        for(Exception t:ch.exceptions)
        	throw t;
        for(Exception t:sh.exceptions)
        	throw t;
    }

    private static class ServerHandler extends AbstractMethodHandler<NetworkMessage> {
    	public List<Exception> exceptions=new LinkedList<Exception>();
    	
    	@Override
        public void handleMethod(String id, NetworkMessage method, Object... parameters) {
            System.out.println("Server received: " + method + " from " + id + " args=" + parameters.length);
            
            try {
	            Assert.assertEquals(parameters.length, 2);
	            Assert.assertEquals(parameters[0], 2.3f);
	            Assert.assertEquals(parameters[1], 7.035923057230);
            }
	        catch(Exception e) {
	        	exceptions.add(e);
	        }
        }
    }

    private static class ClientHandler extends AbstractMethodHandler<NetworkMessage> {
    	public List<Exception> exceptions=new LinkedList<Exception>();
    	
    	@Override
        public void handleMethod(String id, NetworkMessage method, Object... parameters) {
            System.out.println("Client received: " + method + " from " + id + " args=" + parameters.length);
            try {
	            Assert.assertEquals(parameters.length, 1);
	            Assert.assertEquals(parameters[0], 5.3f);
			}
		    catch(Exception e) {
		    	exceptions.add(e);
		    }
        }
    }
}
