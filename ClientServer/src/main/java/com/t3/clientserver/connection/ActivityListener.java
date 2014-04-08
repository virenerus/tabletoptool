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
package com.t3.clientserver.connection;


public interface ActivityListener {
    public static enum Direction { Inbound, Outbound };
    public static enum State { Start, Progress, Complete };
    public static final int CHUNK_SIZE = 4 * 1024;
    
    public void notify(Direction direction, State state, int totalTransferSize, int currentTransferSize);

}
