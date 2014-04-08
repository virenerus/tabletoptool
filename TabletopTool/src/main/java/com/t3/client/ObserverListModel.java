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
package com.t3.client;

import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;

import com.t3.model.ObservableList;

public class ObserverListModel<T> extends AbstractListModel<T> implements Observer {
    
	private static final long serialVersionUID = 1L;
	private ObservableList<T> list;

    public ObserverListModel(ObservableList<T> list) {
        this.list = list;
        
        // TODO: Figure out how to clean this up when no longer in use
        // for now it doesn't matter, but, it's bad design
        list.addObserver(this);
    }
    
    @Override
	public T getElementAt(int index) {
        return list.get(index);
    }

    @Override
	public int getSize() {
        return list.size();
    }

    ////
    // OBSERVER
    @Override
	public void update(Observable o, Object arg) {
        fireContentsChanged(this, 0, list.size());
    }
}
