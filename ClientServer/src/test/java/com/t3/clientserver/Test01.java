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
package com.t3.clientserver;

import com.t3.clientserver.connection.ClientConnection;
import com.t3.clientserver.connection.ServerConnection;
import com.t3.clientserver.handler.AbstractMethodHandler;

/**
 * @author drice
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Test01 {
	
	private enum NetworkMessage implements Command {A,B};

    public static void main(String[] args) throws Exception {
        ServerConnection server = new ServerConnection(4444);
        server.addMessageHandler(new ServerHandler());
        
        ClientConnection client = new ClientConnection("127.0.0.1", 4444, "Testing");
        client.addMessageHandler(new ClientHandler());
        client.start();
        
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                client.callMethod(NetworkMessage.A, new Float(2.3f), new Double(7.035923057230));
            }
            server.broadcastCallMethod(NetworkMessage.B, new Float(5.3f));
            Thread.sleep(1000);
        }

        client.close();
        server.close();

    }

    private static class ServerHandler extends AbstractMethodHandler {

    	@Override
        public void handleMethod(String id, Enum<? extends Command> method, Object... parameters) {
            System.out.println("Server received: " + method + " from " + id + " args=" + parameters.length);
            for (Object param : parameters) {
                System.out.println("\t" + param);
            }
        }
    }

    private static class ClientHandler extends AbstractMethodHandler {
    	@Override
        public void handleMethod(String id, Enum<? extends Command> method, Object... parameters) {
            System.out.println("Client received: " + method + " from " + id + " args=" + parameters.length);
            for (Object param : parameters) {
                System.out.println("\t" + param);
            }
        }
    }
}
