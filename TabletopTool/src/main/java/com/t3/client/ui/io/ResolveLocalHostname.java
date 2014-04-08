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
package com.t3.client.ui.io;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author crash
 * 
 */
public class ResolveLocalHostname {
	/**
	 * Currently the parameter is unused. This routine there returns the ANY
	 * local address if it can, or the local host address if it can't. It
	 * presumes that ANY is actually "0.0.0.0" but if the underlying platform
	 * says it is, that's when it fallsback to using localhost.
	 * 
	 * @param intendedDestination
	 *            used to determine which NIC TabletopTool should bind to
	 * @return
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public static InetAddress getLocalHost(InetAddress intendedDestination) throws UnknownHostException, SocketException {
		InetAddress inet = InetAddress.getByAddress(new byte[] { 0, 0, 0, 0 });
		if (inet.isAnyLocalAddress())
			return inet;
		inet = InetAddress.getLocalHost();
		return inet;
	}
}
