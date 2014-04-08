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
package com.t3.client.tool;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import com.t3.client.AppPreferences;
import com.t3.client.TabletopTool;
import com.t3.language.I18N;
import com.t3.model.GUID;
import com.t3.model.Token;
import com.t3.swing.SwingUtil;
import com.t3.util.TokenUtil;

/**
 */
public class FacingTool extends DefaultTool {
	private static final long serialVersionUID = -2807604658989763950L;

	// TODO: This shouldn't be necessary, just get it from the renderer
	private Token tokenUnderMouse;
	private Set<GUID> selectedTokenSet;

	public FacingTool() {
		// Non tool-bar tool ... atm
	}

	public void init(Token keyToken, Set<GUID> selectedTokenSet) {
		tokenUnderMouse = keyToken;
		this.selectedTokenSet = selectedTokenSet;
	}

	@Override
	public String getTooltip() {
		return "tool.facing.tooltip";
	}

	@Override
	public String getInstructions() {
		return "tool.facing.instructions";
	}

	@Override
	protected void installKeystrokes(Map<KeyStroke, Action> actionMap) {
		super.installKeystrokes(actionMap);

		actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (TabletopTool.confirm(I18N.getText("msg.confirm.removeFacings"))) {
					for (Token token : renderer.getSelectedTokensList()) {
						token.setFacing(null);
						renderer.flush(token);
					}
					// Go back to the pointer tool
					resetTool();
				}
			}
		});
	}

	////
	// MOUSE
	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);

		if (tokenUnderMouse == null || renderer.getTokenBounds(tokenUnderMouse) == null) {
			return;
		}
		Rectangle bounds = renderer.getTokenBounds(tokenUnderMouse).getBounds();

		int x = bounds.x + bounds.width / 2;
		int y = bounds.y + bounds.height / 2;

		double angle = Math.atan2(y - e.getY(), e.getX() - x);

		int degrees = (int) Math.toDegrees(angle);

		if (!SwingUtil.isControlDown(e)) {
			int[] facingAngles = renderer.getZone().getGrid().getFacingAngles();
			degrees = facingAngles[TokenUtil.getIndexNearestTo(facingAngles, degrees)];
		}
		Area visibleArea = null;
		Set<GUID> remoteSelected = new HashSet<GUID>();
		for (GUID tokenGUID : selectedTokenSet) {
			Token token = renderer.getZone().getToken(tokenGUID);
			if (token == null) {
				continue;
			}
			token.setFacing(degrees);
			// if has fog(required) 
			// and ((isGM with pref set) OR serverPolicy allows auto reveal by players)
			if (renderer.getZone().hasFog() && ((AppPreferences.getAutoRevealVisionOnGMMovement() && TabletopTool.getPlayer().isGM())) || TabletopTool.getServerPolicy().isAutoRevealOnMovement()) {
				visibleArea = renderer.getZoneView().getVisibleArea(token);
				remoteSelected.add(token.getId());
				renderer.getZone().exposeArea(visibleArea, token);
			}
			renderer.flushFog();
		}
		// XXX Instead of calling exposeFoW() when visibleArea is null, shouldn't we just skip it?
		TabletopTool.serverCommand().exposeFoW(renderer.getZone().getId(), visibleArea == null ? new Area() : visibleArea, remoteSelected);
		renderer.repaint(); // TODO: shrink this
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Commit
		for (GUID tokenGUID : selectedTokenSet) {
			Token token = renderer.getZone().getToken(tokenGUID);
			if (token == null) {
				continue;
			}
			renderer.flush(token);
			TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
		}
		// Go back to the pointer tool
		resetTool();
	}

	@Override
	protected void resetTool() {
		if (tokenUnderMouse.isStamp()) {
			TabletopTool.getFrame().getToolbox().setSelectedTool(StampTool.class);
		} else {
			TabletopTool.getFrame().getToolbox().setSelectedTool(PointerTool.class);
		}
	}
}
