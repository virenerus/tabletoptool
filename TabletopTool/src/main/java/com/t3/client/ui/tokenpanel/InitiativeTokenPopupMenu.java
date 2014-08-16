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

import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.t3.client.TabletopTool;
import com.t3.client.ui.TokenPopupMenu;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.guid.GUID;
import com.t3.language.I18N;
import com.t3.model.initiative.InitiativeList;
import com.t3.model.initiative.InitiativeValue;
import com.t3.model.initiative.InitiativeList.TokenInitiative;
import com.t3.model.Token;
import com.t3.model.ZonePoint;

/**
 * The popup menu for initiative tokens.
 * 
 * @author Jay
 */
public class InitiativeTokenPopupMenu extends TokenPopupMenu {

    /**
     * The token initiative instances selected by the user.
     */
    private Set<TokenInitiative> selectedTokenInitiatives;
    
    /**
     * The token initiative under the mouse is used for commands that work on a single token init.
     */
    private TokenInitiative tokenInitiativeUnderMouse;
    
    /**
     * @param selectedTokenSet Tokens handled by this menu
     * @param selectedInitiatives TokenIntiatives handled by this menu
     * @param x X location of the menu
     * @param y Y location of the menu
     * @param renderer Renderer of the map
     * @param tokenUnderMouse The token under the mouse provides default values.
     * @param tokenInitUnderMouse The initiative item under the mouse.
     */
    public InitiativeTokenPopupMenu(Set<GUID> selectedTokenSet, Set<TokenInitiative> selectedInitiatives,  int x, int y, 
            ZoneRenderer renderer, Token tokenUnderMouse, TokenInitiative tokenInitUnderMouse) {
        super(selectedTokenSet, x, y, renderer, tokenUnderMouse);
        removeAll();
        selectedTokenInitiatives = selectedInitiatives;
        tokenInitiativeUnderMouse = tokenInitUnderMouse;

        // Build the menu
        InitiativePanel ip = TabletopTool.getFrame().getInitiativePanel();
        I18N.setAction("initPanel.makeCurrent", MAKE_CURRENT_ACTION);
        addGMItem(MAKE_CURRENT_ACTION);
        I18N.setAction("initPanel.toggleHold", TOGGLE_HOLD_ACTION);
        addOwnedItem(TOGGLE_HOLD_ACTION);
        I18N.setAction("initPanel.setInitState", SET_INIT_STATE_VALUE);
        addOwnedItem(SET_INIT_STATE_VALUE);
        I18N.setAction("initPanel.clearInitState", CLEAR_INIT_STATE_VALUE);
        addOwnedItem(CLEAR_INIT_STATE_VALUE);
        I18N.setAction("initPanel.remove", REMOVE_TOKEN_ACTION);
        if (ip.hasGMPermission() || ip.isOwnerPermissions()) {
            add(new JMenuItem(REMOVE_TOKEN_ACTION));
            REMOVE_TOKEN_ACTION.setEnabled(ip.hasOwnerPermission(tokenUnderMouse));
        } // endif
        if (ip.hasGMPermission()) addSeparator();
        I18N.setAction("initPanel.moveUp", MOVE_UP_ACTION);
        addGMItem(MOVE_UP_ACTION);
        I18N.setAction("initPanel.moveDown", MOVE_DOWN_ACTION);
        addGMItem(MOVE_DOWN_ACTION);
        addSeparator();
        I18N.setAction("initPanel.center", CENTER_ACTION);
        add(CENTER_ACTION);
        addOwnedItem(new ImpersonateAction());
        addOwnedItem(createMacroMenu());
        addOwnedItem(createSpeechMenu());
        addOwnedItem(createStateMenu());
        addOwnedItem(createHaloMenu());
        
        // Do move up or move down need to be disabled
        InitiativeList list = getRenderer().getZone().getInitiativeList();
        int index = list.indexOf(tokenInitUnderMouse);
        if (index == 0) MOVE_UP_ACTION.setEnabled(false);
        if (index == list.getSize() - 1) MOVE_DOWN_ACTION.setEnabled(false);
        if (tokenInitUnderMouse == list.getTokenInitiative(list.getCurrent()))
            MAKE_CURRENT_ACTION.setEnabled(false);
    }
    
