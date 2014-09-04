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

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.t3.client.AppUtil;
import com.t3.client.ui.TokenPopupMenu;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.guid.GUID;
import com.t3.model.ModelChangeEvent;
import com.t3.model.ModelChangeListener;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.model.ZonePoint;
import com.t3.swing.SwingUtil;

public class TokenPanel extends JPanel implements ModelChangeListener {

    private ZoneRenderer currentZoneRenderer;
    private JList<Token> tokenList;

    public TokenPanel() {
        setLayout(new BorderLayout());
        tokenList = new JList<Token>();
        tokenList.setCellRenderer(new TokenListCellRenderer());
        tokenList.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseClicked(MouseEvent e) {
                // TODO: make this not an aic
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    
                    Token token = tokenList.getSelectedValue();
                    currentZoneRenderer.centerOn(new ZonePoint(token.getX(), token.getY()));
                    currentZoneRenderer.clearSelectedTokens();
                    currentZoneRenderer.selectToken(token.getId());
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                	
                	int itemUnderMouse = tokenList.locationToIndex(new Point(e.getX(), e.getY()));
                	if (!tokenList.isSelectedIndex(itemUnderMouse)) {
                		if (!SwingUtil.isShiftDown(e)) {
                			tokenList.clearSelection();
                		}
            			tokenList.addSelectionInterval(itemUnderMouse, itemUnderMouse);
                	}


                	final int x = e.getX();
                	final int y = e.getY();
                	EventQueue.invokeLater(new Runnable() {
                		@Override
						public void run() {
                			
                        	Token firstToken = null;
                        	Set<GUID> selectedTokenSet = new HashSet<GUID>();
                        	for (int index : tokenList.getSelectedIndices()) {

                        		Token token = tokenList.getModel().getElementAt(index);
                        		if (firstToken == null) {
                        			firstToken = token;
                        		}
                        		
                        		if (AppUtil.playerOwns(token)) {
                        			selectedTokenSet.add(token.getId());
                        		}
                        	}
                        	if (selectedTokenSet.size() > 0) {
                        		
                        		new TokenPopupMenu(selectedTokenSet, x, y, currentZoneRenderer, firstToken).showPopup(tokenList);
                        	}
                		}
                	});
                }
            }
        });
        new TokenPanelTransferHandler(tokenList);
        add(BorderLayout.CENTER, new JScrollPane(tokenList));
    }
    
    public void setZoneRenderer(ZoneRenderer renderer) {
        if (currentZoneRenderer != null) {
            currentZoneRenderer.getZone().removeModelChangeListener(this);
        }
        
        currentZoneRenderer = renderer;
        
        if (currentZoneRenderer != null) {
            currentZoneRenderer.getZone().addModelChangeListener(this);

            repaint();
        }

        // TODO: make this not a aic
        EventQueue.invokeLater(new Runnable(){
            
            @Override
			public void run() {
                Zone zone = currentZoneRenderer != null ? currentZoneRenderer.getZone() : null;
                tokenList.setModel(new TokenListModel(zone));
            }
        });
    }
    
    ////
    // ModelChangeListener
    @Override
	public void modelChanged(ModelChangeEvent event) {

        // Tokens are added and removed, just repaint ourself
        ((TokenListModel)tokenList.getModel()).update();
        repaint();
    }
}
