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
package com.t3.client.ui.tokenpanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.t3.client.TabletopTool;
import com.t3.model.Token;
import com.t3.model.Zone;

public class TokenListModel implements ListModel<Token> {

    private List<ListDataListener> listenerList = new CopyOnWriteArrayList<ListDataListener>();

    private Zone zone;
    private List<Token> tokenList;

    public TokenListModel () {
        this(null);
    }
    public TokenListModel(Zone zone) {
        this.zone = zone;
    }
    
    @Override
	public int getSize() {
        return getTokenList().size();
    }

    @Override
	public Token getElementAt(int index) {
        return getTokenList().get(index);
    }

    @Override
	public void addListDataListener(ListDataListener l) {
        listenerList.add(l);
    }

    @Override
	public void removeListDataListener(ListDataListener l) {
        listenerList.remove(l);
    }

    public void update() {
        tokenList = new ArrayList<Token>();
        
        if (zone == null) {
            return;
        }
        
        if (TabletopTool.getPlayer().isGM()) {
        	tokenList.addAll(zone.getAllTokens());
        } else {
        	for (Token token : zone.getAllTokens()) {
        		if (zone.isTokenVisible(token)) {
        			tokenList.add(token);
        		}
        	}
        }

        for (ListIterator<Token> iter = tokenList.listIterator(); iter.hasNext();) {
        	
        	if (iter.next().isObjectStamp()) {
        		iter.remove();
        	}
        }
        
        Collections.sort(tokenList, new Comparator<Token>(){
           @Override
		public int compare(Token o1, Token o2) {
               String lName = o1.getName();
               String rName = o2.getName();

               return lName.compareTo(rName);
            } 
        });
        
        fireContentsChangedEvent(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, tokenList.size()));
    }
    
    private void fireContentsChangedEvent(ListDataEvent e) {

        for (ListDataListener listener : listenerList) {
            listener.contentsChanged(e);
        }
    }

    private List<Token> getTokenList() {
        if (tokenList == null) {
            update();
        }
        
        return tokenList;
    }
}