    /**
     * This action will turn the selected token's initiative on and off.
     */
    public final Action TOGGLE_HOLD_ACTION = new AbstractAction() {
        @Override
		public void actionPerformed(ActionEvent e) {
            for (TokenInitiative ti : selectedTokenInitiatives)
                ti.setHolding(!ti.isHolding());
        };
    };

    /**
     * This action will make the token under the mouse the current token.
     */
    public final Action MAKE_CURRENT_ACTION = new AbstractAction() {
        @Override
		public void actionPerformed(ActionEvent e) {
            InitiativeList list = getRenderer().getZone().getInitiativeList();
            list.setCurrent(list.indexOf(tokenInitiativeUnderMouse));
        };
    };

    /**
     * This action will set the initiative state of the currently selected token.
     */
    public final Action SET_INIT_STATE_VALUE = new AbstractAction() {
        @Override
		public void actionPerformed(ActionEvent e) {
            String message = I18N.getText("initPanel.enterState");
            String defaultValue = "";
            if (selectedTokenInitiatives.size() == 1) {
                TokenInitiative ti = selectedTokenInitiatives.iterator().next();
                String sName = ti.getToken().getName();
                if (TabletopTool.getPlayer().isGM() && ti.getToken().getGMName() != null && ti.getToken().getGMName().trim().length() != 0)
                    sName += " (" + ti.getToken().getGMName().trim() + ")";
                message = MessageFormat.format(I18N.getText("initPanel.enterState"), sName);
                if(ti.getState()!=null)
                	defaultValue = ti.getState().toString();
            } // endif
            String input = JOptionPane.showInputDialog(message, defaultValue);
            if (input == null) return;
            input = input.trim();
            for (TokenInitiative ti : selectedTokenInitiatives)
                ti.setUnparsedState(input.trim());
        };
    };

    /**
     * This action will set the initiative state of the currently selected token.
     */
    public final Action CLEAR_INIT_STATE_VALUE = new AbstractAction() {
        @Override
		public void actionPerformed(ActionEvent e) {
            for (TokenInitiative ti : selectedTokenInitiatives)
                ti.setState((InitiativeValue)null);
        };
    };

    /**
     * This action will remove the selected token from the list.
     */
    public final Action REMOVE_TOKEN_ACTION = new AbstractAction() {
        @Override
		public void actionPerformed(ActionEvent e) {
            InitiativeList list = getRenderer().getZone().getInitiativeList();
            InitiativePanel ip = TabletopTool.getFrame().getInitiativePanel();
            for (TokenInitiative ti : selectedTokenInitiatives) {
                if (ip.hasOwnerPermission(ti.getToken())) {
                  int index = list.indexOf(ti);
                  list.removeToken(index);
                } // endif
            } // endfor
        };
    };
    
    /**
     * This action will move a token up one space
     */
    public final Action MOVE_UP_ACTION = new AbstractAction() {
        @Override
		public void actionPerformed(ActionEvent e) {
            InitiativeList list = getRenderer().getZone().getInitiativeList();
            int index = list.indexOf(tokenInitiativeUnderMouse);
            list.moveToken(index, index - 1);
        };
    };    
    
    /**
     * This action will move a token up one space
     */
    public final Action MOVE_DOWN_ACTION = new AbstractAction() {
        @Override
		public void actionPerformed(ActionEvent e) {
            InitiativeList list = getRenderer().getZone().getInitiativeList();
            int index = list.indexOf(tokenInitiativeUnderMouse);
            list.moveToken(index, index + 2);
        };
    };

    /**
     * This action will center the selected token on the map and select it.
     */
    public final Action CENTER_ACTION = new AbstractAction() {
        @Override
		public void actionPerformed(ActionEvent e) {
            Token token = tokenInitiativeUnderMouse.getToken();
            getRenderer().centerOn(new ZonePoint(token.getX(), token.getY()));
            getRenderer().clearSelectedTokens();
            getRenderer().selectToken(token.getId());
        };
    };
}
