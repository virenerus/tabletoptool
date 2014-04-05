/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
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