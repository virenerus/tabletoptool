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
package com.t3.transfer;

import java.io.File;
import java.io.Serializable;

public interface ConsumerListener {
	public void assetAdded(Serializable id);
	public void assetComplete(Serializable id, String name, File data);
	public void assetUpdated(Serializable id);
}
