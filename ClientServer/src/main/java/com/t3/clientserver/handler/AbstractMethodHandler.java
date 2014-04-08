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
package com.t3.clientserver.handler;

import com.t3.clientserver.Command;
import com.t3.clientserver.NetworkSerializer;
import com.t3.clientserver.NetworkSerializer.TransferredMessage;

public abstract class AbstractMethodHandler<T extends Enum<T> & Command> implements MessageHandler {

	@Override
    public void handleMessage(String id, byte[] message) {
        TransferredMessage<T> tm=NetworkSerializer.<T>deserialize(message);
		handleMethod(id, tm.getMessage(), tm.getParameters());
    }

	public abstract void handleMethod(String id, T message,Object... parameters);
}
